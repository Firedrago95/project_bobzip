# bobzip 프로젝트
냉장고 관리 및 레시피 검색 사이트 개발

## 목표
1. Spring data JPA, QueryDsl 과 Spring MVC의 주요기능에 대한 숙달 및 문제해결 과정의 정리
2. 서비스 기획 (ER 다이어그램 작성, 기능구현 목록)부터 배포까지 서비스 생애주기의 전 과정을 혼자 경험

# 목차
1. [기술 스택](#기술-스택)
2. [ER 다이어그램](#er-다이어그램)
3. [기능 구현 목록](#프로젝트-기능-구현-목록)


# 기술 스택
<div >
  <h3> FrontEnd</h3>
  <img src="https://img.shields.io/badge/thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white">
  <img src="https://img.shields.io/badge/bootstap-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white">
  <img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=white">
  <img src="https://img.shields.io/badge/jQuery-0769AD?style=for-the-badge&logo=jQuery&logoColor=white">

</div>
<div >
  <h3> BackEnd</h3>
  <img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
  <img src="https://img.shields.io/badge/Spring_Data_Jpa-00E47C?style=for-the-badge&logo=amazondynamodb&logoColor=white">
  <img src="https://img.shields.io/badge/QueryDsl-0769AD?style=for-the-badge&logo=springboot&logoColor=white">
  
</div>

# ER 다이어그램
![img.png](img.png)

# 프로젝트 기능 구현 목록

### 회원 관리
- [x] **회원 가입**
  - [x] 회원 중복 검사 - [사용자정의 BeanValidation](https://flowerdragon95.tistory.com/196)
- [x] **회원 탈퇴**
- [x] **로그인 검증**
  - [x] 로그인 확인 공통관심사 - [인터셉터 interceptor](https://flowerdragon95.tistory.com/197)

### 재료 관리
- [x] **재료 등록**
  - [x] 재료 중복 검증

### 레시피 관리
- [x] **레시피 등록**
  - [x] 등록 정보 검증
  - [x] 테스트하기 편한 엔티티 생성
    - [빌더패턴 Builder](https://flowerdragon95.tistory.com/195)
- [x] **모든 레시피 조회**
  - [x] 페이징
    - [페이지네이션 (javascript)](https://flowerdragon95.tistory.com/201)
  - [x] 레시피 작성자와 로그인 사용자 일치 확인 
    - [JPA 엔티티의 equals 오버라이딩](https://flowerdragon95.tistory.com/200)
- [x] **레시피 정보 상세보기**
  - [x] 로컬에 저장된 이미지를 참조하기 
    - [리소스 경로 설정하기 ResourceHandler](https://flowerdragon95.tistory.com/199)
- [x] **레시피 삭제**
  - [x] [예외처리] 레시피 작성자가 아닌 권한 없는 삭제 요청 거부
    - [예외처리 @ExceptionHandler]()
- [x] **레시피 검색**
  - [x] [예외처리] 검색 결과가 없는 경우 결과 없음 화면 출력
- [x] **레시피 수정** 
  - [다중 MultipartFile 이미지를 포함한 엔티티 수정](https://flowerdragon95.tistory.com/202)
- [x] **레시피 좋아요**
  - [x] 레시피 좋아요 조회
  - [x] 레시피 좋아요 취소
  - [x] 좋아요 누른 레시피 조회

### 냉장고 관리
- [x] **냉장고 재료 조회**
- [x] **냉장고 재료 등록**
- [x] **냉장고 보관 재료로 레시피 검색**
  - [Querydsl활용하여 냉장고 재료로 레시피 검색하기](https://flowerdragon95.tistory.com/207)

### 댓글 관리
- [x] **댓글 조회**
- [x] **댓글 작성**
- [x] **댓글 수정**
- [x] **댓글 삭제**
- [AJAX 요청을 통한 댓글 조회](https://flowerdragon95.tistory.com/204), [댓글 작성](https://flowerdragon95.tistory.com/205), [댓글 수정](https://flowerdragon95.tistory.com/206)



