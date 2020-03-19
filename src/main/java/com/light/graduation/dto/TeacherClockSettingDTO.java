package com.light.graduation.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author Light
 */
@Data
@SuppressWarnings( "unused" )
public class TeacherClockSettingDTO {
	
	private Date clockInAbsentTime;
	private Double locationDifference;
	private Date clockInEndTime;
	private String clockInMajor;
	private String clockInProject;
	private String teacherNumber;
}
