package member_review_rating.model.service;

import member_review_rating.model.dao.ReviewDAO;
import member_review_rating.model.dto.Review;

import java.util.List;

public class RatingService {
    private final ReviewDAO reviewDAO = new ReviewDAO();

    public boolean addReview(Review review) {
        if (review == null) {
            return false;
        }
        if (review.getReviewerId() == null || review.getTargetUserId() == null || review.getTradeId() == null) {
            return false;
        }
        if (review.getReviewerId().equals(review.getTargetUserId())) {
            return false;
        }
        if (review.getRating() < 1 || review.getRating() > 5) {
            return false;
        }
        if (reviewDAO.existsReview(review.getReviewerId(), review.getTradeId())) {
            return false;
        }
        return reviewDAO.insertReview(review);
    }

    public Review getReviewById(Long reviewId) {
        if (reviewId == null) {
            return null;
        }
        return reviewDAO.findById(reviewId);
    }

    public boolean updateReview(Review review, Long loginUserId) {
        if (review == null || review.getReviewId() == null || loginUserId == null) {
            return false;
        }
        Review existingReview = reviewDAO.findById(review.getReviewId());
        if (existingReview == null || !existingReview.getReviewerId().equals(loginUserId)) {
            return false;
        }
        if (review.getRating() < 1 || review.getRating() > 5) {
            return false;
        }
        existingReview.setRating(review.getRating());
        existingReview.setContent(review.getContent());
        return reviewDAO.updateReview(existingReview);
    }

    public boolean deleteReview(Long reviewId, Long loginUserId) {
        if (reviewId == null || loginUserId == null) {
            return false;
        }
        Review existingReview = reviewDAO.findById(reviewId);
        if (existingReview == null || !existingReview.getReviewerId().equals(loginUserId)) {
            return false;
        }
        return reviewDAO.deleteReview(reviewId);
    }

    public List<Review> getAllReviews() {
        return reviewDAO.findAllReviews();
    }

    public List<Review> getReviewsForUser(Long targetUserId) {
        if (targetUserId == null) {
            return reviewDAO.findAllReviews();
        }
        return reviewDAO.findReviewsByTargetUserId(targetUserId);
    }

    public List<Review> getReviewsForTrade(Long tradeId) {
        if (tradeId == null) {
            return reviewDAO.findAllReviews();
        }
        return reviewDAO.findReviewsByTradeId(tradeId);
    }

    public double getAverageRating(Long targetUserId) {
        if (targetUserId == null) {
            return 0.0;
        }
        return reviewDAO.getAverageRatingByTargetUserId(targetUserId);
    }
}
