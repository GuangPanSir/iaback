package com.light.graduation.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Light
 */
@Data
public class StudentClockIn implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 学生学号
	 */
	private String studentNumber;
	/**
	 * 打卡老师
	 */
	private String clockInTeacherNumber;
	/**
	 * 打卡课程
	 */
	private String clockInProject;
	/**
	 * 打卡时间
	 */
	@JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8" )
	private Date clockTime;
	/**
	 * 打卡的地理位置（以经纬度表示）
	 */
	private String clockLocation;
	/**
	 * 打卡状态（正常,失败）
	 */
	private String clockState;
	/**
	 * 异常原因（如果是异常则无）
	 * 打卡时间不在老师设置的时间内
	 * 打卡地理位置超出范围
	 * 非本人操作（即人脸不匹配）
	 */
	private String errorReason;
	/**
	 * 请假凭证（上传假条图片）
	 */
	private String certification;
	/**
	 * 学生经度
	 */
	private double clockLng;
	/**
	 * 学生纬度
	 */
	private double clockLat;
	/**
	 * 定位精度范围
	 */
	private int clockAccuracy;
	/**
	 * 学生地理位置
	 */
	private String clockAddress;
	
	/**
	 * 请假是否被审核
	 */
	private int isCheck;
	
	/**
	 * 请假是否被批准
	 */
	private int isPermit;
	
	/**
	 * 学生签到人脸
	 */
	private String clockInImg;
	
	/**
	 * 打卡时间
	 */
	@JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8" )
	private Date disposeTime;
}