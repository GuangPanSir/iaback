package com.light.graduation.dto;

import lombok.Data;

/**
 * @Author: Light
 * @Date 2020/4/28 10:40
 */
@Data
public class StudentClockScore {
	private String studentNumber;
	private String studentName;
	private Integer clockNormal;
	private Integer clockAbnormal;
	private Integer clockOverdue;
	private Integer clockVacate;
	private Integer clockAbsent;
	private Integer clockScore;
}
