package com.jyujyu.dayonetest.model;

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
