package com.jyujyu.dayonetest;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class) // 모든 테스트 메서드에 규칙 적용
class JUnitPracticeTest {

	@Test
	void assert_equals_test() {
		String expect = "Something";
		String actual = "Something";

		Assertions.assertEquals(expect, actual);
	}

	@Test
	@DisplayName("Assert Not Equals 메서드 테스트")
	void assertNotEqualsTest() {
		String expect = "Something";
		String actual = "Hello";

		Assertions.assertNotEquals(expect, actual);
	}

	@Test
	@DisplayName("Assert True 메서드 테스트")
	void assertTrueTest() {
		Integer a = 10;
		Integer b = 10;

		Assertions.assertTrue(a.equals(b));
	}

	@Test
	@DisplayName("Assert False 메서드 테스트")
	void assertFalseTest() {
		Integer a = 10;
		Integer b = 20;

		Assertions.assertFalse(a.equals(b));
	}

	@Test
	@DisplayName("Assert Throws 메서드 테스트")
	void assertThrowsTest() {
		Assertions.assertThrows(RuntimeException.class, () -> {
			throw new RuntimeException("임의로 발생시킨 에러");
		});
	}

	@Test
	@DisplayName("Assert Not Null 메서드 테스트")
	void assertNotNullTest() {
		String value = "Hello";
		Assertions.assertNotNull(value);
	}

	@Test
	@DisplayName("Assert Null 메서드 테스트")
	void assertNullTest() {
		String value = null;
		Assertions.assertNull(value);
	}

	@Test
	@DisplayName("Assert Iterable 메서드 테스트")
	void assertIterableEqualsTest() {
		List<Integer> list1 = List.of(1, 2);
		List<Integer> list2 = List.of(1, 2);

		Assertions.assertIterableEquals(list1, list2);
	}

	@Test
	@DisplayName("Assert All 메서드 테스트")
	void assertAllTest(){
		String expect = "Something";
		String actual = "Something";

		Assertions.assertEquals(expect, actual);

		List<Integer> list1 = List.of(1, 2);
		List<Integer> list2 = List.of(1, 2);

		Assertions.assertIterableEquals(list1, list2);

		Assertions.assertAll("Assert All", List.of(
			() -> { Assertions.assertEquals(expect, actual); },
			() -> { Assertions.assertIterableEquals(list1, list2); }
		));
	}
}
