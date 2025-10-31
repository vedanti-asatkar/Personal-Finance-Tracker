package personalfinancemanager.dao;

import java.sql.SQLException;
import personalfinancemanager.models.User;

public interface IUserDAO {
    User findByUsername(String username) throws SQLException;
    boolean save(User user) throws SQLException;
}