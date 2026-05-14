# Project_Structure

## 프로젝트 디렉토리 구조 (기능 분리 MVC)

Requirements_Analysis 문서를 기준으로, 아래 2개 기능을 각각 독립적인 MVC 구조로 분리한다.

1. 회원/후기/평점 관리 기능
2. 상품관리 기능

---

## 1) 회원/후기/평점 관리 기능 MVC

- 사용 테이블: `users`, `reviews`

```text
member-review-rating/
├─ controller/
│  ├─ UserController.java
│  └─ ReviewController.java
├─ model/
│  ├─ dto/
│  │  ├─ User.java
│  │  └─ Review.java
│  ├─ dao/
│  │  ├─ UserDAO.java
│  │  └─ ReviewDAO.java
│  └─ service/
│     ├─ AuthService.java
│     └─ RatingService.java
├─ view/
│  ├─ member/
│  │  ├─ login.jsp
│  │  ├─ register.jsp
│  │  └─ mypage.jsp
│  └─ review/
│     ├─ review-write.jsp
│     └─ review-list.jsp
└─ sql/
	├─ users.sql
	└─ reviews.sql
```

### 역할
- Controller: 회원가입, 로그인, 후기 등록/조회, 평점 계산 요청 처리
- Model: `users`, `reviews` 데이터 접근 및 비즈니스 로직 처리
- View: 회원/후기/평점 관련 화면 출력

---

## 2) 상품관리 기능 MVC

- 사용 테이블: `product`

```text
product-management/
├─ controller/
│  └─ ProductController.java
├─ model/
│  ├─ dto/
│  └─ dao/
│     └─ ProductDAO.java
├─ view/
│  └─ product/
│     ├─ product-list.jsp
│     ├─ product-detail.jsp
│     ├─ product-create.jsp
│     ├─ product-edit.jsp
│     └─ product-search.jsp
└─ sql/
	└─ product.sql
```

### 역할
- Controller: 상품 등록/조회/수정/삭제, 검색, 정렬 요청 처리
- Model: `product` 테이블 데이터 처리
- View: 상품관리 및 상품검색 화면 출력

---

## 통합 배치 예시 (디렉토리 전체 기준)

```text
2026WebProgramClass_teamproject/
├─ README.md
├─ docs/
│  └─ Project_Overview README.md파일 작성할때 사용한 프롬프트
├─ Requirements_Analysis/
│  └─ README.md
├─ Project_Architecture_and_Schedule/
│  └─ README.md
├─ Feature_Implementation/
│  └─ README.md
├─ Testing_Optimization/
│  └─ README.md
├─ src/
│  ├─ member-review-rating/
│  │  ├─ controller/
│  │  │  ├─ UserController.java
│  │  │  └─ ReviewController.java
│  │  ├─ model/
│  │  │  ├─ dto/
│  │  │  │  ├─ User.java
│  │  │  │  └─ Review.java
│  │  │  ├─ dao/
│  │  │  │  ├─ UserDAO.java
│  │  │  │  └─ ReviewDAO.java
│  │  │  └─ service/
│  │  │     ├─ AuthService.java
│  │  │     └─ RatingService.java
│  │  └─ view/
│  │     ├─ member/
│  │     │  ├─ login.jsp
│  │     │  ├─ register.jsp
│  │     │  └─ mypage.jsp
│  │     └─ review/
│  │        ├─ review-write.jsp
│  │        └─ review-list.jsp
│  └─ product-management/
│     ├─ controller/
│     │  └─ ProductController.java
│     ├─ model/
│     │  ├─ dto/
│     │  └─ dao/
│     │     └─ ProductDAO.java
│     └─ view/
│        └─ product/
│           ├─ product-list.jsp
│           ├─ product-detail.jsp
│           ├─ product-create.jsp
│           ├─ product-edit.jsp
│           └─ product-search.jsp
├─ webapp/
  ├─ index.jsp (메인페이지)
  ├─ assets/
  │  ├─ css/
  │  │  └─ main.css
  │  ├─ js/
  │  │  └─ main.js
  │  └─ images/
  ├─ member/
  ├─ review/
  └─ product/
sql/
 ├─ users.sql
 ├─ reviews.sql
 └─ product.sql
```

---

## 3) 메인페이지 구조 (MVC 미사용)

- 사용 테이블: 없음 (데이터 접근 없음)

```text
webapp/
├─ index.jsp (메인페이지)
├─ assets/
│  ├─ css/
│  │  └─ main.css
│  ├─ js/
│  │  └─ main.js
│  └─ images/
└─ (메인 진입점)
```

### 역할
- View: 각 기능(회원/후기/평점, 상품관리)으로 이동할 수 있는 링크 제공
- Assets: 메인페이지 디자인(CSS, JavaScript, 이미지)

### 메인페이지 역할
메인페이지는 단순 정적 페이지로, 다음 모듈로의 접근 링크만 제공:
- 회원/후기/평점 모듈: `/member/login.jsp`, `/member/register.jsp` (로그인, 회원가입 등)
- 상품관리 모듈: `/product/product-list.jsp` (상품 목록, 검색 등)

---

위 구조를 사용하면 문서 디렉토리와 기능 구현 디렉토리를 함께 관리하면서, 회원/후기/평점 기능과 상품관리 기능을 서로 독립적인 MVC로 유지하고, 메인페이지는 각 모듈의 진입점 역할을 할 수 있다.

# 팀 역할 분담
## 윤광훈(팀장)
- 상품관리기능 구현
- product db 구현
- 메인페이지 구현 및 각모듈 통합
## 조유나
- 회원/후기/평점 관리 기능  구현
- users,reviews db구현 
- 

# 일정
## 1주차
- 상품관리기능,회원/후기/평점 관리기능 구현 및 db작성
## 2주차
- 프론트엔드 구현 및 부트스트랩 적용
## 3주차
- 메인페이지 작성 이후 각 모듈 통합
- 테스트 이후 버그수정