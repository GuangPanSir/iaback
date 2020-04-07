package com.light.graduation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author: Light
 * @Date 2020/3/30 14:56
 */
@Data
public class CurrentTimeInformation {
	private String studentNumber;
	private String studentName;
	
	@JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8" )
	private Date clockTime;
	
	private String clockState;
	
	private String errorReason;
}
