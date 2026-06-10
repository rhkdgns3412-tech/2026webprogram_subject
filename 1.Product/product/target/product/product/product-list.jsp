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
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>상품 목록</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/product.css">
</head>
<body>
<div class="page-shell">
    <section class="hero">
        <span class="eyebrow">상품 목록</span>
        <h1 class="section-title">등록된 상품들을 카드로 확인하세요</h1>
        <p class="section-subtitle">이미지, 제목, 가격, 거래 상태를 한 화면에서 비교할 수 있도록 구성했습니다.</p>
        <div class="hero-actions">
            <a class="btn btn-primary" href="<%= request.getContextPath() %>/product?action=create">상품 등록</a>
            <a class="btn btn-secondary" href="<%= request.getContextPath() %>/product?action=search">상품 검색</a>
            <a class="btn btn-secondary" href="<%= request.getContextPath() %>/">메인</a>
        </div>
    </section>

    <section class="toolbar">
        <div class="badge-row">
            <span class="chip">총 <%= products.size() %>개 상품</span>
            <span class="chip is-muted">이미지 첨부 지원</span>
        </div>
        <a class="btn btn-secondary" href="<%= request.getContextPath() %>/product?action=create">새 상품 등록</a>
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
        <article class="product-card">
            <div class="product-thumb">
                <% if (product.getImageUrl() != null && !product.getImageUrl().trim().isEmpty()) { %>
                <img src="<%= product.getImageUrl() %>" alt="<%= product.getTitle() == null ? "상품 이미지" : product.getTitle() %>">
                <% } else { %>
                <div class="thumb-placeholder">
                    <div>
                        <div class="badge is-muted">No image</div>
                        <p class="preview-note">이미지 첨부가 아직 없습니다.</p>
                    </div>
                </div>
                <% } %>
            </div>
            <div class="card-body">
                <div class="badge-row">
                    <span class="badge"><%= product.getStatus() == null ? "판매중" : product.getStatus() %></span>
                    <span class="badge is-muted"><%= product.getCategory() == null ? "미분류" : product.getCategory() %></span>
                </div>
                <div>
                    <h2 class="card-title"><%= product.getTitle() == null ? "상품명 없음" : product.getTitle() %></h2>
                    <div class="price"><%= product.getPrice() %>원</div>
                </div>
                <div class="meta">
                    판매자 <strong><%= product.getSellerId() == null ? "-" : product.getSellerId() %></strong><br>
                    등록일 <%= product.getCreatedAt() == null ? "-" : product.getCreatedAt() %>
                </div>
                <div class="card-actions">
                    <a class="btn btn-secondary" href="<%= request.getContextPath() %>/product?action=detail&productId=<%= product.getProductId() %>">상세</a>
                    <a class="btn btn-secondary" href="<%= request.getContextPath() %>/product?action=edit&productId=<%= product.getProductId() %>">수정</a>
                    <a class="btn btn-danger" href="<%= request.getContextPath() %>/product?action=delete&productId=<%= product.getProductId() %>" onclick="return confirm('삭제하시겠습니까?');">삭제</a>
                </div>
            </div>
        </article>
        <%
                }
            }
        %>
    </section>
</div>
</body>
</html>