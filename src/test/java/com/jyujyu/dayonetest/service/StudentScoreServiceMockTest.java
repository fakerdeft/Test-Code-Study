package com.jyujyu.dayonetest.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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
}
