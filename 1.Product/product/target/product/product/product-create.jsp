<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>상품 등록</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/product.css">
</head>
<body>
<div class="page-shell container py-4">
    <section class="hero">
        <span class="eyebrow">상품 등록</span>
        <h1 class="section-title">상품을 등록해주세요</h1>
        
        <div class="hero-actions">
            <a class="btn btn-secondary" href="<%= request.getContextPath() %>/product?action=list">목록으로</a>
            <a class="btn btn-secondary" href="<%= request.getContextPath() %>/product?action=search">검색하기</a>
        </div>
    </section>

    <section class="panel form-layout">
        <div class="row">
            <div class="col-12">
                <form class="form-card" action="<%= request.getContextPath() %>/product?action=create" method="post" enctype="multipart/form-data">
            <div class="stack">
                <h2 class="form-title">상품 정보</h2>
            </div>

                    <div class="form-grid">
                        <div class="mb-3">
                            <label for="productId" class="form-label">상품명</label>
                            <input id="productId" type="text" name="productId" required placeholder="예: 짧고 굵게 배우는 jsp 웹 프로그래밍과 스프링 프레임워크" class="form-control">
                        </div>
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="title" class="form-label">제목</label>
                                <input id="title" type="text" name="title" required placeholder="ex:전공교재 판매합니다" class="form-control">
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="sellerId" class="form-label">판매자 ID</label>
                                <input id="sellerId" type="text" name="sellerId" required placeholder="판매자 아이디" class="form-control">
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-4 mb-3">
                                <label for="price" class="form-label">가격</label>
                                <input id="price" type="number" name="price" min="0" step="1" required placeholder="0" class="form-control">
                            </div>
                            <div class="col-md-4 mb-3">
                                <label for="category" class="form-label">카테고리</label>
                                <select id="category" name="category" required class="form-select">
                                    <option value="" disabled selected>카테고리를 선택하세요</option>
                                    <option value="교재">교재</option>
                                    <option value="의류">의류</option>
                                    <option value="필기구">필기구</option>
                                    <option value="기타">기타</option>
                                </select>
                            </div>
                        </div>
                        <div class="mb-3">
                            <label for="description" class="form-label">설명</label>
                            <textarea id="description" name="description" rows="8" placeholder="상품 설명을 입력하세요." class="form-control"></textarea>
                        </div>
                        <div class="mb-3">
                            <label for="productImage" class="form-label">상품 사진</label>
                            <input id="productImage" type="file" name="productImage" accept="image/*" required class="form-control">
                            <div class="form-text">링크가 아닌 이미지 파일을 직접 업로드합니다.</div>
                        </div>
                    </div>
                    <div class="form-actions mt-3 d-flex gap-2">
                        <button class="btn btn-primary" type="submit">등록하기</button>
                        <a class="btn btn-secondary" href="<%= request.getContextPath() %>/product?action=list">취소</a>
                    </div>
                </form>
            </div>
        </div>

    </section>
</div>
</body>
</html>