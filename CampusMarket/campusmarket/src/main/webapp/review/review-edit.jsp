<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="member_review_rating.model.dto.Review" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>후기 수정 - CampusMarket</title>
</head>
<body>
<h1>후기 수정</h1>
<% String errorMessage = (String) request.getAttribute("errorMessage"); %>
<% if (errorMessage != null) { %>
    <p style="color:red;"><%= errorMessage %></p>
<% } %>
<% Review review = (Review) request.getAttribute("review"); %>
<% if (review == null) { %>
    <p>수정할 후기를 찾을 수 없습니다.</p>
    <p><a href="list">목록으로 돌아가기</a></p>
<% } else { %>
    <p>후기 대상: <%= review.getTargetUserName() %> (<%= review.getTargetUserId() %>)</p>
    <p>거래 ID: <%= review.getTradeId() %></p>
    <form action="edit" method="post">
        <input type="hidden" name="reviewId" value="<%= review.getReviewId() %>">

        <label for="rating">평점 (1~5)</label><br>
        <select id="rating" name="rating" required>
            <option value="1" <%= review.getRating() == 1 ? "selected" : "" %>>1</option>
            <option value="2" <%= review.getRating() == 2 ? "selected" : "" %>>2</option>
            <option value="3" <%= review.getRating() == 3 ? "selected" : "" %>>3</option>
            <option value="4" <%= review.getRating() == 4 ? "selected" : "" %>>4</option>
            <option value="5" <%= review.getRating() == 5 ? "selected" : "" %>>5</option>
        </select><br><br>

        <label for="content">후기 내용</label><br>
        <textarea id="content" name="content" rows="5" cols="50" required><%= review.getContent() %></textarea><br><br>

        <button type="submit">수정 완료</button>
    </form>
    <p><a href="list?targetUserId=<%= review.getTargetUserId() %>">목록으로 돌아가기</a></p>
<% } %>
</body>
</html>
