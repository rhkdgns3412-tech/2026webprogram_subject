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
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>상품 검색</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/product.css">
</head>
<body>
<div class="page-shell container py-4">
    <section class="hero">
        <span class="eyebrow">상품 검색</span>
        <h1 class="section-title">원하는 상품을 빠르게 찾아보세요</h1>
        <p class="section-subtitle">제목, 카테고리, 설명을 기준으로 검색 결과를 카드형 목록으로 확인합니다.</p>
        <div class="hero-actions">
            <a class="btn btn-secondary" href="<%= request.getContextPath() %>/product?action=list">목록으로</a>
            <a class="btn btn-secondary" href="<%= request.getContextPath() %>/product?action=create">상품 등록</a>
        </div>
    </section>

    <section class="panel">
        <form class="search-bar d-flex" action="<%= request.getContextPath() %>/product" method="get">
            <input type="hidden" name="action" value="search">
            <input type="text" name="keyword" value="<%= keyword == null ? "" : keyword %>" placeholder="제목, 카테고리, 설명 검색" class="form-control me-2">
            <button class="btn btn-primary" type="submit">검색</button>
        </form>
    </section>

    <section class="toolbar">
        <div class="badge-row">
            <span class="chip">검색 결과 <%= products.size() %>개</span>
            <span class="chip is-muted"><%= keyword == null || keyword.trim().isEmpty() ? "전체 상품" : "키워드: " + keyword %></span>
        </div>
        <a class="btn btn-secondary" href="<%= request.getContextPath() %>/product?action=list">전체 목록</a>
    </section>

    <section class="grid">
        <%
            if (products.isEmpty()) {
        %>
        <div class="empty-state">검색 결과가 없습니다. 다른 키워드로 다시 시도해 보세요.</div>
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
                        <p class="card-text text-muted">상품 ID <strong><%= product.getProductId() %></strong></p>
                        <a class="btn btn-sm btn-outline-primary" href="<%= request.getContextPath() %>/product?action=detail&productId=<%= product.getProductId() %>">상세</a>
                        <a class="btn btn-sm btn-outline-secondary" href="<%= request.getContextPath() %>/product?action=edit&productId=<%= product.getProductId() %>">수정</a>
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
