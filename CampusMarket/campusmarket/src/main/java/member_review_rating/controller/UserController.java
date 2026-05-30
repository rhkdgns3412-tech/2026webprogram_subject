package member_review_rating.controller;

import member_review_rating.model.dto.User;
import member_review_rating.model.service.AuthService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/member/*")
public class UserController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final AuthService authService = new AuthService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = extractAction(request);
        switch (action) {
            case "/register":
                request.getRequestDispatcher("/member/register.jsp").forward(request, response);
                break;
            case "/login":
                request.getRequestDispatcher("/member/login.jsp").forward(request, response);
                break;
            case "/logout":
                authService.logout(request);
                response.sendRedirect(request.getContextPath() + "/member/login.jsp");
                break;
            case "/mypage":
                HttpSession session = request.getSession(false);
                if (session == null || session.getAttribute("loginUser") == null) {
                    response.sendRedirect(request.getContextPath() + "/member/login.jsp");
                } else {
                    request.getRequestDispatcher("/member/mypage.jsp").forward(request, response);
                }
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/member/login.jsp");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = extractAction(request);
        if ("/register".equals(action)) {
            handleRegister(request, response);
        } else if ("/login".equals(action)) {
            handleLogin(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/member/login.jsp");
        }
    }

    private String extractAction(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        return uri.substring(contextPath.length() + "/member".length());
    }

    private void handleRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String nickname = request.getParameter("nickname");
        String email = request.getParameter("email");

        User user = new User(username, password, nickname, email);
        boolean success = authService.register(user);
        if (success) {
            response.sendRedirect(request.getContextPath() + "/member/login.jsp");
        } else {
            request.setAttribute("errorMessage", "회원가입에 실패했습니다. 사용자명 또는 이메일이 이미 존재하는지 확인해 주세요.");
            request.getRequestDispatcher("/member/register.jsp").forward(request, response);
        }
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = authService.login(username, password);
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", user);
            response.sendRedirect(request.getContextPath() + "/member/mypage");
        } else {
            request.setAttribute("errorMessage", "로그인 정보가 올바르지 않습니다.");
            request.getRequestDispatcher("/member/login").forward(request, response);
        }
    }
}
