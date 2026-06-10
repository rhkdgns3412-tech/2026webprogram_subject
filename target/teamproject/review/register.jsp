<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <title>회원가입</title>
  <style>
    body { font-family: Arial, sans-serif; background: #f4f4f4; margin: 0; padding: 0; }
    .container { max-width: 520px; margin: 60px auto; background: #fff; padding: 24px; border-radius: 8px; box-shadow: 0 2px 10px rgba(0,0,0,0.08); }
    h1 { margin-top: 0; font-size: 24px; }
    label { display: block; margin-top: 16px; color: #333; }
    input { width: 100%; padding: 10px; margin-top: 6px; border: 1px solid #ccc; border-radius: 4px; }
    .actions { margin-top: 24px; }
    .button { padding: 10px 16px; border: none; border-radius: 4px; background: #333; color: #fff; cursor: pointer; }
    .button:hover { background: #000; }
    .message { margin-top: 16px; color: #c00; }
    .link { display: block; margin-top: 12px; color: #555; text-decoration: none; }
  </style>
</head>
<body>
  <div class="container">
    <h1>회원가입</h1>
    <% if (request.getParameter("error") != null) { %>
      <div style="margin-top: 12px; padding: 12px; background: #fee; border: 1px solid #fcc; color: #c00; border-radius: 4px;">
        회원가입에 실패했습니다. 이미 존재하는 아이디이거나 입력값을 다시 확인해주세요.
      </div>
    <% } %>
    <form action="${pageContext.request.contextPath}/member?action=register" method="post">
      <label for="userId">아이디</label>
      <input type="text" id="userId" name="userId" required maxlength="50" />

      <label for="password">비밀번호</label>
      <input type="password" id="password" name="password" required maxlength="100" />

      <label for="nickname">닉네임</label>
      <input type="text" id="nickname" name="nickname" required maxlength="50" />

      <label for="email">이메일</label>
      <input type="email" id="email" name="email" maxlength="100" />

      <div class="actions">
        <button type="submit" class="button">가입하기</button>
      </div>
    </form>
    <a class="link" href="${pageContext.request.contextPath}/login.jsp">로그인 페이지로 이동</a>
  </div>
</body>
</html>
