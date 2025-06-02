package cz.jurina.tennisclub.tennisclub.dao;

import cz.jurina.tennisclub.tennisclub.entity.Reservation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
public class ReservationDaoImpl implements ReservationDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Reservation> findFutureByPhoneNumber(String phoneNumber) {
        return entityManager
                .createQuery("SELECT r FROM Reservation r WHERE r.customer.phone = :phone AND r.startTime > CURRENT_TIMESTAMP AND r.deleted = false", Reservation.class)
                .setParameter("phone", phoneNumber)
                .getResultList();
    }

    @Override
    public List<Reservation> findAllByCourtId(Long courtId) {
        return entityManager
                .createQuery("SELECT r FROM Reservation r WHERE r.court.id = :courtId AND r.deleted = false ORDER BY r.startTime ASC", Reservation.class)
                .setParameter("courtId", courtId)
                .getResultList();
    }

    @Override
    public void save(Reservation entity) {
        if (entity.getId() == null) {
            entityManager.persist(entity);
            return;
        }
        entityManager.merge(entity);
    }

    @Override
    public void deleteById(Long id) {
        Reservation reservation = entityManager.find(Reservation.class, id);
        if (reservation != null) {
            reservation.setDeleted(true);
            entityManager.merge(reservation);
            entityManager.flush();
        }
    }

    @Override
    public Reservation findById(Long id) {
        return entityManager.find(Reservation.class, id);
    }

    @Override
    public List<Reservation> findAll() {
        return entityManager
                .createQuery("SELECT r FROM Reservation r WHERE r.deleted = false", Reservation.class)
                .getResultList();
    }

    public boolean existsOverlappingReservation(Long courtId, LocalDateTime start, LocalDateTime end) {

        Long count = entityManager
                .createQuery("SELECT COUNT(r) FROM Reservation r WHERE r.court.id = :courtId AND r.deleted = false AND (:start < r.endTime AND :end > r.startTime)", Long.class)
                .setParameter("courtId", courtId)
                .setParameter("start", start)
                .setParameter("end", end)
                .getSingleResult();

        return count > 0;
    }
}
