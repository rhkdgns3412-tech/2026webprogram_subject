<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>로그인 - CampusMarket</title>
</head>
<body>
<h1>로그인</h1>
<% String errorMessage = (String) request.getAttribute("errorMessage"); %>
<% if (errorMessage != null) { %>
    <p style="color:red;"><%= errorMessage %></p>
<% } %>
<form action="<%= request.getContextPath() %>/member/login" method="post">
    <label for="username">아이디</label><br>
    <input type="text" id="username" name="username" required maxlength="50"><br><br>

    <label for="password">비밀번호</label><br>
    <input type="password" id="password" name="password" required maxlength="100"><br><br>

    <button type="submit">로그인</button>
</form>
<p><a href="register.jsp">회원가입</a></p>
</body>
</html>
