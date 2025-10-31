package personalfinancemanager.dao;

import java.util.List;

/**
 * An abstract base class for Data Access Objects (DAOs) to enforce a common
 * contract for CRUD (Create, Read, Update, Delete) operations.
 *
 * @param <T> The type of the model entity this DAO manages.
 */
public abstract class AbstractDAO<T> {

    public abstract boolean save(T entity);

    public abstract List<T> findAllByUserId(int userId);

    public abstract boolean delete(int id);
}