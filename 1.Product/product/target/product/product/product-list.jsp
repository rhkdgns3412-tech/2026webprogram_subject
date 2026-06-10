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
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/product.css">
</head>
<body>
<div class="page-shell container py-4">
    <section class="hero">
        <span class="eyebrow">상품 목록</span>
        <div class="hero-actions">
            <a class="btn btn-primary" href="<%= request.getContextPath() %>/product?action=create">상품 등록</a>
            <a class="btn btn-secondary" href="<%= request.getContextPath() %>/product?action=search">상품 검색</a>
            <a class="btn btn-secondary" href="<%= request.getContextPath() %>/">메인</a>
        </div>
    </section>

    <section class="toolbar mb-3">
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
        <div class="row">
            <div class="col-md-4 mb-4">
                <div class="card h-100">
                    <% if (product.getImageUrl() != null && !product.getImageUrl().trim().isEmpty()) { %>
                        <img src="<%= product.getImageUrl() %>" class="card-img-top" alt="<%= product.getTitle() == null ? "상품 이미지" : product.getTitle() %>">
                    <% } else { %>
                        <div class="card-img-top d-flex align-items-center justify-content-center" style="height:180px; background:#f8f9fa;">
                            <div>
                                <div class="badge bg-secondary text-light">No image</div>
                                <p class="preview-note">이미지 없음</p>
                            </div>
                        </div>
                    <% } %>
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
</div>
</body>
</html>