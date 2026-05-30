package member_review_rating.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static final String DRIVER_CLASS = "org.h2.Driver";
    private static final String URL = "jdbc:h2:~/campusmarket;MODE=MySQL;AUTO_SERVER=TRUE";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";

    static {
        try {
            Class.forName(DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError("H2 JDBC Driver loading failed: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
