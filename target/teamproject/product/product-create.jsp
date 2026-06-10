<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>상품 등록</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/product.css">
</head>
<body>
<div class="page-shell">
    <section class="hero">
        <span class="eyebrow">상품 등록</span>
        <h1 class="section-title">상품을 등록해주세요</h1>
        
        <div class="hero-actions">
            <a class="btn btn-secondary" href="<%= request.getContextPath() %>/product?action=list">목록으로</a>
            <a class="btn btn-secondary" href="<%= request.getContextPath() %>/product?action=search">검색하기</a>
        </div>
    </section>

    <section class="panel form-layout">
        <form class="form-card" action="<%= request.getContextPath() %>/product?action=create" method="post" enctype="multipart/form-data">
            <div class="stack">
                <h2 class="form-title">상품 정보</h2>
            </div>

            <div class="form-grid">
                <div class="field">
                    <label for="productId">상품명</label>
                    <input id="productId" type="text" name="productId" required placeholder="예: 짧고 굵게 배우는 jsp 웹 프로그래밍과 스프링 프레임워크">
                </div>
                <div class="field-group">
                    <div class="field">
                        <label for="title">제목</label>
                        <input id="title" type="text" name="title" required placeholder="ex:전공교재 판매합니다">
                    </div>
                    <div class="field">
                        <label for="sellerId">판매자 ID</label>
                        <input id="sellerId" type="text" name="sellerId" required placeholder="판매자 아이디">
                    </div>
                </div>
                <div class="field-group">
                    <div class="field">
                        <label for="price">가격</label>
                        <input id="price" type="number" name="price" min="0" step="1" required placeholder="0">
                    </div>
                    <div class="field">
                        <label for="category">카테고리</label>
                        <select id="category" name="category" required>
                            <option value="" disabled selected>카테고리를 선택하세요</option>
                            <option value="도서">도서</option>
                            <option value="전자기기">전자기기</option>
                            <option value="의류">의류</option>
                            <option value="생활용품">생활용품</option>
                            <option value="가구">가구</option>
                            <option value="취미">취미</option>
                            <option value="기타">기타</option>
                        </select>
                    </div>
                    <div class="field">
                        <label for="imageFile">상품 이미지</label>
                        <input id="imageFile" type="file" name="imageFile" accept="image/*">
                    </div>
                </div>
                <div class="field">
                    <label for="description">설명</label>
                    <textarea id="description" name="description" rows="8" placeholder="상품 설명을 입력하세요."></textarea>
                </div>
            </div>

            <div class="form-actions">
                <button class="btn btn-primary" type="submit">등록하기</button>
                <a class="btn btn-secondary" href="<%= request.getContextPath() %>/product?action=list">취소</a>
            </div>
        </form>

        <aside class="preview-image">
            <div class="preview-placeholder" id="createPreviewPlaceholder">
                <div>
                    <div class="badge is-muted">Image Preview</div>
                    <p class="preview-note">파일을 선택하면 오른쪽 미리보기 영역에 표시됩니다.</p>
                </div>
            </div>
            <img id="createPreviewImage" alt="선택한 상품 이미지 미리보기" style="display:none;">
        </aside>
    </section>
</div>

<script>
    (function () {
        var input = document.getElementById('imageFile');
        var previewImage = document.getElementById('createPreviewImage');
        var placeholder = document.getElementById('createPreviewPlaceholder');

        input.addEventListener('change', function () {
            var file = this.files && this.files[0];
            if (!file) {
                previewImage.src = '';
                previewImage.style.display = 'none';
                placeholder.style.display = 'grid';
                return;
            }

            var reader = new FileReader();
            reader.onload = function (event) {
                previewImage.src = event.target.result;
                previewImage.style.display = 'block';
                placeholder.style.display = 'none';
            };
            reader.readAsDataURL(file);
        });
    }());
</script>
</body>
</html>