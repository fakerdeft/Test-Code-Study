package com.jyujyu.dayonetest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jyujyu.dayonetest.model.StudentScore;

public interface StudentScoreRepository extends JpaRepository<StudentScore, Long> {

}
