<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <title>리뷰 시스템</title>
  <style>
    body { font-family: Arial, sans-serif; background: #f7f7f7; margin: 0; padding: 0; }
    .container { max-width: 640px; margin: 100px auto; background: #fff; padding: 40px; border-radius: 8px; box-shadow: 0 2px 12px rgba(0,0,0,0.08); text-align: center; }
    h1 { margin-top: 0; font-size: 32px; color: #333; }
    p { color: #666; font-size: 16px; line-height: 1.6; }
    .actions { margin-top: 30px; }
    .button { display: inline-block; padding: 12px 24px; margin: 0 8px; border-radius: 4px; text-decoration: none; font-size: 16px; }
    .primary { background: #333; color: #fff; }
    .secondary { background: #ddd; color: #333; }
    .button:hover { opacity: 0.8; }
  </style>
</head>
<body>
  <div class="container">
    <h1>🌟 리뷰 시스템</h1>
    <p>회원님들의 소중한 평가와 피드백을 나눕니다.</p>
    <p>리뷰를 작성하고, 다른 회원들의 리뷰를 확인해보세요.</p>
    
    <div class="actions">
      <a href="${pageContext.request.contextPath}/login.jsp" class="button primary">로그인</a>
      <a href="${pageContext.request.contextPath}/register.jsp" class="button secondary">회원가입</a>
    </div>
  </div>
</body>
</html>


    <section class="links">
      <a class="link" href="http://localhost:8080/integration/index.jsp">
        <span class="tag product">Product</span>
        <h2 class="title">1.Product index.jsp</h2>
        <p class="desc">메인 페이지 .</p>
      </a>
