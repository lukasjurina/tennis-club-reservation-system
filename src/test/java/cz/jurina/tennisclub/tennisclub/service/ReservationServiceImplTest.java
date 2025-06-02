package cz.jurina.tennisclub.tennisclub.service;

import cz.jurina.tennisclub.tennisclub.dao.CourtDao;
import cz.jurina.tennisclub.tennisclub.dao.CustomerDao;
import cz.jurina.tennisclub.tennisclub.dao.ReservationDao;
import cz.jurina.tennisclub.tennisclub.dto.CreateReservationDto;
import cz.jurina.tennisclub.tennisclub.dto.ReservationDto;
import cz.jurina.tennisclub.tennisclub.entity.Court;
import cz.jurina.tennisclub.tennisclub.entity.CourtSurface;
import cz.jurina.tennisclub.tennisclub.entity.Customer;
import cz.jurina.tennisclub.tennisclub.entity.Reservation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceImplTest {

    @Mock
    private ReservationDao reservationDao;

    @Mock
    private CourtDao courtDao;

    @Mock
    private CustomerDao customerDao;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @Test
    void createReservationSuccess() {
        CreateReservationDto dto = new CreateReservationDto(
                1L, "Joe", "Joe", "777111222",
                LocalDateTime.of(2025, 6, 10, 10, 0),
                LocalDateTime.of(2025, 6, 10, 11, 0),
                false
        );

        CourtSurface surface = new CourtSurface();
        surface.setPrice(15.0);

        Court court = new Court();
        court.setId(1L);
        court.setCourtSurface(surface);

        when(courtDao.findById(1L)).thenReturn(court);
        when(customerDao.findByPhoneNumber("777111222")).thenReturn(null);
        doNothing().when(customerDao).save(any(Customer.class));
        when(reservationDao.existsOverlappingReservation(eq(1L), any(), any())).thenReturn(false);


        double price = reservationService.createReservation(dto);

        assertEquals(900.0, price);
        verify(reservationDao).save(any(Reservation.class));
    }

    @Test
    void createReservationFailure() {
        CreateReservationDto dto = new CreateReservationDto(
                1L, "Joe", "Joe", "777111222",
                LocalDateTime.of(2025, 6, 10, 10, 0),
                LocalDateTime.of(2025, 6, 10, 11, 0),
                false
        );

        Court court = new Court();
        court.setId(1L);
        court.setCourtSurface(new CourtSurface());

        when(courtDao.findById(1L)).thenReturn(court);
        lenient().when(customerDao.findByPhoneNumber("777111222")).thenReturn(new Customer());
        when(reservationDao.existsOverlappingReservation(eq(1L), any(), any())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> reservationService.createReservation(dto));
    }


    @Test
    void getReservationByCourtIdSuccess() {
        Court court = new Court();
        court.setId(1L);

        Customer customer = new Customer();
        customer.setName("Bar");
        customer.setSurname("Foo");
        customer.setPhone("123123123");

        Reservation res = new Reservation();
        res.setId(1L);
        res.setCourt(court);
        res.setCustomer(customer);
        res.setStartTime(LocalDateTime.of(2025, 6, 20, 14, 0));
        res.setEndTime(LocalDateTime.of(2025, 6, 20, 15, 0));
        res.setDouble(false);
        res.setReservationPrice(10.0);
        res.setDateCreated(LocalDateTime.now());

        when(reservationDao.findAllByCourtId(1L)).thenReturn(List.of(res));

        List<ReservationDto> result = reservationService.getReservationsByCourtId(1L);

        assertEquals(1, result.size());
        assertEquals("Bar", result.get(0).getCustomerName());
    }

    @Test
    void getReservationByCourtIdFailure() {
        when(reservationDao.findAllByCourtId(99L)).thenReturn(List.of());

        List<ReservationDto> result = reservationService.getReservationsByCourtId(99L);

        assertTrue(result.isEmpty());
    }


    @Test
    void getReservationsByPhoneNumberSuccess() {
        Customer customer = new Customer();
        customer.setName("Foo");
        customer.setSurname("Bar");
        customer.setPhone("111222333");

        Court court = new Court();
        court.setId(1L);

        Reservation res = new Reservation();
        res.setId(2L);
        res.setCustomer(customer);
        res.setCourt(court);
        res.setStartTime(LocalDateTime.of(2025, 7, 1, 12, 0));
        res.setEndTime(LocalDateTime.of(2025, 7, 1, 13, 0));
        res.setDouble(false);
        res.setReservationPrice(12.5);
        res.setDateCreated(LocalDateTime.now());

        when(reservationDao.findFutureByPhoneNumber("111222333")).thenReturn(List.of(res));

        List<ReservationDto> result = reservationService.getReservationsByPhoneNumber("111222333", true);

        assertEquals(1, result.size());
        assertEquals("Foo", result.get(0).getCustomerName());
    }

    @Test
    void getReservationsByPhoneNumberFailure() {
        assertThrows(IllegalArgumentException.class, () ->
                reservationService.getReservationsByPhoneNumber("INVALID", true)
        );
    }

    @Test
    void cancelReservationFailure() {
        Long invalidId = 99L;

        when(reservationDao.findById(invalidId)).thenReturn(null);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> reservationService.cancelReservation(invalidId)
        );

        assertEquals("Reservation with id 99 not found", exception.getMessage());
    }


    @Test
    void getAllReservation() {
        Reservation res = new Reservation();
        res.setId(1L);

        Customer customer = new Customer();
        customer.setName("Joe");
        customer.setSurname("Doe");
        customer.setPhone("777000111");
        res.setCustomer(customer);

        Court court = new Court();
        court.setId(2L);
        res.setCourt(court);

        res.setStartTime(LocalDateTime.of(2025, 8, 1, 9, 0));
        res.setEndTime(LocalDateTime.of(2025, 8, 1, 10, 0));
        res.setDouble(false);
        res.setReservationPrice(20.0);
        res.setDateCreated(LocalDateTime.now());

        when(reservationDao.findAll()).thenReturn(List.of(res));

        List<ReservationDto> result = reservationService.getAllReservations();

        assertEquals(1, result.size());
        assertEquals("Joe", result.get(0).getCustomerName());
        assertEquals(2L, result.get(0).getCourtId());
    }


}
