package cz.jurina.tennisclub.tennisclub.dao;

import cz.jurina.tennisclub.tennisclub.entity.Customer;


/**
 * Data Access Object interface for managing Customer entities.
 *
 * Extends the BaseDao
 *
 * @author Lukas Jurina
 */
public interface CustomerDao extends BaseDao<Customer>{

    /**
     * Retrieves the customer with the given phone number.
     *
     * @param phoneNumber the phone number of the customer to find
     * @return the customer if found, null otherwise
     */
    Customer findByPhoneNumber(String phoneNumber);

}
