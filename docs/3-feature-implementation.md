# 3-feature-implementation.md

## 3.1 기능 구현 현황


회원 관리 및 리뷰·평점 시스템 기능(REQ-001 ~ REQ-011) : 20230566 조유나 

| 요구사항 번호 | 간단 설명                      | 관련 소스                                                         | 구현 여부 |
| ------- | -------------------------- | ------------------------------------------------------------- | ----- |
| REQ-001 | 회원가입 기능                    | src/main/java/hnu/ice/member/controller/MemberController.java | ✅ 완성  |
| REQ-002 | 로그인 기능                     | src/main/java/hnu/ice/member/controller/MemberController.java | ✅ 완성  |
| REQ-003 | 로그아웃 기능                    | src/main/java/hnu/ice/member/controller/MemberController.java | ✅ 완성  |
| REQ-004 | 회원 정보 조회(마이페이지)            | src/main/webapp/mypage.jsp                                    | ✅ 완성  |
| REQ-005 | 리뷰 작성 기능                   | src/main/java/hnu/ice/review/controller/ReviewController.java | ✅ 완성  |
| REQ-006 | 리뷰 목록 조회 기능                | src/main/webapp/review-list.jsp                               | ✅ 완성  |
| REQ-007 | 리뷰 삭제 기능                   | src/main/java/hnu/ice/review/controller/ReviewController.java | ✅ 완성  |
| REQ-008 | 평점 등록 기능                   | src/main/java/hnu/ice/review/dao/ReviewDao.java               | ✅ 완성  |
| REQ-009 | 평균 평점 계산 기능                | src/main/java/hnu/ice/review/dao/ReviewDao.java               | ✅ 완성  |
| REQ-010 | H2 Database 연동 및 자동 테이블 생성 | src/main/java/hnu/ice/common/DBConnection.java                | ✅ 완성  |
| REQ-011 | UTF-8 인코딩 처리               | src/main/java/hnu/ice/common/EncodingFilter.java              | ✅ 완성  |

상품 관리 기능(REQ-012 ~ REQ-020) : 20232878 윤광훈

| 요구사항 번호 | 간단 설명                      | 관련 소스                                                         | 구현 여부 |
| ------- | -------------------------- | ------------------------------------------------------------- | ----- |
| REQ-012 | 상품 등록 기능                   | src/main/java/kr/hnu/ice/product/controller/ProductController.java | ✅ 완성  |
| REQ-013 | 상품 수정 기능                   | src/main/java/kr/hnu/ice/product/controller/ProductController.java | ✅ 완성  |
| REQ-014 | 상품 삭제 기능                   | src/main/java/kr/hnu/ice/product/controller/ProductController.java | ✅ 완성  |
| REQ-015 | 상품 상세 조회 기능                | src/main/webapp/product/product-detail.jsp                    | ✅ 완성  |
| REQ-016 | 상품 목록 조회 기능                | src/main/webapp/product/product-list.jsp                      | ✅ 완성  |
| REQ-017 | 상품 검색 기능                   | src/main/java/kr/hnu/ice/product/dao/ProductDao.java          | ✅ 완성  |
| REQ-018 | 카테고리 필터링 기능               | src/main/java/kr/hnu/ice/product/dao/ProductDao.java          | ✅ 완성  |
| REQ-019 | 가격순/최신순 정렬 기능             | src/main/java/kr/hnu/ice/product/dao/ProductDao.java          | ✅ 완성  |
| REQ-020 | 상품 이미지 업로드 및 저장          | src/main/java/kr/hnu/ice/product/controller/ProductController.java | ✅ 완성  |
| REQ-021 | 상품 관련 DB 직접 구현            | 없음                                                            | ✅ 완성  |

---

## 3.2 구현 내용 설명

### REQ-001 회원가입 기능

* 구현 여부: ✅ 완성
* 관련 소스: MemberController.java, MemberDao.java, register.jsp
* 설명: 사용자가 아이디, 비밀번호, 닉네임, 이메일을 입력하여 회원가입을 진행할 수 있도록 구현하였다. 입력된 정보는 H2 데이터베이스의 users 테이블에 저장된다.

### REQ-002 로그인 기능

* 구현 여부: ✅ 완성
* 관련 소스: MemberController.java, login.jsp
* 설명: 등록된 사용자 정보를 기반으로 로그인 기능을 구현하였다. 로그인 성공 시 세션에 사용자 정보를 저장한다.

### REQ-003 로그아웃 기능

* 구현 여부: ✅ 완성
* 관련 소스: MemberController.java
* 설명: 세션을 제거하여 로그아웃을 수행하도록 구현하였다.

### REQ-004 마이페이지 기능

* 구현 여부: ✅ 완성
* 관련 소스: mypage.jsp
* 설명: 로그인한 사용자의 회원 정보와 평균 평점, 받은 리뷰 개수를 확인할 수 있도록 구현하였다.

### REQ-005 리뷰 작성 기능

* 구현 여부: ✅ 완성
* 관련 소스: ReviewController.java, review-write.jsp
* 설명: 로그인한 사용자가 다른 사용자에 대한 리뷰와 평점을 등록할 수 있도록 구현하였다.

### REQ-006 리뷰 목록 조회 기능

* 구현 여부: ✅ 완성
* 관련 소스: review-list.jsp
* 설명: 등록된 리뷰를 목록 형태로 출력하며 작성자, 대상 사용자, 평점, 리뷰 내용, 작성일을 확인할 수 있다.

### REQ-007 리뷰 삭제 기능

* 구현 여부: ✅ 완성
* 관련 소스: ReviewController.java
* 설명: 등록된 리뷰를 삭제할 수 있도록 구현하였다.

### REQ-008 평점 등록 기능

* 구현 여부: ✅ 완성
* 관련 소스: ReviewDao.java
* 설명: 리뷰 작성 시 1~5점 범위의 평점을 함께 저장하도록 구현하였다.

### REQ-009 평균 평점 계산 기능

* 구현 여부: ✅ 완성
* 관련 소스: ReviewDao.java
* 설명: SQL의 AVG 함수를 이용하여 사용자의 평균 평점을 계산하도록 구현하였다.

### REQ-010 H2 Database 연동

* 구현 여부: 🔶 부분완성
* 관련 소스: DBConnection.java
* 설명: 초기에는 MySQL Database를 활용하여 회원 정보와 리뷰 데이터를 저장하는 기능을 구현할 계획이었다. 그러나 개발 환경에서 MySQL 서버 연결 및 설정 과정에 문제가 발생하여 정상적인 연동을 완료하지 못하였다. 대신 프로젝트 기능 구현 및 테스트를 위해 H2 Database를 적용하였다. JDBC를 통해 H2 Database와 연동하였으며, 프로그램 실행 시 users 테이블과 reviews 테이블이 자동 생성되도록 구현하였다. 따라서 데이터 저장 및 조회 기능은 정상적으로 동작하지만, MySQL 기반 연동은 완료되지 않아 부분완성으로 분류하였다H2 데이터베이스를 사용하며 프로그램 실행 시 users, reviews 테이블이 자동 생성되도록 구현하였다.

### REQ-011 UTF-8 인코딩 처리

* 구현 여부: ✅ 완성
* 관련 소스: EncodingFilter.java
* 설명: 한글 데이터 처리를 위해 UTF-8 인코딩 필터를 적용하였다.

### REQ-012 상품 등록 기능
* 구현 여부: ✅ 완성
* 관련 소스: ProductController.java, ProductDao.java, product-create.jsp
* 설명: 상품명, 가격, 카테고리, 설명을 입력받아 DB에 저장한다. UUID를 활용한 고유 상품 ID 생성을 지원한다.

### REQ-013 상품 수정 기능
* 구현 여부: ✅ 완성
* 관련 소스: ProductController.java, product-edit.jsp
* 설명: 기존 상품 정보를 불러와 수정할 수 있으며, 거래 상태(판매중/거래완료) 변경 기능을 포함한다.

### REQ-014 상품 삭제 기능
* 구현 여부: ✅ 완성
* 관련 소스: ProductController.java, ProductDao.java
* 설명: 특정 상품 정보를 DB에서 삭제하며, 서버에 저장된 관련 이미지 파일도 함께 삭제하도록 구현하였다.

### REQ-015 상품 상세 조회 기능
* 구현 여부: ✅ 완성
* 관련 소스: ProductController.java, product-detail.jsp
* 설명: 특정 상품의 모든 상세 정보와 등록된 이미지를 확인할 수 있는 전용 페이지를 제공한다.

### REQ-016 상품 목록 조회 기능
* 구현 여부: ✅ 완성
* 관련 소스: ProductController.java, product-list.jsp
* 설명: 전체 등록된 상품을 카드 레이아웃 형태로 출력하며, 페이징 처리를 고려한 리스트 조회를 수행한다.

### REQ-017 상품 검색 기능
* 구현 여부: ✅ 완성
* 관련 소스: ProductDao.java, product-search.jsp
* 설명: 상품명, 카테고리, 설명을 대상으로 키워드 검색을 수행하여 필터링된 결과를 보여준다.

### REQ-018 카테고리 필터링 기능
* 구현 여부: ✅ 완성
* 관련 소스: ProductDao.java, ProductController.java
* 설명: DB에 등록된 카테고리 목록을 동적으로 가져와 드롭다운 필터를 구성하고, 선택된 카테고리 상품만 선별하여 출력한다.

### REQ-019 가격순/최신순 정렬 기능
* 구현 여부: ✅ 완성
* 관련 소스: ProductDao.java, ProductController.java
* 설명: 최신순, 가격 낮은순, 가격 높은순 등 사용자가 선택한 정렬 기준에 따라 SQL 쿼리를 동적으로 생성하여 결과를 제공한다.

### REQ-020 상품 이미지 업로드 및 저장
* 구현 여부: ✅ 완성
* 관련 소스: ProductController.java
* 설명: MultipartConfig를 사용하여 상품 이미지를 서버 특정 경로에 업로드하고, DB에는 해당 경로를 저장하여 상품과 매핑한다.

### REQ-021 상품 관련 DB 직접 구현
* 구현 여부: ✅ 완성
* 관련 소스: 없음
* 설명: XAMPP MySQL 환경에서 상품 관리 기능을 수행하기 위한 전용 데이터베이스와 테이블 스키마를 직접 설계하고 구축하였다.

---

**참고: 본 프로젝트의 상품페이지 백엔드 관련 프롬프트는 `docs` 폴더에 첨부된 StarUML 설계 이미지를 AI에게 제공하여 구조를 분석하게 함으로써 구현 완성도를 높였다.**
