package personalfinancemanager.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {
    private static final Properties properties = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream("config.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            // Throw a runtime exception if the config file is not found.
            // The application cannot proceed without it.
            throw new RuntimeException("FATAL: Could not load config.properties file. Make sure it's in the root directory.", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        try {
            // Explicitly register the driver. This is good practice for command-line apps.
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(properties.getProperty("db.url"),
                                               properties.getProperty("db.user"),
                                               properties.getProperty("db.password"));
        } catch (SQLException | ClassNotFoundException e) {
            // Catch ClassNotFoundException as well, which occurs if the JAR is missing,
            // and wrap it in a SQLException to provide a consistent exception type for the DAO layer.
            throw new SQLException("Failed to establish database connection. Ensure the database is running and the MySQL driver is on the classpath.", e);
        }
    }
}
