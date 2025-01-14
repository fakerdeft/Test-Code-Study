package com.jyujyu.dayonetest;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class JUnitPracticeTest {

	@Test
	void assertEqualsTest() {
		String expect = "Something";
		String actual = "Something";

		Assertions.assertEquals(expect, actual);
	}

	@Test
	void assertNotEqualsTest() {
		String expect = "Something";
		String actual = "Hello";

		Assertions.assertNotEquals(expect, actual);
	}

	@Test
	void assertTrueTest() {
		Integer a = 10;
		Integer b = 10;

		Assertions.assertTrue(a.equals(b));
	}

	@Test
	void assertFalseTest() {
		Integer a = 10;
		Integer b = 20;

		Assertions.assertFalse(a.equals(b));
	}

	@Test
	void assertThrowsTest() {
		Assertions.assertThrows(RuntimeException.class, () -> {
			throw new RuntimeException("임의로 발생시킨 에러");
		});
	}

	@Test
	void assertNotNullTest() {
		String value = "Hello";
		Assertions.assertNotNull(value);
	}

	@Test
	void assertNullTest() {
		String value = null;
		Assertions.assertNull(value);
	}

	@Test
	void assertIterableEqualsTest() {
		List<Integer> list1 = List.of(1, 2);
		List<Integer> list2 = List.of(1, 2);

		Assertions.assertIterableEquals(list1, list2);
	}

	@Test
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
