package cz.jurina.tennisclub.tennisclub.service;

import cz.jurina.tennisclub.tennisclub.dao.CourtDao;
import cz.jurina.tennisclub.tennisclub.dao.CustomerDao;
import cz.jurina.tennisclub.tennisclub.dao.ReservationDao;
import cz.jurina.tennisclub.tennisclub.dto.CreateReservationDto;
import cz.jurina.tennisclub.tennisclub.dto.ReservationDto;
import cz.jurina.tennisclub.tennisclub.entity.Court;
import cz.jurina.tennisclub.tennisclub.entity.Customer;
import cz.jurina.tennisclub.tennisclub.entity.Reservation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationDao reservationDao;
    private final CustomerDao customerDao;
    private final CourtDao courtDao;

    @Override
    public double createReservation(CreateReservationDto dto) {
        return createOrUpdate(dto, new Reservation());
    }

    @Override
    public List<ReservationDto> getAllReservations() {
        return reservationDao.findAll().stream().map(this::entityToDto).collect(Collectors.toList());
    }

    @Override
    public List<ReservationDto> getReservationsByCourtId(Long courtId) {
        return reservationDao.findAllByCourtId(courtId).stream().map(this::entityToDto).collect(Collectors.toList());
    }

    @Override
    public List<ReservationDto> getReservationsByPhoneNumber(String phoneNumber, boolean onlyFuture) {

        if (!phoneNumber.matches("^\\+?\\d{9,15}$")) {
            throw new IllegalArgumentException("Invalid phone number.");
        }

        return reservationDao.
                findFutureByPhoneNumber(phoneNumber)
                .stream()
                .filter(r -> !onlyFuture || r.getStartTime().isAfter(java.time.LocalDateTime.now()))
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public double updateReservation(Long reservationId, CreateReservationDto dto) {

        testId(reservationId);

        return createOrUpdate(dto, reservationDao.findById(reservationId));
    }

    @Override
    public void cancelReservation(Long reservationId) {

        testId(reservationId);

        reservationDao.deleteById(reservationId);
    }

    private ReservationDto entityToDto(Reservation r) {
        return new ReservationDto(r.getId(),
                r.getCourt().getId(),
                r.getCustomer().getName(),
                r.getCustomer().getSurname(),
                r.getCustomer().getPhone(),
                r.getStartTime(),
                r.getEndTime(),
                r.isDouble(),
                r.getReservationPrice(),
                r.getDateCreated());
    }

    private void testId(Long id) {
        if (reservationDao.findById(id) == null){
            throw new IllegalArgumentException("Court with id " + id + " does not exist");
        }
    }

    private double createOrUpdate(CreateReservationDto dto, Reservation newReservation) {
        Court court = courtDao.findById(dto.getCourtId());
        if (court == null) {
            throw new IllegalArgumentException("Court not found");
        }

        if (reservationDao.existsOverlappingReservation(dto.getCourtId(), dto.getStartTime(), dto.getEndTime())) {
            throw new IllegalArgumentException("Reservations overlaps");
        }

        if (dto.getEndTime().isBefore(dto.getStartTime())) {
            throw new IllegalArgumentException("End time cannot be before start time");
        }

        if (!dto.getCustomerPhoneNumber().matches("^\\+?\\d{9,15}$")) {
            throw new IllegalArgumentException("Invalid phone number.");
        }

        Customer customer = customerDao.findByPhoneNumber(dto.getCustomerPhoneNumber());

        if (customer == null) {
            customer = new Customer();
            customer.setName(dto.getCustomerName());
            customer.setSurname(dto.getCustomerSurname());
            customer.setPhone(dto.getCustomerPhoneNumber());

            customerDao.save(customer);
        }

        long minutes = Duration.between(dto.getStartTime(), dto.getEndTime()).toMinutes();
        double price = court.getCourtSurface().getPrice() * minutes;
        price = dto.getIsDouble() ? price * 1.5 : price;

        newReservation.setCustomer(customer);
        newReservation.setCourt(court);
        newReservation.setStartTime(dto.getStartTime());
        newReservation.setEndTime(dto.getEndTime());
        newReservation.setDouble(dto.getIsDouble());
        newReservation.setReservationPrice(price);

        reservationDao.save(newReservation);

        return price;
    }
}
