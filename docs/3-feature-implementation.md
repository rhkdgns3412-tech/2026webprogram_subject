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
