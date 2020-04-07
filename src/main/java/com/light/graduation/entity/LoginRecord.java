package com.light.graduation.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Light
 */
@Data
public class LoginRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 教师工号
	 */
	private String teacherNumber;
	/**
	 * 打卡班级
	 */
	private String clockInMajor;
	/**
	 * 打卡开始时间
	 */
	private Date clockInStartTime;
	/**
	 * 打卡截止时间
	 */
	private Date clockInEndTime;
	/**
	 * 最晚打卡截止时间（在截止时间与最晚截止时间打卡的视为迟到，最晚截止时间之后的视为缺席）
	 */
	private Date clockInAbsentTime;
	/**
	 * 允许打卡地理位置的差值
	 */
	private Double locationDifference;
	/**
	 * 正常打卡人数
	 */
	private Integer clockInNormal;
	/**
	 * 异常签到人数
	 */
	private Integer clockInAbnormal;
	/**
	 * 缺席人数
	 */
	private Integer clockInAbsent;
	/**
	 * 迟到打卡人数
	 */
	private Integer clockInOverdue;
	/**
	 * 打卡课程
	 */
	private String clockInProject;
	/**
	 * 教师经度
	 */
	private double clockInLng;
	/**
	 * 教师纬度
	 */
	private double clockInLat;
	/**
	 * 定位精度范围
	 */
	private int clockInAccuracy;
	/**
	 * 教师地理位置
	 */
	private String clockInAddress;
}