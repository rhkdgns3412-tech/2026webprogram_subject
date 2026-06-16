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
    <title>상품 수정</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/product.css">
</head>
<body>
<div class="page-shell container py-4">
    <section class="hero">
        <span class="eyebrow">상품 수정</span>
        <h1 class="section-title">상품 정보를 수정하세요</h1>
        <p class="section-subtitle">상품 ID는 고정하고, 제목·가격·상태·설명만 갱신하는 흐름으로 수정 화면을 구성했습니다.</p>
        <div class="hero-actions">
            <a class="btn btn-secondary" href="<%= request.getContextPath() %>/product?action=list">목록으로</a>
            <% if (product != null) { %>
            <a class="btn btn-secondary" href="<%= request.getContextPath() %>/product?action=detail&productId=<%= product.getProductId() %>">상세로</a>
            <% } %>
        </div>
    </section>

    <% if (product != null) { %>
    <section class="panel form-layout">
        <div class="row">
            <div class="col-12">
                <form class="form-card" action="<%= request.getContextPath() %>/product?action=edit" method="post" enctype="multipart/form-data">
            <div class="stack">
                <h2 class="form-title">상품 정보</h2>
            </div>

                    <div class="form-grid">
                        <div class="mb-3">
                            <label for="productId" class="form-label">상품 ID</label>
                            <input id="productId" type="text" name="productId" value="<%= product.getProductId() %>" readonly class="form-control-plaintext">
                        </div>
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="title" class="form-label">제목</label>
                                <input id="title" type="text" name="title" value="<%= product.getTitle() == null ? "" : product.getTitle() %>" required class="form-control">
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="sellerId" class="form-label">판매자 ID</label>
                                <input id="sellerId" type="text" name="sellerId" value="<%= product.getSellerId() == null ? "" : product.getSellerId() %>" required class="form-control">
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-4 mb-3">
                                <label for="price" class="form-label">가격</label>
                                <input id="price" type="number" name="price" min="0" step="1" value="<%= product.getPrice() %>" required class="form-control">
                            </div>
                            <div class="col-md-4 mb-3">
                                <label for="category" class="form-label">카테고리</label>
                                <select id="category" name="category" required class="form-select">
                                    <option value="" <%= (product.getCategory() == null || product.getCategory().trim().isEmpty()) ? "selected" : "" %> disabled>카테고리를 선택하세요</option>
                                    <option value="교재" <%= "교재".equals(product.getCategory()) ? "selected" : "" %>>교재</option>
                                    <option value="의류" <%= "의류".equals(product.getCategory()) ? "selected" : "" %>>의류</option>
                                    <option value="필기구" <%= "필기구".equals(product.getCategory()) ? "selected" : "" %>>필기구</option>
                                    <option value="기타" <%= "기타".equals(product.getCategory()) ? "selected" : "" %>>기타</option>
                                </select>
                            </div>
                            <div class="col-md-4 mb-3">
                                <label for="status" class="form-label">거래 상태</label>
                                <select id="status" name="status" class="form-select">
                                    <option value="판매중" <%= "판매중".equals(product.getStatus()) ? "selected" : "" %>>판매중</option>
                                    <option value="예약중" <%= "예약중".equals(product.getStatus()) ? "selected" : "" %>>예약중</option>
                                    <option value="거래완료" <%= "거래완료".equals(product.getStatus()) ? "selected" : "" %>>거래완료</option>
                                </select>
                            </div>
                        </div>
                        <div class="mb-3">
                            <label for="description" class="form-label">설명</label>
                            <textarea id="description" name="description" rows="8" placeholder="상품 설명을 입력하세요." class="form-control"><%= product.getDescription() == null ? "" : product.getDescription() %></textarea>
                        </div>
                        <div class="mb-3">
                            <label for="productImage" class="form-label">상품 사진 교체</label>
                            <input id="productImage" type="file" name="productImage" accept="image/*" class="form-control">
                            <div class="form-text">새 파일을 올리면 기존 사진을 교체하고, 비워두면 기존 사진을 유지합니다.</div>
                        </div>
                        <% if (product.getImagePath() != null && !product.getImagePath().trim().isEmpty()) { %>
                        <div class="preview-image mb-3">
                            <img src="<%= request.getContextPath() %>/<%= product.getImagePath() %>" alt="현재 상품 사진">
                        </div>
                        <% } %>
                    </div>
                    <div class="form-actions mt-3 d-flex gap-2">
                        <button class="btn btn-primary" type="submit">수정하기</button>
                        <a class="btn btn-secondary" href="<%= request.getContextPath() %>/product?action=detail&productId=<%= product.getProductId() %>">취소</a>
                    </div>
                </form>
            </div>
        </div>

    </section>
    <% } else { %>
    <section class="panel">
        <div class="empty-state">수정할 상품이 없습니다.</div>
    </section>
    <% } %>
</div>
</body>
</html>
