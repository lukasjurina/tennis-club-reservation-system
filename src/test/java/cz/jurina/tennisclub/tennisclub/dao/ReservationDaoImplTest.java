package cz.jurina.tennisclub.tennisclub.dao;

import cz.jurina.tennisclub.tennisclub.entity.*;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(ReservationDaoImpl.class)
class ReservationDaoImplTest {

    @Autowired
    private ReservationDao reservationDao;

    @Autowired
    private EntityManager entityManager;

    private Customer customer;
    private Court court;

    @BeforeEach
    void setUp() {
        CourtSurface surface = new CourtSurface();
        surface.setSurfaceType("Clay");
        surface.setPrice(10.0);
        entityManager.persist(surface);

        court = new Court();
        court.setCourtSurface(surface);
        entityManager.persist(court);

        customer = new Customer();
        customer.setName("Lukas");
        customer.setSurname("Jurina");
        customer.setPhone("777111222");
        entityManager.persist(customer);

        entityManager.flush();
    }

    @Test
    void testSaveAndFindById() {
        Reservation res = createReservation(false);
        reservationDao.save(res);

        Reservation found = reservationDao.findById(res.getId());
        assertNotNull(found);
        assertEquals(customer.getPhone(), found.getCustomer().getPhone());
    }

    @Test
    void testFindAll() {
        Reservation r1 = createReservation(false);
        Reservation r2 = createReservation(true);
        reservationDao.save(r1);
        reservationDao.save(r2);

        List<Reservation> all = reservationDao.findAll();
        assertEquals(2, all.size());
    }

    @Test
    void testFindAllByCourtId() {
        Reservation res = createReservation(false);
        reservationDao.save(res);

        List<Reservation> list = reservationDao.findAllByCourtId(court.getId());
        assertEquals(1, list.size());
        assertEquals(court.getId(), list.get(0).getCourt().getId());
    }

    @Test
    void testFindFutureByPhoneNumber() {
        Reservation res = createReservation(false);
        reservationDao.save(res);

        List<Reservation> list = reservationDao.findFutureByPhoneNumber("777111222");
        assertEquals(1, list.size());
    }

    @Test
    void testExistsOverlappingReservationReturnsTrue() {
        Reservation res = createReservation(false);
        reservationDao.save(res);

        boolean exists = reservationDao.existsOverlappingReservation(
                court.getId(),
                res.getStartTime().minusMinutes(15),
                res.getEndTime().plusMinutes(15)
        );

        assertTrue(exists);
    }

    @Test
    void testExistsOverlappingReservationReturnsFalse() {
        Reservation res = createReservation(false);
        reservationDao.save(res);

        boolean exists = ((ReservationDaoImpl) reservationDao).existsOverlappingReservation(
                court.getId(),
                res.getEndTime().plusMinutes(1),
                res.getEndTime().plusMinutes(30)
        );

        assertFalse(exists);
    }

    @Test
    void testDeleteByIdMarksAsDeleted() {
        Reservation res = createReservation(false);
        reservationDao.save(res);

        reservationDao.deleteById(res.getId());

        Reservation deleted = reservationDao.findById(res.getId());
        assertTrue(deleted.isDeleted());
    }

    private Reservation createReservation(boolean isDouble) {
        Reservation reservation = new Reservation();
        reservation.setCustomer(customer);
        reservation.setCourt(court);
        reservation.setStartTime(LocalDateTime.now().plusHours(2));
        reservation.setEndTime(LocalDateTime.now().plusHours(3));
        reservation.setDouble(isDouble);
        reservation.setDeleted(false);
        reservation.setReservationPrice(100.0);
        return reservation;
    }
}
