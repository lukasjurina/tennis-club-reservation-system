package cz.jurina.tennisclub.tennisclub.dao;

import cz.jurina.tennisclub.tennisclub.entity.CourtSurface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class CourtSurfaceDaoImpl implements CourtSurfaceDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(CourtSurface courtSurface) {
        if (courtSurface.getId() == null) {
            entityManager.persist(courtSurface);
        }else{
            entityManager.merge(courtSurface);
        }
        entityManager.flush();
    }

    @Override
    public void deleteById(Long id) {
        CourtSurface courtSurface = entityManager.find(CourtSurface.class, id);
        if (courtSurface != null) {
            courtSurface.setDeleted(true);
            entityManager.merge(courtSurface);
            entityManager.flush();
        }
    }

    @Override
    public CourtSurface findById(Long id) {
        return entityManager.find(CourtSurface.class, id);
    }

    @Override
    public List<CourtSurface> findAll() {
        return entityManager
                .createQuery("SELECT cs FROM CourtSurface cs WHERE cs.deleted = false", CourtSurface.class)
                .getResultList();
    }
}
