### ✅ 테스트 환경

- IntelliJ
- Java 21
- Spring Boot 3.4
- JUnit 5
- Mockito
- Docker
- Spring Data JPA
- MySQL 8.0
- Flyway

### ✅ 정적 코드 테스트

#### 정적 코드

- 코드가 동작하고 있지 않을 때의 상태 의미
- 정적 코드 테스트는 애플리케이션 실행하지 않은 상태에서 소스 코드들을 분석하고 검증하는 테스트

#### 컨벤션 검증 예시

```
class ArchitectureTest {
    
    JavaClasses javaClasses;
    
    // 테스트 클래스들은 이 검증에서 제외하고 main 디렉토리만 검증
    @BeforeEach
    void beforeEach() {
        javaClasses = new ClassFileImporter()
            .withImportOption(new ImportOption.DoNotIncludeTests())
            .importPackages("com.jyujyu.dayonetest");
    }
    
    @Test
    @DisplayName("controller 패키지 안에 있는 클래스들은 Controller로 끝나야 합니다")
    void controllerTest() {
        ArchRule rule = classes()
            .that().resideInAnyPackage("..controller")
            .should().haveSimpleNameEndingWith("Api");
    
        ArchRule annotationRule = classes()
            .that().resideInAnyPackage("..controller")
            .should().beAnnotatedWith(RestController.class)
            .orShould().beAnnotatedWith(Controller.class);
    
        rule.check(javaClasses);
        annotationRule.check(javaClasses);
    }
    
    @Test
    @DisplayName("request 패키지 안에 있는 클래스는 Request로 끝나야 합니다.")
    void requestTest() {
        ArchRule rule = classes()
            .that().resideInAnyPackage("..request..")
            .should().haveSimpleNameEndingWith("Request");
    
        rule.check(javaClasses);
    }
    
    @Test
    @DisplayName("response 패키지 안에 있는 클래스는 Response로 끝나야 합니다.")
    void responseTest() {
        ArchRule rule = classes()
            .that().resideInAnyPackage("..response..")
            .should().haveSimpleNameEndingWith("Response");
    
        rule.check(javaClasses);
    }
    
    @Test
    @DisplayName("repository 패키지 안에 있는 클래스는 Repository로 끝나야 하고, 인터페이스여야 합니다.")
    void repositoryTest() {
        ArchRule rule = classes()
            .that().resideInAnyPackage("..repository..")
            .should().haveSimpleNameEndingWith("Repository")
            .andShould().beInterfaces();
    
        rule.check(javaClasses);
    }
    
    @Test
    @DisplayName("service 패키지 안에 있는 클래스는 Service로 끝나야 하고, @Service 애너테이션이 붙어있어야 합니다")
    void serviceTest() {
        ArchRule rule = classes()
            .that().resideInAnyPackage("..service..")
            .should().haveSimpleNameEndingWith("Service")
            .andShould().beAnnotatedWith(Service.class);
    
        rule.check(javaClasses);
    }
    
    @Test
    @DisplayName("config 패키지 안에 있는 클래스는 Config로 끝나야 하고, @Configuration 애너테이션이 붙어있어야 합니다.")
    void configTest() {
        ArchRule rule = classes()
            .that().resideInAnyPackage("..config..")
            .should().haveSimpleNameEndingWith("Config")
            .andShould().beAnnotatedWith(Configuration.class);
    
        rule.check(javaClasses);
    }
}
```

#### 의존성 검증 예시

```
@Test
@DisplayName("Controller는 Service와 Request/Response를 사용할 수 있음")
void controllerDependencyTest() {
    ArchRule rule = classes()
        .that().resideInAnyPackage("..controller")
        .should().dependOnClassesThat()
        .resideInAnyPackage("..request..", "..response..", "..service..");

    rule.check(javaClasses);
}

@Test
@DisplayName("Controller는 의존되지 않음")
void controllerDependencyTest2() {
    ArchRule rule = classes()
        .that().resideInAnyPackage("..controller")
        .should().onlyHaveDependentClassesThat().resideInAnyPackage("..controller");

    rule.check(javaClasses);
}

@Test
@DisplayName("Controller는 모델을 사용할 수 없음")
void controllerDependencyTest3() {
    ArchRule rule = noClasses()
        .that().resideInAnyPackage("..controller")
        .should().onlyHaveDependentClassesThat().resideInAnyPackage("..model..");

    rule.check(javaClasses);
}

@Test
@DisplayName("Service는 Controller를 의존하면 안됨")
void serviceDependencyTest() {
    ArchRule rule = noClasses()
        .that().resideInAnyPackage("..service..")
        .should().dependOnClassesThat().resideInAnyPackage("..controller");

    rule.check(javaClasses);
}

@Test
@DisplayName("Model은 오직 Service와 Repository에 의해 의존됨")
void modelDependencyTest() {
    ArchRule rule = classes()
        .that().resideInAnyPackage("..model..")
        .should().onlyHaveDependentClassesThat().resideInAnyPackage("..repository..", "..service..", "..model..");

    rule.check(javaClasses);
}

@Test
@DisplayName("Model은 아무것도 의존하지 않음")
void modelDependencyTest2() {
    ArchRule rule = classes()
        .that().resideInAnyPackage("..model..")
        .should().onlyDependOnClassesThat()
        .resideInAnyPackage("..model..", "java..", "jakarta..", "lombok..");

    rule.check(javaClasses);
}
```

### ✅ Reference

- [스프링 테스트 By 쥬쥬](https://www.inflearn.com/course/%EC%A5%AC%EC%A5%AC%EC%99%80-%ED%95%98%EB%A3%A8%EB%A7%8C%EC%97%90-%EB%81%9D%EB%82%B4%EB%8A%94-%EC%8A%A4%ED%94%84%EB%A7%81%ED%85%8C%EC%8A%A4%ED%8A%B8)