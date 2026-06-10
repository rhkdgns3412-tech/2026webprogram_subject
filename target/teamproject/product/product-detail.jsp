<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="kr.hnu.ice.product.model.Product" %>
<%
    Product product = (Product) request.getAttribute("product");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>상품 상세</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/product.css">
</head>
<body>
<div class="page-shell">
    <section class="hero">
        <span class="eyebrow">상품 상세</span>
        <h1 class="section-title">상품 정보를 한눈에 확인하세요</h1>
        <p class="section-subtitle">이미지, 상태, 가격, 설명을 카드형 레이아웃으로 정리했습니다.</p>
        <div class="detail-actions">
            <a class="btn btn-secondary" href="<%= request.getContextPath() %>/product?action=list">목록</a>
            <% if (product != null) { %>
            <a class="btn btn-primary" href="<%= request.getContextPath() %>/product?action=edit&productId=<%= product.getProductId() %>">수정</a>
            <% } %>
        </div>
    </section>

    <% if (product != null) { %>
    <section class="panel detail-layout">
        <div class="detail-image">
            <% if (product.getImageUrl() != null && !product.getImageUrl().trim().isEmpty()) { %>
            <img src="<%= product.getImageUrl() %>" alt="<%= product.getTitle() == null ? "상품 이미지" : product.getTitle() %>">
            <% } else { %>
            <div class="detail-placeholder">
                <div>
                    <div class="badge is-muted">No image</div>
                    <p class="preview-note">첨부된 상품 이미지가 없습니다.</p>
                </div>
            </div>
            <% } %>
        </div>

        <div class="detail-card">
            <div class="stack">
                <div class="badge-row">
                    <span class="badge"><%= product.getStatus() == null ? "판매중" : product.getStatus() %></span>
                    <span class="badge is-muted"><%= product.getCategory() == null ? "미분류" : product.getCategory() %></span>
                </div>
                <h2 class="detail-title"><%= product.getTitle() == null ? "상품명 없음" : product.getTitle() %></h2>
                <div class="price"><%= product.getPrice() %>원</div>
            </div>

            <div class="info-grid">
                <div class="info-item">
                    <span class="info-label">상품 ID</span>
                    <div class="info-value"><%= product.getProductId() %></div>
                </div>
                <div class="info-item">
                    <span class="info-label">판매자 ID</span>
                    <div class="info-value"><%= product.getSellerId() == null ? "-" : product.getSellerId() %></div>
                </div>
                <div class="info-item">
                    <span class="info-label">카테고리</span>
                    <div class="info-value"><%= product.getCategory() == null ? "-" : product.getCategory() %></div>
                </div>
                <div class="info-item">
                    <span class="info-label">등록일</span>
                    <div class="info-value"><%= product.getCreatedAt() == null ? "-" : product.getCreatedAt() %></div>
                </div>
            </div>

            <div class="stack">
                <span class="info-label">설명</span>
                <div class="description-box"><%= product.getDescription() == null || product.getDescription().trim().isEmpty() ? "상품 설명이 없습니다." : product.getDescription() %></div>
            </div>

            <div class="detail-actions">
                <a class="btn btn-primary" href="<%= request.getContextPath() %>/product?action=edit&productId=<%= product.getProductId() %>">상품 수정</a>
                <a class="btn btn-secondary" href="<%= request.getContextPath() %>/product?action=delete&productId=<%= product.getProductId() %>" onclick="return confirm('삭제하시겠습니까?');">상품 삭제</a>
            </div>
        </div>
    </section>
    <% } else { %>
    <section class="panel">
        <div class="empty-state">상품 정보를 찾을 수 없습니다.</div>
    </section>
    <% } %>
</div>
</body>
</html>