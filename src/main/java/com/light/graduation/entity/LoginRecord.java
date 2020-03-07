package com.light.graduation.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * loginrecord
 * @author 
 */
@Data
public class LoginRecord implements Serializable {
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

    private static final long serialVersionUID = 1L;
}