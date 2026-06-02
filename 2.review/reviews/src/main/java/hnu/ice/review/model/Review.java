package hnu.ice.review.model;

public class Review {
    private int reviewId;
    private String writerId;
    private String targetUserId;
    private int rating;
    private String content;
    private String createdAt;

    public Review() {
    }

    public Review(int reviewId, String writerId, String targetUserId, int rating, String content, String createdAt) {
        this.reviewId = reviewId;
        this.writerId = writerId;
        this.targetUserId = targetUserId;
        this.rating = rating;
        this.content = content;
        this.createdAt = createdAt;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public String getWriterId() {
        return writerId;
    }

    public void setWriterId(String writerId) {
        this.writerId = writerId;
    }

    public String getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(String targetUserId) {
        this.targetUserId = targetUserId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewId=" + reviewId +
                ", writerId='" + writerId + '\'' +
                ", targetUserId='" + targetUserId + '\'' +
                ", rating=" + rating +
                ", content='" + content + '\'' +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
