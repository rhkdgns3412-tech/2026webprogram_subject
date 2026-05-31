<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List,kr.hnu.ice.product.model.Product" %>
<%
    List<Product> products = (List<Product>) request.getAttribute("products");
    if (products == null) {
        products = java.util.Collections.emptyList();
    }
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>상품 목록</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 32px; }
        table { width: 100%; border-collapse: collapse; }
        th, td { border: 1px solid #ddd; padding: 10px; text-align: left; }
        th { background: #f4f6f8; }
        a { margin-right: 8px; }
    </style>
</head>
<body>
<h1>상품 목록</h1>
<p>
    <a href="<%= request.getContextPath() %>/">메인</a>
    <a href="<%= request.getContextPath() %>/product?action=create">상품 등록</a>
    <a href="<%= request.getContextPath() %>/product?action=search">상품 검색</a>
</p>
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>판매자</th>
        <th>제목</th>
        <th>가격</th>
        <th>카테고리</th>
        <th>상태</th>
        <th>등록일</th>
        <th>관리</th>
    </tr>
    </thead>
    <tbody>
    <%
        for (Product product : products) {
    %>
    <tr>
        <td><%= product.getProductId() %></td>
        <td><%= product.getSellerId() %></td>
        <td><%= product.getTitle() %></td>
        <td><%= product.getPrice() %></td>
        <td><%= product.getCategory() %></td>
        <td><%= product.getStatus() %></td>
        <td><%= product.getCreatedAt() %></td>
        <td>
            <a href="<%= request.getContextPath() %>/product?action=detail&productId=<%= product.getProductId() %>">상세</a>
            <a href="<%= request.getContextPath() %>/product?action=edit&productId=<%= product.getProductId() %>">수정</a>
            <a href="<%= request.getContextPath() %>/product?action=delete&productId=<%= product.getProductId() %>" onclick="return confirm('삭제하시겠습니까?');">삭제</a>
        </td>
    </tr>
    <%
        }
    %>
    </tbody>
</table>
</body>
</html>