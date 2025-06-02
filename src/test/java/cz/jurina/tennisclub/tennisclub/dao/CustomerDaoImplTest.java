package cz.jurina.tennisclub.tennisclub.dao;

import cz.jurina.tennisclub.tennisclub.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(CustomerDaoImpl.class)
class CustomerDaoImplTest {

    @Autowired
    private CustomerDao customerDao;

    @Test
    void testSaveAndFindById() {
        Customer customer = new Customer();
        customer.setName("Foo");
        customer.setSurname("Bar");
        customer.setPhone("777111222");
        customer.setDeleted(false);

        customerDao.save(customer);
        assertNotNull(customer.getId());

        Customer found = customerDao.findById(customer.getId());
        assertEquals("Foo", found.getName());
        assertEquals("777111222", found.getPhone());
    }

    @Test
    void testFindByPhoneNumber() {
        Customer customer = new Customer();
        customer.setName("Bar");
        customer.setSurname("Foo");
        customer.setPhone("123456789");
        customer.setDeleted(false);

        customerDao.save(customer);

        Customer found = customerDao.findByPhoneNumber("123456789");
        assertNotNull(found);
        assertEquals("Bar", found.getName());
    }

    @Test
    void testDeleteById() {
        Customer customer = new Customer();
        customer.setName("Delete");
        customer.setSurname("Me");
        customer.setPhone("000000000");
        customer.setDeleted(false);

        customerDao.save(customer);
        Long id = customer.getId();

        customerDao.deleteById(id);

        Customer deleted = customerDao.findById(id);
        assertTrue(deleted.isDeleted());
    }

    @Test
    void testFindAllExcludesDeleted() {
        Customer active = new Customer();
        active.setName("Active");
        active.setSurname("User");
        active.setPhone("111222333");
        active.setDeleted(false);
        customerDao.save(active);

        Customer deleted = new Customer();
        deleted.setName("Deleted");
        deleted.setSurname("User");
        deleted.setPhone("444555666");
        deleted.setDeleted(true);
        customerDao.save(deleted);

        List<Customer> customers = customerDao.findAll();
        assertEquals(1, customers.size());
        assertEquals("Active", customers.get(0).getName());
    }
}
