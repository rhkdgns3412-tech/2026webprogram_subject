package member_review_rating.model.service;

import member_review_rating.model.dao.UserDAO;
import member_review_rating.model.dto.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AuthService {
    private final UserDAO userDAO = new UserDAO();

    public boolean register(User user) {
        if (user == null || user.getUsername() == null || user.getPassword() == null) {
            return false;
        }
        if (userDAO.findByUsername(user.getUsername()) != null) {
            return false;
        }
        return userDAO.registerUser(user);
    }

    public User login(String username, String password) {
        if (username == null || password == null) {
            return null;
        }
        return userDAO.findByUsernameAndPassword(username, password);
    }

    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}
