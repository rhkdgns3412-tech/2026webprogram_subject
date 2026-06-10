<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Project Hub</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
	<link rel="stylesheet" href="<%= request.getContextPath() %>/assets/product.css">
	<style>
		.hero-grid {
			display: grid;
			grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
			gap: 18px;
		}
		.link-card {
			display: grid;
			gap: 14px;
			padding: 20px;
			border: 1px solid var(--border);
			border-radius: 24px;
			background: rgba(255, 255, 255, 0.8);
			box-shadow: var(--shadow);
		}
		.link-list {
			display: flex;
			flex-wrap: wrap;
			gap: 10px;
		}
		.link-list a {
			padding: 10px 14px;
			border-radius: 12px;
			background: #fff;
			border: 1px solid var(--border);
			font-weight: 700;
		}
		.link-list a:hover {
			background: #edf7f5;
		}
		.helper-small {
			font-size: 13px;
			color: var(--muted);
			line-height: 1.6;
		}
	</style>
	</head>
<body>
<div class="page-shell container py-4">
	<section class="hero">
		<span class="eyebrow">Project Hub</span>
		<h1>상품 모듈과 리뷰 모듈로 바로 이동하는 시작 화면</h1>
		<p>이 화면에서 1.Product의 주요 JSP와 2.review의 주요 JSP를 한 번에 열 수 있도록 구성했습니다.</p>
		<div class="hero-actions">
			<a class="btn btn-primary" href="<%= request.getContextPath() %>/product?action=list">상품 목록</a>
			<a class="btn btn-secondary" href="<%= request.getContextPath() %>/product?action=create">상품 등록</a>
			<a class="btn btn-secondary" href="<%= request.getContextPath() %>/product?action=search">상품 검색</a>
		</div>
	</section>

	</section>
	<section class="panel">
		<div class="row g-4">
			<div class="col-md-6">
				<div class="card p-3 h-100">
					<div class="badge">1.Product JSP</div>
					<h2 class="section-title">상품 모듈</h2>
					<p class="section-subtitle">상품 등록, 목록, 검색, 상세 화면으로 바로 이동합니다.</p>
					<div class="d-flex flex-wrap gap-2">
						<a class="btn btn-outline-primary" href="<%= request.getContextPath() %>/product?action=list">product-list.jsp</a>
						<a class="btn btn-outline-primary" href="<%= request.getContextPath() %>/product?action=create">product-create.jsp</a>
						<a class="btn btn-outline-primary" href="<%= request.getContextPath() %>/product?action=search">product-search.jsp</a>
					</div>
					<div class="helper-small mt-2">상세와 수정은 목록 화면에서 개별 상품을 선택하면 열립니다.</div>
				</div>
			</div>
			<div class="col-md-6">
				<div class="card p-3 h-100">
					<div class="badge">2.review JSP</div>
					<h2 class="section-title">리뷰 모듈</h2>
					<p class="section-subtitle">리뷰 앱은 별도 프로젝트라서 리뷰용 JSP 진입 경로를 함께 제공합니다.</p>
					<div class="d-flex flex-wrap gap-2">
						<a class="btn btn-outline-secondary" href="/reviews/login.jsp">login.jsp</a>
						<a class="btn btn-outline-secondary" href="/reviews/register.jsp">register.jsp</a>
						<a class="btn btn-outline-secondary" href="/reviews/mypage.jsp">mypage.jsp</a>
						<a class="btn btn-outline-secondary" href="/reviews/review-list.jsp">review-list.jsp</a>
						<a class="btn btn-outline-secondary" href="/reviews/review-write.jsp">review-write.jsp</a>
					</div>
					<div class="helper-small mt-2">review 프로젝트가 같은 톰캣에 /reviews 컨텍스트로 배포되어 있어야 정상 동작합니다.</div>
				</div>
			</div>
		</div>
	</section>

	<section class="panel">
		<div class="grid">
			<div class="helper-box">DB 연결은 <strong>Product_db</strong>를 우선 사용하고, 로컬 환경에서 <strong>pruduct_db</strong>도 자동으로 시도합니다.</div>
			<div class="helper-box">업로드 이미지는 서버 디스크에 저장되며, 상품 삭제 시 함께 정리됩니다.</div>
		</div>
	</section>
</div>
</body>
</html>
