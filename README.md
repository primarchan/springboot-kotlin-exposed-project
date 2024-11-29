
# SPRINGBOOT-KOTLIN-EXPOSED-PROJECT

Spring Boot, Kotlin, Exposed를 기반으로 한 표준 API 프로젝트
- Kotlin과 Exposed ORM을 활용하여 간결하고 유지보수 가능한 코드를 작성하도록 설계

---

## 프로젝트 개요

### 프로젝트 목표
- Kotlin과 Exposed를 활용한 간결하고 효율적인 데이터 액세스 계층 구현.
- Spring Boot를 기반으로 한 API 서버 개발.
- 표준화된 코드와 최신 기술 스택을 적용하여 유지보수성과 확장성을 강화.

---

## 주요 기술 스택

| **기술/도구** | **버전** | **설명**                                |
|---------------|--------|----------------------------------------|
| Kotlin        | 1.9.25 | 현대적이고 간결한 프로그래밍 언어.      |
| JDK           | 17     | 프로젝트의 런타임 환경.                |
| Spring Boot   | 3.3.0  | Spring Framework 기반의 서버 개발 플랫폼.|
| Exposed       | 0.49.0 | Kotlin 기반 ORM 라이브러리.            |
| PostgreSQL    | 42.7.2 | 관계형 데이터베이스.                    |
| SpringDoc OpenAPI | 2.2.0 | Swagger 기반 API 문서화 라이브러리.   |

---

## 주요 기능

### 1. 표준화된 응답 포맷 제공
- 성공/실패, 에러 등을 표준화된 포맷으로 제공하며, 확장 가능한 설계를 적용

### 2. 커서 기반 페이지네이션
- 효율적인 대규모 데이터 조회 지원.
- `lastId` 및 `size` 파라미터를 활용한 커서 기반 페이지네이션.

### 3. 로깅 및 AOP
- HTTP 요청/응답 로깅을 위한 **AOP** 적용.
- 특정 클래스 및 메서드에서 로깅 활성화를 위한 **커스텀 어노테이션**(`@EnableRequestLogging`) 사용.

### 4. 유효성 검증 및 예외 처리
- 요청 데이터에 대한 유효성 검증을 위한 **Jakarta Validation** 적용.
- **GlobalExceptionHandler**를 활용한 전역 및 도메인별 예외 처리.

### 5. Exposed 기반의 데이터 액세스 계층
- Join 쿼리 및 개별 조회를 활용한 데이터 처리.
- Domain 및 Response DTO 간 명확한 책임 분리.

### 6. Swagger 기반 API 문서화
- **SpringDoc OpenAPI**를 활용하여 Swagger UI를 통한 API 문서 제공.
- OpenAPI 명세를 기반으로 API 테스트 및 탐색 가능.

---

## 주요 의존성

### 필수 의존성
- **Spring Boot AOP**: AOP 기반 로깅 및 관점 지향 프로그래밍 지원.
- **Jackson Module for Kotlin**: JSON 직렬화/역직렬화를 위한 Kotlin 모듈.
- **Spring Boot Validation**: 데이터 유효성 검증.
- **Exposed Core/DAO/JDBC**: 데이터베이스와의 효율적인 상호작용.
- **PostgreSQL Driver**: PostgreSQL 데이터베이스와의 연결 지원.
- **SpringDoc OpenAPI**: Swagger 기반 API 문서화 지원.

### 개발용 의존성
- **Spring Boot DevTools**: 코드 변경 시 빠른 리로딩 지원.
- **JUnit 5**: 단위 테스트 및 통합 테스트 지원.

---

## 프로젝트 설정

### 1. Kotlin 컴파일러 옵션
- `-Xjsr305=strict`: Java와 Kotlin의 null 안정성 통합.

### 2. JDK 설정
- **Java 17**: 최신 언어 기능을 활용하기 위해 JDK 17 사용.

### 3. 애노테이션 처리
- Jakarta Persistence와 호환을 위해 `@Entity`, `@MappedSuperclass`, `@Embeddable` 애노테이션을 열어둠.

### 4. OpenAPI 설정
- **OpenApiConfig** 파일에서 프로젝트 정보(title, description, contact 등)를 명세화.
- API 서버 환경(local, production) 정의.

---

## 프로젝트 구조

```
src/
├── main/
│   ├── kotlin/org/example/springbootkotlinexposedproject/
│   │   ├── common/         # 공통 유틸 및 로깅
│   │   ├── domain/         # 도메인별 서비스 및 컨트롤러
│   │   ├── logging/        # 로깅 관련 AOP
│   │   ├── domain01/       # 도메인 관련 로직
│   └── resources/
│       ├── application.yml # 애플리케이션 설정 파일
│       └── db/             # DB 마이그레이션 스크립트
```

---

## 프로젝트 실행 방법

### 1. 환경 설정
- `application.yml`에서 DB 및 로컬 설정 확인.

### 2. 빌드 및 실행
```bash
./gradlew clean build
java -jar build/libs/springboot-kotlin-exposed-project-0.0.1-SNAPSHOT.jar
```

### 3. API 테스트
- API 요청 및 응답 확인을 위해 Postman 또는 HTTP 클라이언트 사용.

---

## 추가 적용 계획
- **ELK Stack 연동**: 중앙 집중식 로그 관리를 위해 Elasticsearch, Logstash, Kibana 적용.
- **테스트 케이스 강화**: 통합 테스트 및 성능 테스트 추가.
- **Swagger**: API 문서화를 위한 Swagger 적용.

---