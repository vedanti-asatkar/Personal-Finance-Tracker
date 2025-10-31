package personalfinancemanager.auth;

import java.sql.SQLException;
import personalfinancemanager.dao.UserDAO;
import personalfinancemanager.models.User;
import personalfinancemanager.util.ConsoleInput;
import personalfinancemanager.util.HashUtil;

public class AuthManager {
    private final UserDAO userDAO = new UserDAO();

    /**
     * Login method for the console application.
     */
    public User login() {
        try {
            String username = ConsoleInput.readString("Username: ");
            String password = ConsoleInput.readString("Password: ");
            User user = login(username, password);
            if (user != null) {
                System.out.println("[OK] Login successful. Welcome, " + user.getUsername() + "!");
            } else {
                System.out.println("[X] Invalid credentials.");
            }
            return user;
        } catch (SQLException e) {
            System.err.println("Database error during login: " + e.getMessage());
            return null;
        }
    }

    /**
     * Overloaded login method for GUI.
     */
    public User login(String username, String password) throws SQLException {
        User user = userDAO.findByUsername(username);
        if (user != null && user.getPasswordHash().equals(HashUtil.hashPassword(password))) {
            return user;
        } else {
            return null;
        }
    }

    /**
     * Register method for the console application.
     */
    public boolean register() {
        try {
            String username = ConsoleInput.readString("Choose username: ");
            if (userDAO.findByUsername(username) != null) {
                System.out.println("[X] Username already exists. Try another.");
                return false;
            }
            String password = ConsoleInput.readString("Choose password: ");
            RegistrationResult result = register(username, password);
            boolean success = (result == RegistrationResult.SUCCESS);
            if (success) {
                System.out.println("[OK] Registration successful. Please login.");
            }
            return success;
        } catch (SQLException e) {
            System.err.println("Database error during registration: " + e.getMessage());
            return false;
        }
    }

    /**
     * Overloaded register method for GUI.
     */
    public RegistrationResult register(String username, String password) {
        try {
            if (userDAO.findByUsername(username) != null) {
                return RegistrationResult.USER_ALREADY_EXISTS;
            }
            String hash = HashUtil.hashPassword(password);
            User newUser = new User(0, username, hash);
            boolean success = userDAO.save(newUser);
            return success ? RegistrationResult.SUCCESS : RegistrationResult.DATABASE_ERROR;
        } catch (SQLException e) {
            // This catch block will be triggered if the DB connection fails.
            System.err.println("Database error during registration check: " + e.getMessage());
            return RegistrationResult.DATABASE_ERROR;
        }
    }
}
