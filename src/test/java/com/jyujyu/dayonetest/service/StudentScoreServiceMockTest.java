package com.jyujyu.dayonetest.service;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.jyujyu.dayonetest.controller.response.ExamFailStudentResponse;
import com.jyujyu.dayonetest.controller.response.ExamPassStudentResponse;
import com.jyujyu.dayonetest.model.StudentFail;
import com.jyujyu.dayonetest.model.StudentPass;
import com.jyujyu.dayonetest.repository.StudentFailRepository;
import com.jyujyu.dayonetest.repository.StudentPassRepository;
import com.jyujyu.dayonetest.repository.StudentScoreRepository;

class StudentScoreServiceMockTest {

	@Test
	@DisplayName("첫 번째 Mock 테스트")
	void firstSaveScoreMockTest() {
		// given
		StudentScoreService studentScoreService = new StudentScoreService(
			Mockito.mock(StudentScoreRepository.class),
			Mockito.mock(StudentPassRepository.class),
			Mockito.mock(StudentFailRepository.class)
		);

		String givenStudentName = "jyujyu";
		String givenExam = "testExam";
		Integer givenKorScore = 80;
		Integer givenEnglishScore = 100;
		Integer givenMathScore = 60;

		// when
		studentScoreService.saveScore(
			givenExam,
			givenStudentName,
			givenKorScore,
			givenEnglishScore,
			givenMathScore
		);
	}

	@Test
	@DisplayName("성적 저장 검증 로직 / 60점 이상인 경우")
	void saveScoreMockTest() {
		// given : 평균 점수가 60점 이상인 경우
		StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
		StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
		StudentFailRepository studentFailRepository = Mockito.mock(StudentFailRepository.class);

		StudentScoreService studentScoreService = new StudentScoreService(
			studentScoreRepository,
			studentPassRepository,
			studentFailRepository
		);

		String givenStudentName = "jyujyu";
		String givenExam = "testExam";
		Integer givenKorScore = 80;
		Integer givenEnglishScore = 100;
		Integer givenMathScore = 60;

		// when
		studentScoreService.saveScore(
			givenExam,
			givenStudentName,
			givenKorScore,
			givenEnglishScore,
			givenMathScore
		);

		// then
		Mockito.verify(studentScoreRepository, Mockito.times(1)).save(Mockito.any());
		Mockito.verify(studentPassRepository, Mockito.times(1)).save(Mockito.any());
		Mockito.verify(studentFailRepository, Mockito.times(0)).save(Mockito.any());
	}

	@Test
	@DisplayName("성적 저장 로직 검증 / 60점 미만인 경우")
	void saveScoreMockTest2() {
		// given : 평균 점수가 60점 미만인 경우
		StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
		StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
		StudentFailRepository studentFailRepository = Mockito.mock(StudentFailRepository.class);

		StudentScoreService studentScoreService = new StudentScoreService(
			studentScoreRepository,
			studentPassRepository,
			studentFailRepository
		);

		String givenStudentName = "jyujyu";
		String givenExam = "testExam";
		Integer givenKorScore = 40;
		Integer givenEnglishScore = 40;
		Integer givenMathScore = 60;

		// when
		studentScoreService.saveScore(
			givenExam,
			givenStudentName,
			givenKorScore,
			givenEnglishScore,
			givenMathScore
		);

		// then
		Mockito.verify(studentScoreRepository, Mockito.times(1)).save(Mockito.any());
		Mockito.verify(studentPassRepository, Mockito.times(0)).save(Mockito.any());
		Mockito.verify(studentFailRepository, Mockito.times(1)).save(Mockito.any());
	}

	@Test
	@DisplayName("합격자 명단 가져오기 검증")
	void getPassStudentsListTest() {
		// given
		StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
		StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
		StudentFailRepository studentFailRepository = Mockito.mock(StudentFailRepository.class);

		StudentPass expectStudent1 = StudentPass.builder()
			.id(1L)
			.studentName("jyujyu")
			.exam("testexam")
			.avgScore(70.0)
			.build();
		StudentPass expectStudent2 = StudentPass.builder()
			.id(2L)
			.studentName("test")
			.exam("testexam")
			.avgScore(80.0)
			.build();
		StudentPass notExpectStudent3 = StudentPass.builder()
			.id(3L)
			.studentName("iamnot")
			.exam("secondexam")
			.avgScore(90.0)
			.build();

		Mockito.when(studentPassRepository.findAll()).thenReturn(List.of(
			expectStudent1,
			expectStudent2,
			notExpectStudent3
		));

		StudentScoreService studentScoreService = new StudentScoreService(
			studentScoreRepository,
			studentPassRepository,
			studentFailRepository
		);

		String givenTestExam = "testexam";

		// when
		var expectResponses = Stream.of(expectStudent1, expectStudent2)
			.map(pass -> new ExamPassStudentResponse(pass.getStudentName(), pass.getAvgScore()))
			.toList();
		List<ExamPassStudentResponse> responses = studentScoreService.getPassStudentsList(givenTestExam);

		Assertions.assertIterableEquals(expectResponses, responses);
	}

	@Test
	@DisplayName("불합격자 명단 가져오기 검증")
	void getFailStudentsListTest() {
		// given
		StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
		StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
		StudentFailRepository studentFailRepository = Mockito.mock(StudentFailRepository.class);
		String givenTestExam = "testexam";

		StudentFail expectStudent1 = StudentFail.builder()
			.id(1L)
			.studentName("jyujyu")
			.exam(givenTestExam)
			.avgScore(50.0)
			.build();
		StudentFail expectStudent2 = StudentFail.builder()
			.id(2L)
			.studentName("test")
			.exam(givenTestExam)
			.avgScore(45.0)
			.build();
		StudentFail notExpectStudent3 = StudentFail.builder()
			.id(3L)
			.studentName("iamnot")
			.exam("secondexam")
			.avgScore(35.0)
			.build();

		Mockito.when(studentFailRepository.findAll()).thenReturn(List.of(
			expectStudent1,
			expectStudent2,
			notExpectStudent3
		));

		StudentScoreService studentScoreService = new StudentScoreService(
			studentScoreRepository,
			studentPassRepository,
			studentFailRepository
		);

		// when
		var expectFailList = Stream.of(expectStudent1, expectStudent2)
			.map(fail -> new ExamFailStudentResponse(fail.getStudentName(), fail.getAvgScore()))
			.toList();
		List<ExamFailStudentResponse> responses = studentScoreService.getFailStudentsList(givenTestExam);

		// then
		Assertions.assertIterableEquals(expectFailList, responses);
	}
}
