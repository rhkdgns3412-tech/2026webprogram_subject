# Feature_Implementation


##  회원 / 후기 / 평점 관리 기능 요구사항 분석

### 1. 회원 관리
- 사용자는 회원가입 및 로그인을 할 수 있다.
- 로그인 상태는 세션(Session)을 통해 유지된다.
- 사용자는 자신의 정보를 확인할 수 있다.

### 2. 후기 및 평점 기능
- 사용자는 거래 완료된 상품에 대해 후기를 작성할 수 있다.
- 후기 작성 시 평점(1~5점)과 간단한 코멘트를 입력할 수 있다.
- 한 거래에 대해 한 번만 후기를 작성할 수 있다.

### 3. 평점 관리
- 사용자의 평균 평점을 계산하여 표시한다.
- 특정 사용자의 후기 목록을 조회할 수 있다.

---

##  데이터베이스 설계

### users 테이블
- user_id (PK)
- username
- password
- nickname
- created_at

### reviews 테이블
- review_id (PK)
- product_id (FK)
- reviewer_id (FK)
- target_user_id (FK)
- rating (1~5)
- content
- created_at

---

##  관계 설명
- 한 사용자는 여러 개의 후기를 받을 수 있다.
- 한 거래(상품)는 하나의 후기로 연결된다.