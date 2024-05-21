# Redis를 이용한 동시성 처리

## 순서
- 간단한 도서재고관리 시스템을 개발하여 동시성 이슈를 발생시킨다.
- 레디스가 제공하는 분산락을 적용하여 분산 환경에서의 동시성 이슈를 처리한다. (Redis Distributed Lock)

## 기술검토 (Lettuce vs Redisson)

- 배경: 분산락을 구현하기 위해 일반적으로 Lettuce와 Redisson이 많이 사용된다.
- 결과: Redisson 사용하기로 결정
- 이유1: 개발자가 직접 락 프로세스를 구현해줘야 하는 Lettuce와 달리 Redisson은 인터페이스를 제공해주므로 구현이 간단하다.
- 이유2: Lettuce는 락을 획득할 때까지 Redis에 요청을 보내는 Spin Lock 방식을 차용하기 때문에 부하가 발생할 수 있다. Redisson 은 Pub/Sub 구조로 Redis를 subscribe 하다가 락을 점유하면 알림을 받는 방식이므로 Redis에 대한 트래픽을 크게 줄일 수 있다.    



## 개발 환경
- IntelliJ IDEA
- JDK 17
- Gradle 7.6
- Spring Boot 2.7.6
- Spring Data JPA
- H2 Database
- Redisson
- Lombok
- JUnit5

## 테스트케이스 (1)

- 상황: 여러 사용자가 동시에 도서 주문 (분산락 미적용)
- 결과: 총 n권의 책이 존재할 때 n권을 주문 시 재고 불일치 문제 발생  

## 테스트케이스 (2)

- 상황: 여러 사용자가 동시에 도서 주문 (분산락 적용)
- 결과: 총 n권의 책이 존재할 때 n권을 주문 시 재고는 0권이 되어 동시성 이슈 해결

### 출처
https://helloworld.kurly.com/blog/distributed-redisson-lock/




