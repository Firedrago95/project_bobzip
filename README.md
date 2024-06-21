# bobzip 프로젝트
냉장고 관리 및 레시피 검색 사이트 개발 


## 목차
1. [기능구현 목록](#기능구현-목록)
2. [ER 다이어그램](#er-다이어그램)
3. [문제 해결](#문제_해결)


## 기능구현 목록

* 회원 관리
  * [x] 회원 가입
    * [x] 회원 중복 검사 => [사용자정의 Validation으로 중복검사 하기 (BeanValidation)](https://flowerdragon95.tistory.com/196)
  * [x] 회원 탈퇴 
  * [x] 로그인 검증 => [로그인 확인 공통관심사 (인터셉터)](https://flowerdragon95.tistory.com/197) 
* 재료
  * [x] 재료 등록
    * [x] 재료 중복 검증
* 레시피
  * [x] 레시피 등록 => [테스트하기 편한 엔티티 생성 (Builder)](https://flowerdragon95.tistory.com/195)
    * [x] 등록 정보 검증
  * [x] 모든 레시피 조회
    * [x] 페이징 => [타임리프 페이지네이션 만들기 (javascript)](https://flowerdragon95.tistory.com/201)
    * [x] 레시피 작성자와 로그인 사용자 일치 확인 => [JPA 엔티티의 equals 오버라이딩](https://flowerdragon95.tistory.com/200)
  * [x] 레시피 정보 상세보기 => [로컬에 저장된 이미지를 참조하기 (ResourceHandler)](https://flowerdragon95.tistory.com/199)
  * [x] 레시피 삭제
    * [x] [예외처리] 레시피 작성자가 아닌 권한없는 삭제요청 거부
  * [x] 레시피 검색 
    * [x] [예외처리] 검색결과가 없는 경우 결과없음 화면 출력
* 냉장고 관리
  * [ ] 냉장고 보관재료 등록 
  * [ ] 냉장고 삭제
  * [ ] 냉장고 보관재료로 레시피 검색
* 댓글 관리
  * [ ] 댓글 작성
  * [ ] 댓글 삭제
  * [ ] 댓글 수정


## ER 다이어그램
![img.png](img.png)
