-- reviews 테이블 생성 SQL
CREATE TABLE IF NOT EXISTS reviews (
    review_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    reviewer_id BIGINT NOT NULL,
    target_user_id BIGINT NOT NULL,
    trade_id BIGINT NOT NULL,
    rating INT NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY unique_reviewer_trade (reviewer_id, trade_id),
    CONSTRAINT fk_reviews_reviewer FOREIGN KEY (reviewer_id) REFERENCES users(user_id),
    CONSTRAINT fk_reviews_target FOREIGN KEY (target_user_id) REFERENCES users(user_id),
    CONSTRAINT fk_reviews_trade FOREIGN KEY (trade_id) REFERENCES trades(trade_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
