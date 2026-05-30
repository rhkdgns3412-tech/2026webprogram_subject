<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="member_review_rating.model.dto.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>마이페이지 - CampusMarket</title>
</head>
<body>
<%
    User loginUser = (User) session.getAttribute("loginUser");
    if (loginUser == null) {
        response.sendRedirect(request.getContextPath() + "/member/login.jsp");
        return;
    }
%>
<h1>마이페이지</h1>
<p>안녕하세요, <strong><%= loginUser.getNickname() %></strong>님.</p>
<ul>
    <li>아이디: <%= loginUser.getUsername() %></li>
    <li>닉네임: <%= loginUser.getNickname() %></li>
    <li>이메일: <%= loginUser.getEmail() %></li>
    <li>가입일: <%= loginUser.getCreatedAt() %></li>
</ul>
<p><a href="<%= request.getContextPath() %>/member/logout">로그아웃</a>
</body>
</html>
