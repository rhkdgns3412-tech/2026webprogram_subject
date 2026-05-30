<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="member_review_rating.model.dto.Review" %>
<%@ page import="member_review_rating.model.dto.User" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>후기 목록 - CampusMarket</title>
</head>
<body>
<h1>후기 목록</h1>
<% Double averageRating = (Double) request.getAttribute("averageRating"); %>
<% Long targetUserId = (Long) request.getAttribute("targetUserId"); %>
<% Long tradeId = (Long) request.getAttribute("tradeId"); %>
<% User loginUser = (User) session.getAttribute("loginUser"); %>
<% if (tradeId != null) { %>
    <p>거래 ID: <%= tradeId %> 에 대한 후기 목록입니다.</p>
<% } else if (targetUserId != null) { %>
    <p>대상 사용자 ID: <%= targetUserId %></p>
    <p>평균 평점: <%= String.format("%.2f", averageRating) %></p>
<% } else { %>
    <p>전체 후기 목록입니다.</p>
<% } %>

<form action="list" method="get">
    <label for="targetUserId">특정 사용자 후기 조회 (ID)</label>
    <input type="number" id="targetUserId" name="targetUserId" value="<%= targetUserId != null ? targetUserId : "" %>">
    <label for="tradeId">거래 ID로 조회</label>
    <input type="number" id="tradeId" name="tradeId" value="<%= tradeId != null ? tradeId : "" %>">
    <button type="submit">조회</button>
</form>

<p><a href="write">후기 작성</a></p>

<% List<Review> reviews = (List<Review>) request.getAttribute("reviews"); %>
<% if (reviews == null || reviews.isEmpty()) { %>
    <p>등록된 후기가 없습니다.</p>
<% } else { %>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>후기ID</th>
            <th>작성자</th>
            <th>대상 사용자</th>
            <th>거래ID</th>
            <th>평점</th>
            <th>내용</th>
            <th>작성일</th>
            <th>관리</th>
        </tr>
        </thead>
        <tbody>
        <% for (Review review : reviews) { %>
            <tr>
                <td><%= review.getReviewId() %></td>
                <td><%= review.getReviewerName() %> (<%= review.getReviewerId() %>)</td>
                <td><%= review.getTargetUserName() %> (<%= review.getTargetUserId() %>)</td>
                <td><%= review.getTradeId() %></td>
                <td><%= review.getRating() %></td>
                <td><%= review.getContent() %></td>
                <td><%= review.getCreatedAt() %></td>
                <td>
                    <% if (loginUser != null && loginUser.getUserId().equals(review.getReviewerId())) { %>
                        <a href="edit?reviewId=<%= review.getReviewId() %>">수정</a>
                        <form action="delete" method="post" style="display:inline; margin-left:8px;">
                            <input type="hidden" name="reviewId" value="<%= review.getReviewId() %>">
                            <button type="submit" onclick="return confirm('이 후기를 삭제하시겠습니까?');">삭제</button>
                        </form>
                    <% } else { %>
                        -
                    <% } %>
                </td>
            </tr>
        <% } %>
        </tbody>
    </table>
<% } %>
</body>
</html>
