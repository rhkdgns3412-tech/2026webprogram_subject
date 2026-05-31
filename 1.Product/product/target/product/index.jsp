<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Product MVC</title>
	<style>
		body { font-family: Arial, sans-serif; margin: 40px; background: #f5f7fb; color: #222; }
		.card { max-width: 760px; margin: 0 auto; background: #fff; border-radius: 16px; padding: 28px; box-shadow: 0 10px 30px rgba(0,0,0,.08); }
		h1 { margin-top: 0; }
		a { display: inline-block; margin: 6px 10px 6px 0; padding: 10px 14px; border-radius: 10px; background: #1f6feb; color: #fff; text-decoration: none; }
		p { line-height: 1.6; }
	</style>
</head>
<body>
<div class="card">
	<h1>Product MVC 메인페이지</h1>
	<p>이 페이지는 메인 진입점이며, Product 모듈의 각 기능으로 바로 이동합니다.</p>
	<a href="<%= request.getContextPath() %>/product?action=list">상품 목록</a>
	<a href="<%= request.getContextPath() %>/product?action=create">상품 등록</a>
	<a href="<%= request.getContextPath() %>/product?action=search">상품 검색</a>
	<p>연결 DB: Product_db 우선, 로컬 폴더가 <strong>pruduct_db</strong>로 저장된 경우 자동으로 시도합니다.</p>
</div>
</body>
</html>
