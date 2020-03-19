package com.light.graduation.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Light
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings( "unused" )
public class CheckStudentClockSelectPojo {
	private String major;
	private String project;
	private String teacher;
}
