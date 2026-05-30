package member_review_rating.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    private static final String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS users (\n" +
            "    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
            "    username VARCHAR(50) NOT NULL UNIQUE,\n" +
            "    password VARCHAR(100) NOT NULL,\n" +
            "    nickname VARCHAR(50) NOT NULL,\n" +
            "    email VARCHAR(100) NOT NULL UNIQUE,\n" +
            "    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";

    private static final String CREATE_TRADES_TABLE = "CREATE TABLE IF NOT EXISTS trades (\n" +
            "    trade_id BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
            "    seller_id BIGINT NOT NULL,\n" +
            "    buyer_id BIGINT NOT NULL,\n" +
            "    item_name VARCHAR(200) NOT NULL,\n" +
            "    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n" +
            "    CONSTRAINT fk_trades_seller FOREIGN KEY (seller_id) REFERENCES users(user_id),\n" +
            "    CONSTRAINT fk_trades_buyer FOREIGN KEY (buyer_id) REFERENCES users(user_id)\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";

    private static final String CREATE_REVIEWS_TABLE = "CREATE TABLE IF NOT EXISTS reviews (\n" +
            "    review_id BIGINT AUTO_INCREMENT PRIMARY KEY,\n" +
            "    reviewer_id BIGINT NOT NULL,\n" +
            "    target_user_id BIGINT NOT NULL,\n" +
            "    trade_id BIGINT NOT NULL,\n" +
            "    rating INT NOT NULL,\n" +
            "    content TEXT NOT NULL,\n" +
            "    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\n" +
            "    UNIQUE KEY unique_reviewer_trade (reviewer_id, trade_id),\n" +
            "    CONSTRAINT fk_reviews_reviewer FOREIGN KEY (reviewer_id) REFERENCES users(user_id),\n" +
            "    CONSTRAINT fk_reviews_target FOREIGN KEY (target_user_id) REFERENCES users(user_id),\n" +
            "    CONSTRAINT fk_reviews_trade FOREIGN KEY (trade_id) REFERENCES trades(trade_id)\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";

    public static void initializeDatabase() {
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(CREATE_USERS_TABLE);
            stmt.execute(CREATE_TRADES_TABLE);
            stmt.execute(CREATE_REVIEWS_TABLE);

            System.out.println("Database tables initialized successfully!");

        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
