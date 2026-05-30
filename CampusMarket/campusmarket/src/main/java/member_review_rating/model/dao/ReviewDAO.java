package member_review_rating.model.dao;

import member_review_rating.model.dto.Review;
import member_review_rating.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {
    private static final String INSERT_REVIEW_SQL = "INSERT INTO reviews (reviewer_id, target_user_id, trade_id, rating, content) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_SQL = "SELECT r.review_id, r.reviewer_id, reviewer.username AS reviewer_name, r.target_user_id, target_user.username AS target_user_name, r.trade_id, r.rating, r.content, r.created_at FROM reviews r JOIN users reviewer ON r.reviewer_id = reviewer.user_id JOIN users target_user ON r.target_user_id = target_user.user_id ORDER BY r.created_at DESC";
    private static final String SELECT_BY_TARGET_SQL = "SELECT r.review_id, r.reviewer_id, reviewer.username AS reviewer_name, r.target_user_id, target_user.username AS target_user_name, r.trade_id, r.rating, r.content, r.created_at FROM reviews r JOIN users reviewer ON r.reviewer_id = reviewer.user_id JOIN users target_user ON r.target_user_id = target_user.user_id WHERE r.target_user_id = ? ORDER BY r.created_at DESC";
    private static final String SELECT_BY_ID_SQL = "SELECT r.review_id, r.reviewer_id, reviewer.username AS reviewer_name, r.target_user_id, target_user.username AS target_user_name, r.trade_id, r.rating, r.content, r.created_at FROM reviews r JOIN users reviewer ON r.reviewer_id = reviewer.user_id JOIN users target_user ON r.target_user_id = target_user.user_id WHERE r.review_id = ?";
    private static final String SELECT_BY_REVIEWER_AND_TRADE_SQL = "SELECT review_id FROM reviews WHERE reviewer_id = ? AND trade_id = ?";
    private static final String SELECT_BY_TRADE_SQL = "SELECT r.review_id, r.reviewer_id, reviewer.username AS reviewer_name, r.target_user_id, target_user.username AS target_user_name, r.trade_id, r.rating, r.content, r.created_at FROM reviews r JOIN users reviewer ON r.reviewer_id = reviewer.user_id JOIN users target_user ON r.target_user_id = target_user.user_id WHERE r.trade_id = ? ORDER BY r.created_at DESC";
    private static final String UPDATE_REVIEW_SQL = "UPDATE reviews SET rating = ?, content = ? WHERE review_id = ?";
    private static final String DELETE_REVIEW_SQL = "DELETE FROM reviews WHERE review_id = ?";
    private static final String SELECT_AVG_RATING_BY_TARGET_SQL = "SELECT AVG(rating) AS avg_rating FROM reviews WHERE target_user_id = ?";

    public boolean insertReview(Review review) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(INSERT_REVIEW_SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setLong(1, review.getReviewerId());
            pstmt.setLong(2, review.getTargetUserId());
            pstmt.setLong(3, review.getTradeId());
            pstmt.setInt(4, review.getRating());
            pstmt.setString(5, review.getContent());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    review.setReviewId(generatedKeys.getLong(1));
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Review findById(Long reviewId) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_BY_ID_SQL)) {
            pstmt.setLong(1, reviewId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToReview(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Review> findAllReviews() {
        List<Review> reviews = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                reviews.add(mapResultSetToReview(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    public List<Review> findReviewsByTargetUserId(Long targetUserId) {
        List<Review> reviews = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_BY_TARGET_SQL)) {
            pstmt.setLong(1, targetUserId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    reviews.add(mapResultSetToReview(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    public List<Review> findReviewsByTradeId(Long tradeId) {
        List<Review> reviews = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_BY_TRADE_SQL)) {
            pstmt.setLong(1, tradeId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    reviews.add(mapResultSetToReview(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    public boolean existsReview(Long reviewerId, Long tradeId) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_BY_REVIEWER_AND_TRADE_SQL)) {
            pstmt.setLong(1, reviewerId);
            pstmt.setLong(2, tradeId);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateReview(Review review) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(UPDATE_REVIEW_SQL)) {
            pstmt.setInt(1, review.getRating());
            pstmt.setString(2, review.getContent());
            pstmt.setLong(3, review.getReviewId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteReview(Long reviewId) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(DELETE_REVIEW_SQL)) {
            pstmt.setLong(1, reviewId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public double getAverageRatingByTargetUserId(Long targetUserId) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_AVG_RATING_BY_TARGET_SQL)) {
            pstmt.setLong(1, targetUserId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("avg_rating");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    private Review mapResultSetToReview(ResultSet rs) throws SQLException {
        Review review = new Review();
        review.setReviewId(rs.getLong("review_id"));
        review.setReviewerId(rs.getLong("reviewer_id"));
        review.setTargetUserId(rs.getLong("target_user_id"));
        review.setTradeId(rs.getLong("trade_id"));
        review.setRating(rs.getInt("rating"));
        review.setContent(rs.getString("content"));
        review.setReviewerName(rs.getString("reviewer_name"));
        review.setTargetUserName(rs.getString("target_user_name"));
        review.setCreatedAt(rs.getTimestamp("created_at"));
        return review;
    }
}
