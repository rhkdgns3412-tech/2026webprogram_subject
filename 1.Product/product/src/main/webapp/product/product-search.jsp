<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List,kr.hnu.ice.product.model.Product" %>
<%
    List<Product> products = (List<Product>) request.getAttribute("products");
    if (products == null) {
        products = java.util.Collections.emptyList();
    }
    String keyword = (String) request.getAttribute("keyword");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>상품 검색</title>
</head>
<body>
<h1>상품 검색</h1>
<form action="<%= request.getContextPath() %>/product" method="get">
    <input type="hidden" name="action" value="search">
    <input type="text" name="keyword" value="<%= keyword == null ? "" : keyword %>" placeholder="제목, 카테고리, 설명 검색">
    <button type="submit">검색</button>
</form>
<table border="1" cellpadding="8" cellspacing="0">
    <tr>
        <th>ID</th>
        <th>제목</th>
        <th>가격</th>
        <th>카테고리</th>
        <th>상태</th>
    </tr>
    <% for (Product product : products) { %>
    <tr>
        <td><%= product.getProductId() %></td>
        <td><%= product.getTitle() %></td>
        <td><%= product.getPrice() %></td>
        <td><%= product.getCategory() %></td>
        <td><%= product.getStatus() %></td>
    </tr>
    <% } %>
</table>
<p><a href="<%= request.getContextPath() %>/product?action=list">목록으로</a></p>
</body>
</html>