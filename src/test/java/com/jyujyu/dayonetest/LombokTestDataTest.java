package com.jyujyu.dayonetest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LombokTestDataTest {

	@Test
	void testDataTest() {
		TestData testData = new TestData();
		testData.setName("jyujyu");

		Assertions.assertEquals("jyujyu", testData.getName());
	}
}
