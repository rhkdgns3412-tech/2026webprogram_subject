<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>통합 이동 페이지</title>
  <style>
    :root {
      --bg-1: #f6efe7;
      --bg-2: #e6eef8;
      --card: rgba(255, 255, 255, 0.92);
      --text: #1e2a39;
      --muted: #4c5b6d;
      --line: #d8e0ea;
      --product: #0f766e;
      --review: #b45309;
    }

    * { box-sizing: border-box; }

    body {
      margin: 0;
      min-height: 100vh;
      font-family: "Segoe UI", "Malgun Gothic", sans-serif;
      color: var(--text);
      background:
        radial-gradient(circle at 12% 20%, #ffffff 0%, transparent 28%),
        radial-gradient(circle at 84% 78%, #ffffff 0%, transparent 26%),
        linear-gradient(135deg, var(--bg-1), var(--bg-2));
      display: grid;
      place-items: center;
      padding: 24px;
    }

    .panel {
      width: min(760px, 100%);
      background: var(--card);
      border: 1px solid var(--line);
      border-radius: 20px;
      box-shadow: 0 20px 45px rgba(30, 42, 57, 0.14);
      padding: 28px;
    }

    h1 {
      margin: 0;
      font-size: clamp(26px, 4vw, 36px);
      letter-spacing: -0.02em;
    }

    p {
      margin: 12px 0 0;
      color: var(--muted);
      line-height: 1.6;
    }

    .links {
      margin-top: 22px;
      display: grid;
      gap: 12px;
      grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
    }

    .link {
      display: block;
      text-decoration: none;
      border-radius: 14px;
      padding: 18px;
      border: 1px solid var(--line);
      background: #fff;
      transition: transform 120ms ease, box-shadow 120ms ease;
      color: inherit;
    }

    .link:hover {
      transform: translateY(-2px);
      box-shadow: 0 10px 20px rgba(30, 42, 57, 0.12);
    }

    .tag {
      display: inline-block;
      font-size: 12px;
      font-weight: 700;
      padding: 4px 8px;
      border-radius: 999px;
      margin-bottom: 10px;
      color: #fff;
    }

    .tag.product { background: var(--product); }
    .tag.review { background: var(--review); }

    .title {
      font-size: 20px;
      font-weight: 700;
      margin: 0 0 8px;
    }

    .desc {
      margin: 0;
      color: var(--muted);
      font-size: 14px;
      line-height: 1.5;
    }

    .hint {
      margin-top: 18px;
      font-size: 13px;
      color: var(--muted);
      background: #f7fafc;
      border: 1px dashed var(--line);
      border-radius: 10px;
      padding: 10px 12px;
    }
  </style>
</head>
<body>
  <main class="panel">
    <h1>3.Integeration 통합 페이지</h1>
    <p>아래 버튼으로 Product와 Review 메인 페이지로 이동할 수 있습니다.</p>

    <section class="links">
      <a class="link" href="http://localhost:8080/product/index.jsp">
        <span class="tag product">Product</span>
        <h2 class="title">1.Product index.jsp</h2>
        <p class="desc">상품 모듈 메인 페이지로 이동합니다.</p>
      </a>

      <a class="link" href="http://localhost:8080/reviews/index.jsp">
        <span class="tag review">Review</span>
        <h2 class="title">2.review index.jsp</h2>
        <p class="desc">리뷰 모듈 메인 페이지로 이동합니다.</p>
      </a>
    </section>

    <div class="hint">
      Tomcat 기본 포트(8080) 기준 링크입니다. 포트나 컨텍스트 경로가 다르면 주소를 변경해 주세요.
    </div>
  </main>
</body>
</html>
