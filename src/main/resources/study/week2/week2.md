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

### ✅ Test Double 패턴

테스트 더블(Test Double) 패턴

- 실제 의존성을 단순화된 또는 제어 가능한 객체로 대체하는 개념
- 테스트 대상 코드를 외부 시스템이나 구성 요소로부터 격리시켜 독립적으로 코드의 동작을 확인
- 외부 시스템, 데이터베이스, API 또는 복잡한 구성 요소와 상호작용할 때 효과적

1. **목(Mock)**: 테스트 중에 받아야 할 호출에 대한 예상값
2. **스텁(Stub)**: 특정 메서드 호출에 대해 미리 정의된 응답을 제공
3. **페이크(Fake)**: 페이크는 실제 구현과 유사한 인터페이스를 제공하는 단순화된 구현
4. **스파이(Spy)**: 받은 호출에 대한 정보를 기록하여 테스트 이후에 상호작용 검사 가능

### ✅ 간단한 성적 저장 애플리케이션

#### 요구사항

- 학생은 여러개의 시험 성적을 가지고 있습니다
- 학생의 시험 성적 정보를 저장할 수 있습니다
- 평균 점수가 60점 이상이라면 합격자 리스트에 저장됩니다
- 평균 점수가 60점 미만이라면 불합격자 리스트에 저장됩니다

```
@Service
@RequiredArgsConstructor
public class StudentScoreService {

    private final StudentScoreRepository studentScoreRepository;
    private final StudentPassRepository studentPassRepository;
    private final StudentFailRepository studentFailRepository;
    
    public void saveScore(String exam, String studentName, Integer korScore, Integer englishScore, Integer mathScore){
        StudentScore studentScore = StudentScore.builder()
            .exam(exam)
            .studentName(studentName)
            .korScore(korScore)
            .englishScore(englishScore)
            .mathScore(mathScore)
            .build();
    
        studentScoreRepository.save(studentScore);
    
        MyCalculator calculator = new MyCalculator(0.0);
        Double avgScore = calculator
            .add(korScore.doubleValue())
            .add(englishScore.doubleValue())
            .add(mathScore.doubleValue())
            .divide(3.0)
            .getResult();
    
        if (avgScore >= 60) {
            StudentPass studentPass = StudentPass.builder()
                .exam(exam)
                .studentName(studentName)
                .avgScore(avgScore)
                .build();
    
            studentPassRepository.save(studentPass);
        } else {
            StudentFail studentFail = StudentFail.builder()
                .exam(exam)
                .studentName(studentName)
                .avgScore(avgScore)
                .build();
    
            studentFailRepository.save(studentFail);
        }
    }
    
    public List<ExamPassStudentResponse> getPassStudentsList(String exam) {
        List<StudentPass> studentPasses = studentPassRepository.findAll();
    
        return studentPasses.stream()
            .filter(pass -> pass.getExam().equals(exam))
            .map(pass -> new ExamPassStudentResponse(pass.getStudentName(), pass.getAvgScore()))
            .toList();
    }
    
    public List<ExamFailStudentResponse> getFailStudentsList(String exam) {
        List<StudentFail> studentFails = studentFailRepository.findAll();
    
        return studentFails.stream()
            .filter(fail -> fail.getExam().equals(exam))
            .map(fail -> new ExamFailStudentResponse(fail.getStudentName(), fail.getAvgScore()))
            .toList();
    }
}
```

### ✅ Mock Test

#### Mockito란?

- 모킹 프레임워크(Mocking Framework)
- 깔끔하고 간단한 API로 테스트를 작성 가능
- Mockito는 테스트가 매우 가독성이 있으며, 깔끔한 에러를 보여줌

#### Mock Object 생성 방법

1. mock(): Mockito 라이브러리에서 가장 기본적이고 자주 사용되는 메서드 중 하나. 인터페이스, 추상 클래스 또는 구체 클래스의 모킹된 객체를
   생성

```
import org.mockito.Mockito;

public class Example {
    public interface MyObject {
        String getValue();
    }

    public static void main(String[] args) {
        // MyObject 인터페이스의 모킹된 객체 생성
        MyObject mockObject = Mockito.mock(MyObject.class);

        // 모킹된 객체의 동작 설정
        Mockito.when(mockObject.getValue()).thenReturn("Mocked value");

        // 모킹된 객체 사용
        System.out.println(mockObject.getValue()); // 출력: "Mocked value"
    }
}
```

2. spy(): 실제 객체를 사용하면서 일부 메서드를 모킹하여 테스트하는 데 사용. mock() 메서드와는 달리 spy() 메서드는 기존 객체를 래핑하여 테스트할 수 있음. 모킹된 메서드가 호출되면 실제 구현이
   실행되지만, 원하는 메서드에 대해서는 테스트에서 명시적인 동작을 설정할 수 있음

```
import org.mockito.Mockito;

public class Example {
    public static class MyObject {
        public String getValue() {
            return "Real Value";
        }
        
        public String getRealValue() {
            return "Real Value";
        }
    }

    public static void main(String[] args) {
        // MyDependency 클래스의 실제 객체 생성
        MyObject realObject = new MyObject();

        // 실제 객체를 스파이 객체로 래핑
        MyObject spyObject = Mockito.spy(realObject);

        // 일부 메서드의 동작을 모킹
        Mockito.when(spyObject.getValue()).thenReturn("Mocked value");

        // 스파이 객체 사용
        System.out.println(spyObject.getValue()); // 출력: "Mocked value"
        System.out.println(spyObject.getRealValue()); // 출력: "Real Value"
    }
}
```

#### 행동 검증

1. verify(): 메소드가 예상대로 호출되었는지, 호출 횟수가 맞는지, 호출 순서가 맞는지 등을 확인
2. times(): 특정 모의 객체(mock object)의 메소드 호출 횟수를 정확하게 검증.
3. ArgumentCaptor: 메소드 호출 시 전달된 인자들을 캡처하고 추출하는 기능을 제공하는 클래스. 특정 메소드가 호출될 때 전달된 인자들을 테스트 중에 검증하거나, 다른 곳에서 사용할 수 있도록 할 때
   사용

ArgumentCaptor 사용 예시

```
@Test
@DisplayName("성적 저장 로직 검증 / 60점 미만인 경우")
void saveScoreMockTest2() {
    // given : 평균 점수가 60점 미만인 경우
    StudentScore expectStudentScore = StudentScoreFixture.failed();
    StudentFail expectStudentFail = StudentFailFixture.create(expectStudentScore);

    ArgumentCaptor<StudentScore> studentScoreArgumentCaptor = ArgumentCaptor.forClass(StudentScore.class);
    ArgumentCaptor<StudentFail> studentFailArgumentCaptor = ArgumentCaptor.forClass(StudentFail.class);

    // when
    studentScoreService.saveScore(
        expectStudentScore.getExam(),
        expectStudentScore.getStudentName(),
        expectStudentScore.getKorScore(),
        expectStudentScore.getEnglishScore(),
        expectStudentScore.getMathScore()
    );

    // then
    Mockito.verify(studentScoreRepository, Mockito.times(1)).save(studentScoreArgumentCaptor.capture());

    StudentScore capturedStudentScore = studentScoreArgumentCaptor.getValue();

    Assertions.assertEquals(expectStudentScore.getStudentName(), capturedStudentScore.getStudentName());
    Assertions.assertEquals(expectStudentScore.getExam(), capturedStudentScore.getExam());
    Assertions.assertEquals(expectStudentScore.getKorScore(), capturedStudentScore.getKorScore());
    Assertions.assertEquals(expectStudentScore.getEnglishScore(), capturedStudentScore.getEnglishScore());
    Assertions.assertEquals(expectStudentScore.getMathScore(), capturedStudentScore.getMathScore());

    Mockito.verify(studentPassRepository, Mockito.times(0)).save(Mockito.any());

    Mockito.verify(studentFailRepository, Mockito.times(1)).save(studentFailArgumentCaptor.capture());

    StudentFail capturedStudentFail = studentFailArgumentCaptor.getValue();

    Assertions.assertEquals(expectStudentFail.getStudentName(), capturedStudentFail.getStudentName());
    Assertions.assertEquals(expectStudentFail.getExam(), capturedStudentFail.getExam());
    Assertions.assertEquals(expectStudentFail.getAvgScore(), capturedStudentFail.getAvgScore());
}
```

### ✅ Given 절에서 유용한 패턴

#### 1. Test Data Builder 패턴

1. 객체 생성과 초기화를 담당하는 별도의 빌더 클래스를 생성
2. 빌더 클래스는 필요한 속성을 설정하는 메소드를 제공
3. 메소드 체인을 사용하여 속성을 설정할 수 있으므로, 가독성이 좋아짐
4. 빌더 클래스의 **`build()`** 메소드를 호출하여 실제 테스트에 사용할 객체를 생성

Builder 사용 예시

```
public class StudentScoreTestDataBuilder {

    public static StudentScore.StudentScoreBuilder passed() {
        return StudentScore.builder()
            .exam("defaultExam")
            .studentName("defaultName")
            .korScore(80)
            .englishScore(100)
            .mathScore(90);
    }
    
    public static StudentScore.StudentScoreBuilder failed() {
        return StudentScore.builder()
            .exam("defaultExam")
            .studentName("defaultName")
            .korScore(50)
            .englishScore(40)
            .mathScore(30);
    }
}
```

```
@Test
@DisplayName("성적 저장 검증 로직 / 60점 이상인 경우")
void saveScoreMockTest() {
    // given : 평균 점수가 60점 이상인 경우
    StudentScore expectStudentScore = StudentScoreTestDataBuilder.passed().build();
    StudentPass expectStudentPass = StudentPassFixture.create(expectStudentScore);

    ArgumentCaptor<StudentScore> studentScoreArgumentCaptor = ArgumentCaptor.forClass(StudentScore.class);
    ArgumentCaptor<StudentPass> studentPassArgumentCaptor = ArgumentCaptor.forClass(StudentPass.class);

    // when
    studentScoreService.saveScore(
        expectStudentScore.getExam(),
        expectStudentScore.getStudentName(),
        expectStudentScore.getKorScore(),
        expectStudentScore.getEnglishScore(),
        expectStudentScore.getMathScore()
    );

    // then
    Mockito.verify(studentScoreRepository, Mockito.times(1)).save(studentScoreArgumentCaptor.capture());

    StudentScore capturedStudentScore = studentScoreArgumentCaptor.getValue();

    Assertions.assertEquals(expectStudentScore.getStudentName(), capturedStudentScore.getStudentName());
    Assertions.assertEquals(expectStudentScore.getExam(), capturedStudentScore.getExam());
    Assertions.assertEquals(expectStudentScore.getKorScore(), capturedStudentScore.getKorScore());
    Assertions.assertEquals(expectStudentScore.getEnglishScore(), capturedStudentScore.getEnglishScore());
    Assertions.assertEquals(expectStudentScore.getMathScore(), capturedStudentScore.getMathScore());

    Mockito.verify(studentPassRepository, Mockito.times(1)).save(studentPassArgumentCaptor.capture());

    StudentPass capturedStudentPass = studentPassArgumentCaptor.getValue();

    Assertions.assertEquals(expectStudentPass.getStudentName(), capturedStudentPass.getStudentName());
    Assertions.assertEquals(expectStudentPass.getExam(), capturedStudentPass.getExam());
    Assertions.assertEquals(expectStudentPass.getAvgScore(), capturedStudentPass.getAvgScore());

    Mockito.verify(studentFailRepository, Mockito.times(0)).save(Mockito.any());
}
```

#### 2. Object Mother 패턴

1. 여러 테스트에서 사용되는 테스트 데이터를 관리하는 클래스를 생성
2. 해당 클래스에는 다양한 테스트 데이터를 생성하고 초기화하는 메소드들이 포함됨
3. 각 테스트 메소드에서 필요한 테스트 데이터를 Object Mother 클래스의 메소드를 호출하여 가져옴

```
public class User {
    private String name;
    private int age;
    // ... (생략)
}

public class UserMother {
    public static User createValidUser() {
        User user = new User();
        user.setName("jyujyu");
        user.setAge(20);
        // ... (다른 필드 설정)
        return user;
    }

    public static User createInvalidUser() {
        User user = new User();
        // 잘못된 데이터로 설정
        return user;
    }
}
```

#### 3. Fixture Object 패턴

1. 테스트 데이터를 생성하고 초기화하는 작업을 담당하는 Fixture 클래스를 만듦
2. Fixture 클래스는 테스트에 필요한 객체와 데이터를 논리적으로 그룹화
3. 각 테스트 메소드에서 필요한 Fixture 객체를 생성하여 사용

Fixture 사용 예시

```
public class StudentScoreFixture {

    public static StudentScore passed() {
        return StudentScore.builder()
            .exam("defaultExam")
            .studentName("defaultName")
            .korScore(90)
            .englishScore(80)
            .mathScore(100)
            .build();
    }
    
    public static StudentScore failed() {
        return StudentScore.builder()
            .exam("defaultExam")
            .studentName("defaultName")
            .korScore(40)
            .englishScore(60)
            .mathScore(50)
            .build();
    }
}
```

```
public class StudentPassFixture {

    public static StudentPass create(StudentScore studentScore) {
        var calculator = new MyCalculator();
        return StudentPass.builder()
            .exam(studentScore.getExam())
            .studentName(studentScore.getStudentName())
            .avgScore(calculator
                .add(studentScore.getKorScore().doubleValue())
                .add(studentScore.getEnglishScore().doubleValue())
                .add(studentScore.getMathScore().doubleValue())
                .divide(3.0)
                .getResult()
            )
            .build();
    }
    
    public static StudentPass create(String studentName, String exam) {
        return StudentPass.builder()
            .exam(exam)
            .studentName(studentName)
            .avgScore(80.0)
            .build();
    }
}
```

```
@Test
@DisplayName("합격자 명단 가져오기 검증")
void getPassStudentsListTest() {
    // given
    String givenTestExam = "testexam";
    StudentPass expectStudent1 = StudentPassFixture.create("jyujyu", givenTestExam);
    StudentPass expectStudent2 = StudentPassFixture.create("testName", givenTestExam);
    StudentPass notExpectStudent3 = StudentPassFixture.create("anotherStudent", "anotherExam");

    Mockito.when(studentPassRepository.findAll()).thenReturn(List.of(
        expectStudent1,
        expectStudent2,
        notExpectStudent3
    ));

    // when
    var expectResponses = Stream.of(expectStudent1, expectStudent2)
        .map(pass -> new ExamPassStudentResponse(pass.getStudentName(), pass.getAvgScore()))
        .toList();
    List<ExamPassStudentResponse> responses = studentScoreService.getPassStudentsList(givenTestExam);

    Assertions.assertIterableEquals(expectResponses, responses);
}
```

### ✅ Reference

- [스프링 테스트 By 쥬쥬](https://www.inflearn.com/course/%EC%A5%AC%EC%A5%AC%EC%99%80-%ED%95%98%EB%A3%A8%EB%A7%8C%EC%97%90-%EB%81%9D%EB%82%B4%EB%8A%94-%EC%8A%A4%ED%94%84%EB%A7%81%ED%85%8C%EC%8A%A4%ED%8A%B8)