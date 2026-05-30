<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>후기 작성 - CampusMarket</title>
</head>
<body>
<h1>후기 작성</h1>
<% String errorMessage = (String) request.getAttribute("errorMessage"); %>
<% if (errorMessage != null) { %>
    <p style="color:red;"><%= errorMessage %></p>
<% } %>
<form action="write" method="post">
    <label for="targetUserId">후기 대상 사용자 ID</label><br>
    <input type="number" id="targetUserId" name="targetUserId" required><br><br>

    <label for="tradeId">거래 ID</label><br>
    <input type="number" id="tradeId" name="tradeId" required><br><br>

    <label for="rating">평점 (1~5)</label><br>
    <select id="rating" name="rating" required>
        <option value="1">1</option>
        <option value="2">2</option>
        <option value="3">3</option>
        <option value="4">4</option>
        <option value="5">5</option>
    </select><br><br>

    <label for="content">후기 내용</label><br>
    <textarea id="content" name="content" rows="5" cols="50" required></textarea><br><br>

    <button type="submit">후기 등록</button>
</form>
<p><a href="list">후기 목록</a></p>
</body>
</html>
