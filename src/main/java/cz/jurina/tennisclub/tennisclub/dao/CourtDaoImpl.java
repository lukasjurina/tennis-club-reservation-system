package cz.jurina.tennisclub.tennisclub.dao;

import cz.jurina.tennisclub.tennisclub.entity.Court;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class CourtDaoImpl implements CourtDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void save(Court court) {
        if (court.getId() == null) {
            entityManager.persist(court);
        }else{
            entityManager.merge(court);
        }

        entityManager.flush();

    }

    @Override
    public void deleteById(Long id) {
        Court court = entityManager.find(Court.class, id);
        if (court != null) {
            court.setDeleted(true);
            entityManager.merge(court);
            entityManager.flush();
        }
    }

    @Override
    public Court findById(Long id) {
        return entityManager.find(Court.class, id);
    }

    @Override
    public List<Court> findAll() {
        return entityManager
                .createQuery("SELECT c FROM Court c WHERE c.deleted = false", Court.class)
                .getResultList();
    }
}
