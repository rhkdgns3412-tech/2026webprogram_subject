package hnu.ice.review.dao;

import hnu.ice.common.DBConnection;
import hnu.ice.review.model.Review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewDao {

    public List<Review> getAllReviews() {
        String sql = "SELECT review_id, writer_id, target_user_id, rating, content, created_at FROM reviews ORDER BY created_at DESC";
        List<Review> reviews = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Review review = new Review();
                review.setReviewId(rs.getInt("review_id"));
                review.setWriterId(rs.getString("writer_id"));
                review.setTargetUserId(rs.getString("target_user_id"));
                review.setRating(rs.getInt("rating"));
                review.setContent(rs.getString("content"));
                review.setCreatedAt(rs.getString("created_at"));
                reviews.add(review);
            }

            return reviews;
        } catch (SQLException e) {
            throw new RuntimeException("리뷰 목록 조회에 실패했습니다.", e);
        }
    }

    public boolean addReview(Review review) {
        String sql = "INSERT INTO reviews (writer_id, target_user_id, rating, content, created_at) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, review.getWriterId());
            pstmt.setString(2, review.getTargetUserId());
            pstmt.setInt(3, review.getRating());
            pstmt.setString(4, review.getContent());
            pstmt.setString(5, review.getCreatedAt());

            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            throw new RuntimeException("리뷰 등록에 실패했습니다.", e);
        }
    }

    public boolean deleteReview(int reviewId) {
        String sql = "DELETE FROM reviews WHERE review_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setInt(1, reviewId);
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            throw new RuntimeException("리뷰 삭제에 실패했습니다.", e);
        }
    }

    public double getAverageRating(String targetUserId) {
        String sql = "SELECT AVG(rating) AS avg_rating FROM reviews WHERE target_user_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, targetUserId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    double avg = rs.getDouble("avg_rating");
                    if (rs.wasNull()) {
                        return 0.0;
                    }
                    return avg;
                }
            }

            return 0.0;
        } catch (SQLException e) {
            throw new RuntimeException("평균 평점 조회에 실패했습니다.", e);
        }
    }

    public int getReviewCount(String targetUserId) {
        String sql = "SELECT COUNT(*) AS review_count FROM reviews WHERE target_user_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, targetUserId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("review_count");
                }
            }

            return 0;
        } catch (SQLException e) {
            throw new RuntimeException("리뷰 개수 조회에 실패했습니다.", e);
        }
    }
}
