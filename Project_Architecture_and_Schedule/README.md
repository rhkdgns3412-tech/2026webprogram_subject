# 프로젝트 구조

## 프로젝트 구조 (기능 분리 MVC)

아래 2개 기능을 각각 독립적인 MVC 구조로 분리한다.

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
└─  webapp/
    ├─ member/
    ├─ review/
    └─ product/
sql
├─ users.sql
├─ reviews.sql
└─ product.sql
```

# 팀 역할 분담
## 윤광훈(팀장)
- 상품관리기능 구현
- product db 구현
- merge 관리
## 조유나
- 회원/후기/평점 관리 기능  구현
- users,reviews db구현 

# 일정
## 1주차
- 상품관리기능,회원/후기/평점 관리기능 구현 및 db작성
## 2주차
- 각모듈 통합 테스트 및 부트스트랩으로 프론트엔드 구현
## 3주차
- 테스트 이후 버그수정