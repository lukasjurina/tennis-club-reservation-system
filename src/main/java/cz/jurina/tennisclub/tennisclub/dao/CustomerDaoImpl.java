package cz.jurina.tennisclub.tennisclub.dao;

import cz.jurina.tennisclub.tennisclub.entity.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class CustomerDaoImpl implements CustomerDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Customer findByPhoneNumber(String phoneNumber) {
        return entityManager
                .createQuery("SELECT c FROM Customer c WHERE c.phone = :phoneNumber AND c.deleted = false", Customer.class)
                .setParameter("phoneNumber", phoneNumber)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public void save(Customer entity) {
        if (entity.getId() == null) {
            entityManager.persist(entity);
            return;
        }
        entityManager.merge(entity);
    }

    @Override
    public void deleteById(Long id) {
        Customer entity = entityManager.find(Customer.class, id);
        if (entity != null) {
            entity.setDeleted(true);
            entityManager.merge(entity);
            entityManager.flush();
        }

    }

    @Override
    public Customer findById(Long id) {
        return entityManager.find(Customer.class, id);
    }

    @Override
    public List<Customer> findAll() {
        return entityManager
                .createQuery("Select c from Customer c WHERE c.deleted = false", Customer.class)
                .getResultList();
    }
}
