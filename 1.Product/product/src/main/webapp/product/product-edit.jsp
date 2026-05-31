<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="kr.hnu.ice.product.model.Product" %>
<%
    Product product = (Product) request.getAttribute("product");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>상품 수정</title>
</head>
<body>
<h1>상품 수정</h1>
<% if (product != null) { %>
<form action="<%= request.getContextPath() %>/product?action=edit" method="post">
    <p>상품명(product_id) <input type="text" name="productId" value="<%= product.getProductId() %>" required></p>
    <p>제목(title) <input type="text" name="title" value="<%= product.getTitle() %>" required></p>
    <p>판매자 ID <input type="text" name="sellerId" value="<%= product.getSellerId() %>" required></p>
    <p>가격 <input type="number" name="price" value="<%= product.getPrice() %>" required></p>
    <p>카테고리 <input type="text" name="category" value="<%= product.getCategory() %>" required></p>
    <p>상태
        <select name="status">
            <option value="판매중" <%= "판매중".equals(product.getStatus()) ? "selected" : "" %>>판매중</option>
            <option value="예약중" <%= "예약중".equals(product.getStatus()) ? "selected" : "" %>>예약중</option>
            <option value="거래완료" <%= "거래완료".equals(product.getStatus()) ? "selected" : "" %>>거래완료</option>
        </select>
    </p>
    <p>설명<br><textarea name="description" rows="6" cols="50"><%= product.getDescription() == null ? "" : product.getDescription() %></textarea></p>
    <button type="submit">수정</button>
</form>
<% } else { %>
<p>수정할 상품이 없습니다.</p>
<% } %>
<p><a href="<%= request.getContextPath() %>/product?action=list">목록으로</a></p>
</body>
</html>