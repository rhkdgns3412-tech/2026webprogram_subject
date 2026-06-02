<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
  String loginUser = (String) session.getAttribute("loginUser");
  if (loginUser == null) {
    response.sendRedirect(request.getContextPath() + "/login.jsp");
    return;
  }
%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <title>리뷰 작성</title>
  <style>
    body { font-family: Arial, sans-serif; background: #f4f4f4; margin: 0; padding: 0; }
    .container { max-width: 640px; margin: 60px auto; background: #fff; padding: 24px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.08); }
    h1 { margin-top: 0; font-size: 24px; }
    label { display: block; margin-top: 16px; color: #333; }
    input, select, textarea { width: 100%; padding: 10px; margin-top: 6px; border: 1px solid #ccc; border-radius: 4px; }
    textarea { min-height: 140px; resize: vertical; }
    .actions { margin-top: 24px; }
    .button { padding: 10px 16px; border: none; border-radius: 4px; background: #333; color: #fff; cursor: pointer; }
    .button:hover { background: #000; }
    .link { display: inline-block; margin-top: 12px; color: #555; text-decoration: none; }
  </style>
</head>
<body>
  <div class="container">
    <h1>리뷰 작성</h1>
    <% if (request.getParameter("error") != null) { %>
      <div style="margin-top: 12px; padding: 12px; background: #fee; border: 1px solid #fcc; color: #c00; border-radius: 4px;">
        리뷰 작성에 실패했습니다. 입력값을 확인해주세요.
      </div>
    <% } %>
    <p>작성자: <strong><%= loginUser %></strong></p>
    <form action="${pageContext.request.contextPath}/review?action=write" method="post">
      <label for="targetUserId">대상 사용자 아이디</label>
      <input type="text" id="targetUserId" name="targetUserId" required maxlength="50" />

      <label for="rating">평점</label>
      <select id="rating" name="rating" required>
        <option value="5">5</option>
        <option value="4">4</option>
        <option value="3">3</option>
        <option value="2">2</option>
        <option value="1">1</option>
      </select>

      <label for="content">리뷰 내용</label>
      <textarea id="content" name="content" required maxlength="500"></textarea>

      <div class="actions">
        <button type="submit" class="button">등록하기</button>
      </div>
    </form>
    <a class="link" href="${pageContext.request.contextPath}/review?action=list">리뷰 목록으로 돌아가기</a>
  </div>
</body>
</html>
