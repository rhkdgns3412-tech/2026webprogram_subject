<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List,kr.hnu.ice.product.model.Product" %>
<%
    List<Product> products = (List<Product>) request.getAttribute("products");
    if (products == null) {
        products = java.util.Collections.emptyList();
    }
    
    List<String> categories = (List<String>) request.getAttribute("categories");
    String currentCategory = (String) request.getAttribute("category");
    String currentSort = (String) request.getAttribute("sort");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>상품 목록</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/product.css">
</head>
<body>
<div class="page-shell container py-4">
    <section class="hero">
        <span class="eyebrow">상품 목록</span>
        <h1>캠퍼스 중고 장터</h1>
        <div class="hero-actions">
            <a class="btn btn-primary" href="<%= request.getContextPath() %>/product?action=create">상품 등록</a>
            <a class="btn btn-secondary" href="<%= request.getContextPath() %>/product?action=search">상품 검색</a>
            <a class="btn btn-secondary" href="<%= request.getContextPath() %>/">메인</a>
        </div>
    </section>

    <%-- 필터 및 정렬 툴바 --%>
    <section class="panel mb-4">
        <form action="<%= request.getContextPath() %>/product" method="get" class="row g-3 align-items-end">
            <input type="hidden" name="action" value="list">
            
            <%-- 카테고리 필터 --%>
            <div class="col-md-4">
                <label for="category" class="form-label fw-bold">카테고리</label>
                <select name="category" id="category" class="form-select" onchange="this.form.submit()">
                    <option value="전체" <%= (currentCategory == null || "전체".equals(currentCategory)) ? "selected" : "" %>>전체 카테고리</option>
                    <%
                        if (categories != null) {
                            for (String cat : categories) {
                    %>
                        <option value="<%= cat %>" <%= cat.equals(currentCategory) ? "selected" : "" %>><%= cat %></option>
                    <%
                            }
                        }
                    %>
                </select>
            </div>

            <%-- 가격순 정렬 --%>
            <div class="col-md-4">
                <label for="sort" class="form-label fw-bold">정렬 기준</label>
                <select name="sort" id="sort" class="form-select" onchange="this.form.submit()">
                    <option value="latest" <%= (currentSort == null || "latest".equals(currentSort)) ? "selected" : "" %>>최신 등록순</option>
                    <option value="price_asc" <%= "price_asc".equals(currentSort) ? "selected" : "" %>>가격 낮은순</option>
                    <option value="price_desc" <%= "price_desc".equals(currentSort) ? "selected" : "" %>>가격 높은순</option>
                </select>
            </div>

            <div class="col-md-4 text-end">
                <span class="badge bg-light text-dark border p-2">총 <%= products.size() %>개 상품</span>
            </div>
        </form>
    </section>

    <section class="grid">
        <%
            if (products.isEmpty()) {
        %>
        <div class="empty-state">등록된 상품이 없습니다. 첫 상품을 등록해 보세요.</div>
        <%
            } else {
                for (Product product : products) {
        %>
        <div class="row">
            <div class="col-md-4 mb-4">
                <div class="card h-100">
                    <div class="product-thumb">
                        <% if (product.getImagePath() != null && !product.getImagePath().trim().isEmpty()) { %>
                        <img src="<%= request.getContextPath() %>/<%= product.getImagePath() %>" alt="<%= product.getTitle() == null ? "상품 사진" : product.getTitle() %>">
                        <% } else { %>
                        <div class="thumb-placeholder">등록된 사진이 없습니다.</div>
                        <% } %>
                    </div>
                    <div class="card-body">
                        <div class="mb-2">
                            <span class="badge bg-primary"><%= product.getStatus() == null ? "판매중" : product.getStatus() %></span>
                            <span class="badge bg-secondary"><%= product.getCategory() == null ? "미분류" : product.getCategory() %></span>
                        </div>
                        <h5 class="card-title"><%= product.getTitle() == null ? "상품명 없음" : product.getTitle() %></h5>
                        <p class="card-text fw-bold"><%= product.getPrice() %>원</p>
                        <p class="card-text text-muted">판매자 <strong><%= product.getSellerId() == null ? "-" : product.getSellerId() %></strong></p>
                    </div>
                    <div class="card-footer bg-transparent">
                        <a class="btn btn-sm btn-outline-primary" href="<%= request.getContextPath() %>/product?action=detail&productId=<%= product.getProductId() %>">상세</a>
                        <a class="btn btn-sm btn-outline-secondary" href="<%= request.getContextPath() %>/product?action=edit&productId=<%= product.getProductId() %>">수정</a>
                        <a class="btn btn-sm btn-danger" href="<%= request.getContextPath() %>/product?action=delete&productId=<%= product.getProductId() %>" onclick="return confirm('삭제하시겠습니까?');">삭제</a>
                    </div>
                </div>
            </div>
        </div>
        <%
                }
            }
        %>
        
    </section>
</div>
</body>
</html>
