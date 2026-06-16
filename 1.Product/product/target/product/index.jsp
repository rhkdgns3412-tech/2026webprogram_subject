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
		<h1>상품 </h1>
		<div class="hero-actions">
			<a class="btn btn-primary" href="<%= request.getContextPath() %>/product?action=list">상품 목록</a>
			<a class="btn btn-secondary" href="<%= request.getContextPath() %>/product?action=create">상품 등록</a>
			<a class="btn btn-secondary" href="<%= request.getContextPath() %>/product?action=search">상품 검색</a>
		</div>
	</section>


</div>
</body>
</html>
