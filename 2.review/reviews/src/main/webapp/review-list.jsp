<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.List, hnu.ice.review.model.Review" %>
<%
  List<Review> reviews = (List<Review>) request.getAttribute("reviews");
  if (reviews == null) {
    reviews = java.util.Collections.emptyList();
  }
%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <title>리뷰 목록</title>
  <style>
    body { font-family: Arial, sans-serif; background: #f7f7f7; margin: 0; padding: 0; }
    .container { max-width: 960px; margin: 40px auto; background: #fff; padding: 22px; border-radius: 8px; box-shadow: 0 2px 12px rgba(0,0,0,0.08); }
    h1 { margin-top: 0; font-size: 24px; }
    table { width: 100%; border-collapse: collapse; margin-top: 18px; }
    th, td { border: 1px solid #ddd; padding: 10px; text-align: left; }
    th { background: #f0f0f0; }
    .actions { margin-top: 18px; }
    .button { display: inline-block; padding: 10px 14px; color: #fff; background: #333; text-decoration: none; border-radius: 4px; }
    .button:hover { background: #000; }
    .empty { margin-top: 18px; color: #666; }
  </style>
</head>
<body>
  <div class="container">
    <h1>리뷰 목록</h1>
    <div class="actions">
      <a class="button" href="${pageContext.request.contextPath}/review-write.jsp">리뷰 작성</a>
      <a class="button" href="${pageContext.request.contextPath}/mypage.jsp">마이페이지</a>
    </div>

    <table>
      <thead>
        <tr>
          <th>리뷰 ID</th>
          <th>작성자</th>
          <th>대상 사용자</th>
          <th>평점</th>
          <th>내용</th>
          <th>작성일</th>
          <th>삭제</th>
        </tr>
      </thead>
      <tbody>
        <%
          if (reviews.isEmpty()) {
        %>
        <tr>
          <td colspan="7" class="empty">등록된 리뷰가 없습니다.</td>
        </tr>
        <%
          } else {
            for (Review review : reviews) {
        %>
        <tr>
          <td><%= review.getReviewId() %></td>
          <td><%= review.getWriterId() %></td>
          <td><%= review.getTargetUserId() %></td>
          <td><%= review.getRating() %></td>
          <td><%= review.getContent() %></td>
          <td><%= review.getCreatedAt() %></td>
          <td><a href="<%= request.getContextPath() %>/review?action=delete&reviewId=<%= review.getReviewId() %>">삭제</a></td>
        </tr>
        <%
            }
          }
        %>
      </tbody>
    </table>
  </div>
</body>
</html>
