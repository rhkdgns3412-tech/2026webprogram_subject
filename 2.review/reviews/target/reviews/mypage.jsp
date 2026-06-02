<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="hnu.ice.member.model.Member" %>
<%
  String loginUser = (String) session.getAttribute("loginUser");
  Member member = (Member) request.getAttribute("member");
  Double averageRating = (Double) request.getAttribute("averageRating");
  Integer reviewCount = (Integer) request.getAttribute("reviewCount");
  
  if (loginUser == null) {
    response.sendRedirect(request.getContextPath() + "/login.jsp");
    return;
  }
  
  if (member == null) {
    member = new Member();
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
    .info-group { margin-top: 20px; padding: 16px; background: #f9f9f9; border-radius: 4px; border-left: 4px solid #333; }
    .info-row { margin-top: 8px; }
    .info-label { display: inline-block; width: 120px; color: #666; font-weight: bold; }
    .info-value { display: inline-block; color: #333; }
    .nav { margin-top: 24px; }
    .nav a { display: inline-block; margin-right: 12px; margin-top: 8px; padding: 10px 14px; border-radius: 4px; text-decoration: none; color: #fff; background: #333; }
    .nav a:hover { background: #000; }
  </style>
</head>
<body>
  <div class="container">
    <h1>마이페이지</h1>
    
    <div class="info-group">
      <h2 style="margin-top: 0;">회원 정보</h2>
      <div class="info-row">
        <span class="info-label">아이디:</span>
        <span class="info-value"><%= loginUser %></span>
      </div>
      <div class="info-row">
        <span class="info-label">닉네임:</span>
        <span class="info-value"><%= member.getNickname() != null ? member.getNickname() : "-" %></span>
      </div>
      <div class="info-row">
        <span class="info-label">이메일:</span>
        <span class="info-value"><%= member.getEmail() != null ? member.getEmail() : "-" %></span>
      </div>
    </div>
    
    <div class="info-group">
      <h2 style="margin-top: 0;">평가 정보</h2>
      <div class="info-row">
        <span class="info-label">평균 평점:</span>
        <span class="info-value"><strong><%= String.format("%.1f", averageRating) %></strong></span>
      </div>
      <div class="info-row">
        <span class="info-label">받은 후기:</span>
        <span class="info-value"><strong><%= reviewCount %></strong>개</span>
      </div>
    </div>
    
    <div class="nav">
      <a href="<%= request.getContextPath() %>/review?action=list">리뷰 목록</a>
      <a href="<%= request.getContextPath() %>/review-write.jsp">리뷰 작성</a>
      <a href="<%= request.getContextPath() %>/member?action=logout">로그아웃</a>
    </div>
  </div>
</body>
</html>
