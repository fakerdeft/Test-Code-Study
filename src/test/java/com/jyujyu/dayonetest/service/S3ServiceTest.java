package com.jyujyu.dayonetest.service;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.testcontainers.shaded.org.apache.commons.io.FileUtils;

import com.jyujyu.dayonetest.IntegrationTest;

class S3ServiceTest extends IntegrationTest {

	@Autowired
	private S3Service s3Service;

	@Test
	void s3PutAndGetTest() throws IOException {
		// given
		var bucket = "test-bucket";
		var key = "sampleObject.xt";
		var sampleFile = new ClassPathResource("static/sample.txt").getFile();

		// when
		s3Service.putFile(bucket, key, sampleFile);

		// then
		var resultfile = s3Service.getFile(bucket, key);

		List<String> sampleFileLines = FileUtils.readLines(sampleFile);
		List<String> resultFileLines = FileUtils.readLines(resultfile);

		Assertions.assertIterableEquals(sampleFileLines, resultFileLines);
	}
}
