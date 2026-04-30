# Requirements_Analysis

##  기능 구현 과정

### 1. 회원가입 및 로그인
- JSP를 통해 사용자 입력을 받는다.
- Servlet에서 요청을 처리하고 DAO를 통해 DB에 저장한다.
- 로그인 성공 시 Session에 사용자 정보를 저장한다.

---

### 2. 후기 작성 기능
- 사용자는 거래 완료된 상품에 대해 후기 작성 가능
- ReviewServlet에서 요청을 받아 DB에 저장
- 한 상품에 대해 중복 후기 작성 방지 로직 적용

---

### 3. 평점 계산 기능
- 특정 사용자에 대한 모든 리뷰를 조회
- 평균 평점 계산 후 화면에 출력

예시 SQL:
SELECT AVG(rating) FROM reviews WHERE target_user_id = ?

---

### 4. 후기 조회 기능
- 특정 사용자의 후기 리스트 조회
- JSP에서 리스트 형태로 출력