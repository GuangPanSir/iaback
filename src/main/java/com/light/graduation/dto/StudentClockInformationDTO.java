package com.light.graduation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author: Light
 * @Date 2020/3/3 14:53
 */
@Data
public class StudentClockInformationDTO {
	private String clockInProject;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date clockTime;
	private String clockState;
	private String errorReason;
	private String teacherName;
}
