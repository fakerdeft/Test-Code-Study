package com.jyujyu.dayonetest.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "student_fail")
public class StudentFail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "student_fail_id")
	private Long id;

	@Column(name = "exam")
	private String exam;

	@Column(name="student_name")
	private String studentName;

	@Column(name = "avg_score")
	private double avgScore;
}
