<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>상품 등록</title>
</head>
<body>
<h1>상품 등록</h1>
<form action="<%= request.getContextPath() %>/product?action=create" method="post">
    <p>상품명(product_id) <input type="text" name="productId" required></p>
    <p>제목(title) <input type="text" name="title" required></p>
    <p>판매자 ID <input type="text" name="sellerId" required></p>
    <p>가격 <input type="number" name="price" required></p>
    <p>카테고리 <input type="text" name="category" required></p>
    <p>설명<br><textarea name="description" rows="6" cols="50"></textarea></p>
    <button type="submit">등록</button>
</form>
<p><a href="<%= request.getContextPath() %>/product?action=list">목록으로</a></p>
</body>
</html>