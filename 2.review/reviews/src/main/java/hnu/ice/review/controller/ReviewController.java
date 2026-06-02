package hnu.ice.review.controller;

import hnu.ice.review.dao.ReviewDao;
import hnu.ice.review.model.Review;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/review")
public class ReviewController extends HttpServlet {
    private final ReviewDao reviewDao = new ReviewDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        switch (action) {
            case "list":
                handleList(request, response);
                break;
            case "delete":
                handleDelete(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        switch (action) {
            case "write":
                handleWrite(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                break;
        }
    }

    private void handleList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Review> reviews = reviewDao.getAllReviews();
        request.setAttribute("reviews", reviews);
        request.getRequestDispatcher("/review-list.jsp").forward(request, response);
    }

    private void handleWrite(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        try {
            String writerId = (String) session.getAttribute("loginUser");
            String targetUserId = request.getParameter("targetUserId");
            int rating = Integer.parseInt(request.getParameter("rating"));
            String content = request.getParameter("content");
            String createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            Review review = new Review();
            review.setWriterId(writerId);
            review.setTargetUserId(targetUserId);
            review.setRating(rating);
            review.setContent(content);
            review.setCreatedAt(createdAt);

            reviewDao.addReview(review);
            response.sendRedirect(request.getContextPath() + "/review?action=list");
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/review-write.jsp?error=1");
        }
    }

    private void handleDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        try {
            int reviewId = Integer.parseInt(request.getParameter("reviewId"));
            reviewDao.deleteReview(reviewId);
            response.sendRedirect(request.getContextPath() + "/review?action=list");
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/review?action=list");
        }
    }
}
