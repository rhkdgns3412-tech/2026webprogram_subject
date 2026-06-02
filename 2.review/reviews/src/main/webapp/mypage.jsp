<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
  String loginUser = (String) session.getAttribute("loginUser");
  Double averageRating = (Double) request.getAttribute("averageRating");
  Integer reviewCount = (Integer) request.getAttribute("reviewCount");
  if (loginUser == null) {
    response.sendRedirect(request.getContextPath() + "/login.jsp");
    return;
  }
  if (averageRating == null) {
    averageRating = 0.0;
  }
  if (reviewCount == null) {
    reviewCount = 0;
  }
%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <title>마이페이지</title>
  <style>
    body { font-family: Arial, sans-serif; background: #f7f7f7; margin: 0; padding: 0; }
    .container { max-width: 640px; margin: 60px auto; background: #fff; padding: 28px; border-radius: 8px; box-shadow: 0 2px 12px rgba(0,0,0,0.08); }
    h1 { margin-top: 0; font-size: 26px; }
    .info { margin-top: 16px; color: #333; }
    .nav { margin-top: 24px; }
    .nav a { display: inline-block; margin-right: 12px; margin-top: 8px; padding: 10px 14px; border-radius: 4px; text-decoration: none; color: #fff; background: #333; }
    .nav a:hover { background: #000; }
  </style>
</head>
<body>
  <div class="container">
    <h1>마이페이지</h1>
    <p class="info">안녕하세요, <strong><%= loginUser %></strong>님.</p>
    <p class="info">평균 평점: <strong><%= String.format("%.1f", averageRating) %></strong> / 후기 개수: <strong><%= reviewCount %></strong></p>
    <div class="nav">
      <a href="<%= request.getContextPath() %>/review?action=list">리뷰 목록</a>
      <a href="<%= request.getContextPath() %>/review-write.jsp">리뷰 작성</a>
      <a href="<%= request.getContextPath() %>/member?action=logout">로그아웃</a>
    </div>
  </div>
</body>
</html>
