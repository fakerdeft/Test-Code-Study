package com.jyujyu.dayonetest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jyujyu.dayonetest.model.StudentPass;

public interface StudentPassRepository extends JpaRepository<StudentPass, Long> {

}
