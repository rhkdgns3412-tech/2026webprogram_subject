<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="kr.hnu.ice.product.model.Product" %>
<%
    Product product = (Product) request.getAttribute("product");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>상품 상세</title>
</head>
<body>
<h1>상품 상세</h1>
<% if (product != null) { %>
<ul>
    <li>상품 ID: <%= product.getProductId() %></li>
    <li>판매자 ID: <%= product.getSellerId() %></li>
    <li>상품명: <%= product.getTitle() %></li>
    <li>가격: <%= product.getPrice() %></li>
    <li>카테고리: <%= product.getCategory() %></li>
    <li>설명: <%= product.getDescription() %></li>
    <li>상태: <%= product.getStatus() %></li>
    <li>등록일: <%= product.getCreatedAt() %></li>
</ul>
<% } else { %>
<p>상품 정보를 찾을 수 없습니다.</p>
<% } %>
<p>
    <a href="<%= request.getContextPath() %>/product?action=list">목록</a>
    <a href="<%= request.getContextPath() %>/product?action=edit&productId=<%= product == null ? "" : product.getProductId() %>">수정</a>
</p>
</body>
</html>