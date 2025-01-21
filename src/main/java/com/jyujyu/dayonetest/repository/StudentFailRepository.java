package com.jyujyu.dayonetest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jyujyu.dayonetest.model.StudentFail;

public interface StudentFailRepository extends JpaRepository<StudentFail, Long> {

}
