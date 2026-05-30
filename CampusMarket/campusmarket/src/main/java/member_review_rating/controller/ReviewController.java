package member_review_rating.controller;

import member_review_rating.model.dto.Review;
import member_review_rating.model.dto.User;
import member_review_rating.model.service.RatingService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/review/*")
public class ReviewController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final RatingService ratingService = new RatingService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = extractAction(request);
        switch (action) {
            case "/write":
                request.getRequestDispatcher("/review/review-write.jsp").forward(request, response);
                break;
            case "/edit":
                handleEdit(request, response);
                break;
            case "/list":
                handleList(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/review/list");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = extractAction(request);
        if ("/write".equals(action)) {
            handleWrite(request, response);
        } else if ("/edit".equals(action)) {
            handleUpdate(request, response);
        } else if ("/delete".equals(action)) {
            handleDelete(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/review/list");
        }
    }

    private String extractAction(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        return uri.substring(contextPath.length() + "/review".length());
    }

    private void handleWrite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginUser") == null) {
            response.sendRedirect(request.getContextPath() + "/member/login.jsp");
            return;
        }

        User loginUser = (User) session.getAttribute("loginUser");
        String targetUserIdValue = request.getParameter("targetUserId");
        String tradeIdValue = request.getParameter("tradeId");
        String ratingValue = request.getParameter("rating");
        String content = request.getParameter("content");

        Long targetUserId = null;
        Long tradeId = null;
        int rating = 0;
        try {
            targetUserId = Long.parseLong(targetUserIdValue);
            tradeId = Long.parseLong(tradeIdValue);
            rating = Integer.parseInt(ratingValue);
        } catch (NumberFormatException ignored) {
        }

        Review review = new Review(loginUser.getUserId(), targetUserId, tradeId, rating, content);
        boolean success = ratingService.addReview(review);
        if (success) {
            response.sendRedirect(request.getContextPath() + "/review/list?targetUserId=" + targetUserId);
        } else {
            request.setAttribute("errorMessage", "후기 등록에 실패했습니다. 평점은 1~5점이어야 하며, 동일 사용자는 중복 작성할 수 없습니다.");
            request.getRequestDispatcher("/review/review-write.jsp").forward(request, response);
        }
    }

    private void handleEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginUser") == null) {
            response.sendRedirect(request.getContextPath() + "/member/login.jsp");
            return;
        }

        User loginUser = (User) session.getAttribute("loginUser");
        String reviewIdValue = request.getParameter("reviewId");
        Long reviewId = null;
        try {
            reviewId = Long.parseLong(reviewIdValue);
        } catch (NumberFormatException ignored) {
        }

        Review review = ratingService.getReviewById(reviewId);
        if (review == null || !review.getReviewerId().equals(loginUser.getUserId())) {
            response.sendRedirect(request.getContextPath() + "/review/list");
            return;
        }

        request.setAttribute("review", review);
        request.getRequestDispatcher("/review/review-edit.jsp").forward(request, response);
    }

    private void handleUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginUser") == null) {
            response.sendRedirect(request.getContextPath() + "/member/login.jsp");
            return;
        }

        User loginUser = (User) session.getAttribute("loginUser");
        String reviewIdValue = request.getParameter("reviewId");
        String ratingValue = request.getParameter("rating");
        String content = request.getParameter("content");

        Long reviewId = null;
        int rating = 0;
        try {
            reviewId = Long.parseLong(reviewIdValue);
            rating = Integer.parseInt(ratingValue);
        } catch (NumberFormatException ignored) {
        }

        Review review = new Review();
        review.setReviewId(reviewId);
        review.setRating(rating);
        review.setContent(content);

        boolean success = ratingService.updateReview(review, loginUser.getUserId());
        if (success) {
            Review savedReview = ratingService.getReviewById(reviewId);
            response.sendRedirect(request.getContextPath() + "/review/list?targetUserId=" + savedReview.getTargetUserId());
        } else {
            request.setAttribute("errorMessage", "후기 수정에 실패했습니다. 평점은 1~5점이어야 하며, 본인 작성 후기만 수정할 수 있습니다.");
            request.setAttribute("review", ratingService.getReviewById(reviewId));
            request.getRequestDispatcher("/review/review-edit.jsp").forward(request, response);
        }
    }

    private void handleDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginUser") == null) {
            response.sendRedirect(request.getContextPath() + "/member/login.jsp");
            return;
        }

        User loginUser = (User) session.getAttribute("loginUser");
        String reviewIdValue = request.getParameter("reviewId");
        Long reviewId = null;
        try {
            reviewId = Long.parseLong(reviewIdValue);
        } catch (NumberFormatException ignored) {
        }

        ratingService.deleteReview(reviewId, loginUser.getUserId());
        response.sendRedirect(request.getContextPath() + "/review/list");
    }

    private void handleList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String targetUserIdValue = request.getParameter("targetUserId");
        String tradeIdValue = request.getParameter("tradeId");
        Long targetUserId = null;
        Long tradeId = null;
        if (targetUserIdValue != null && !targetUserIdValue.isEmpty()) {
            try {
                targetUserId = Long.parseLong(targetUserIdValue);
            } catch (NumberFormatException ignored) {
            }
        }
        if (tradeIdValue != null && !tradeIdValue.isEmpty()) {
            try {
                tradeId = Long.parseLong(tradeIdValue);
            } catch (NumberFormatException ignored) {
            }
        }

        List<Review> reviews;
        double averageRating = 0.0;
        if (tradeId != null) {
            reviews = ratingService.getReviewsForTrade(tradeId);
        } else {
            reviews = ratingService.getReviewsForUser(targetUserId);
            if (targetUserId != null) {
                averageRating = ratingService.getAverageRating(targetUserId);
            }
        }

        request.setAttribute("reviews", reviews);
        request.setAttribute("averageRating", averageRating);
        request.setAttribute("targetUserId", targetUserId);
        request.setAttribute("tradeId", tradeId);
        request.getRequestDispatcher("/review/review-list.jsp").forward(request, response);
    }
}
