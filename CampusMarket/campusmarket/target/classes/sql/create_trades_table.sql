-- trades 테이블 생성 SQL
CREATE TABLE IF NOT EXISTS trades (
    trade_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    seller_id BIGINT NOT NULL,
    buyer_id BIGINT NOT NULL,
    item_name VARCHAR(200) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_trades_seller FOREIGN KEY (seller_id) REFERENCES users(user_id),
    CONSTRAINT fk_trades_buyer FOREIGN KEY (buyer_id) REFERENCES users(user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
