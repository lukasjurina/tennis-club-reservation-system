package cz.jurina.tennisclub.tennisclub.dao;

import java.util.List;

/**
 * Basic Data Object Interface providing basic CRUD operations
 *
 * @param <T> the type of entity this DAO manages
 *
 * @author Lukas Jurina
 */
public interface BaseDao<T> {

    /**
     * Persists the given entity to the database.
     * Can be used as SELECT as well as UPDATE
     *
     * @param entity the entity to be saved
     */
    void save (T entity);

    /**
     * Marks the entity with the given ID as deleted.
     *
     * @param id the ID of the entity to delete
     */
    void deleteById(Long id);


    /**
     * Retrieves the entity with the given ID.
     *
     * @param id the ID of the entity to find
     * @return the entity if found, null otherwise
     */
    T findById(Long id);

    /**
     * Returns all entities that are not marked as deleted.
     *
     * @return a list of all active entities, empty list otherwise
     */
    List<T> findAll();
}
