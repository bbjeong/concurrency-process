# concurrency-process
- 간단한 재고관리 시스템을 개발하여 동시성 이슈를 처리한다.
- 다양한 방법으로 동시성 이슈를 해결한다.

## 개발 환경
- IDE: IntelliJ IDEA
- JDK: 17
- Gradle: 7.6
- Spring Boot: 2.7.6
- Spring JPA
- Spring Data JPA
- H2 Database
- Lombok
- JUnit5

## 기능
- 재고관리 시스템은 다음과 같은 기능을 제공한다. 
  1. 재고 조회
  2. 재고 등록
  5. 재고 소진
  6. 재고 이력 조회

## 문제해결
- 재고관리 기능을 다양한 해결방법으로 처리한다. 
  - 방법1) Application Level
  - 방법2) Database Lock
  - 방법3) Redis Distributed Lock

## 테스트
- 각 방법별로 테스트 코드를 작성하여 동시성 이슈를 확인한다.




