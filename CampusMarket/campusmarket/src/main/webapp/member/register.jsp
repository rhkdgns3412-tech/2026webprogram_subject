<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원가입 - CampusMarket</title>
</head>
<body>
<h1>회원가입</h1>
<% String errorMessage = (String) request.getAttribute("errorMessage"); %>
<% if (errorMessage != null) { %>
    <p style="color:red;"><%= errorMessage %></p>
<% } %>
<form action="register" method="post">
    <label for="username">아이디</label><br>
    <input type="text" id="username" name="username" required maxlength="50"><br><br>

    <label for="password">비밀번호</label><br>
    <input type="password" id="password" name="password" required maxlength="100"><br><br>

    <label for="nickname">닉네임</label><br>
    <input type="text" id="nickname" name="nickname" required maxlength="50"><br><br>

    <label for="email">이메일</label><br>
    <input type="email" id="email" name="email" required maxlength="100"><br><br>

    <button type="submit">회원가입</button>
</form>
<p><a href="login.jsp">로그인</a></p>
</body>
</html>
