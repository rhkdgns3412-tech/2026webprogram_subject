package hnu.ice.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    private static final String URL = "jdbc:h2:./data/reviewdb;MODE=MySQL;DATABASE_TO_UPPER=false";
    private static final String USER = "sa";
    private static final String PASSWORD = "";
    private static boolean initialized = false;

    private DBConnection() {
        // utility class
    }

    public static Connection getConnection() {
        try {
            Class.forName("org.h2.Driver");
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            
            if (!initialized) {
                initDatabase(connection);
                initialized = true;
            }
            
            return connection;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("H2 JDBC Driver not found.", e);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to obtain database connection.", e);
        }
    }

    public static void initDatabase(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            // Create users table
            stmt.execute(
                "CREATE TABLE IF NOT EXISTS users (" +
                "  user_id VARCHAR(50) PRIMARY KEY," +
                "  password VARCHAR(100) NOT NULL," +
                "  nickname VARCHAR(50) NOT NULL," +
                "  email VARCHAR(100)," +
                "  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")"
            );

            // Create reviews table
            stmt.execute(
                "CREATE TABLE IF NOT EXISTS reviews (" +
                "  review_id INT AUTO_INCREMENT PRIMARY KEY," +
                "  writer_id VARCHAR(50) NOT NULL," +
                "  target_user_id VARCHAR(50) NOT NULL," +
                "  rating INT NOT NULL CHECK (rating BETWEEN 1 AND 5)," +
                "  content TEXT NOT NULL," +
                "  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "  FOREIGN KEY (writer_id) REFERENCES users(user_id)," +
                "  FOREIGN KEY (target_user_id) REFERENCES users(user_id)" +
                ")"
            );
        }
    }
}
