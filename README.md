# 블라인드 게시판 (Anonymous Board)

익명/가명(스레드별 별명) 게시가 가능한 **Spring Boot 3.x + Java 21** 백엔드 템플릿입니다. JPA/QueryDSL, Redis, PostgreSQL, Flyway, springdoc-openapi, Testcontainers를 기본 포함하며, 익명화·레이트리밋·신고/모더레이션·감사 로그 등 운영 기능까지 갖춘 실무형 보일러플레이트입니다.

> 이 README는 **예시 구조**를 기준으로 작성되었습니다. 실제 저장소에 맞게 패키지/모듈/경로를 조정하세요.

---

## 주요 기능

- **익명/가명 게시**: 사용자 실아이디 비노출, *스레드별 고정 별명* 부여  
- **게시글/댓글/대댓글/반응(좋아요)**  
- **신고/모더레이션**: 신고 사유·누적 기준 자동 조치, 운영자 조치 이력  
- **레이트리밋/스팸 차단**: IP/사용자 기준 버킷형 제한 + 금칙어 필터  
- **검색/정렬/페이징**: 최신/인기/댓글많음, 키워드 검색  
- **감사 로그**: 민감 이벤트 서버측 감사 기록  
- **OpenAPI 문서**: `/swagger-ui` 또는 `/docs`  
- **테스트**: Testcontainers로 DB/Redis 통합 테스트

---

## 기술 스택

- **Runtime**: Java 21, Spring Boot 3.3+
- **Build**: Gradle 8.x
- **DB**: MariaDB
- **Cache/RateLimit**: Redis 7.x
- **ORM**: JPA + QueryDSL 5.x
- **Migration**: Flyway
- **Auth**: JWT (Access + Refresh), 토큰 블랙리스트
- **Docs**: springdoc-openapi (OpenAPI 3.1)
- **Test**: JUnit 5, Testcontainers

---
