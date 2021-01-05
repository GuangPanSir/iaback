package com.light.graduation.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * attendancestatistics
 * @author 
 */
@Data
public class AttendanceStatistics implements Serializable {
    /**
     * 学生学号
     */
    private String studentNumber;

    /**
     * 学生院系
     */
    private String studentCollege;

    /**
     * 学生专业
     */
    private String studentMajor;

    /**
     * 考勤课程
     */
    private String clockProject;

    /**
     * 考勤教师
     */
    private String clockTeacher;

    /**
     * 正常签到次数
     */
    private Integer clockNormal;

    /**
     * 异常签到次数
     */
    private Integer clockAbnormal;

    /**
     * 迟到次数
     */
    private Integer clockOverdue;

    /**
     * 请假次数
     */
    private Integer clockVacate;

    /**
     * 缺勤次数
     */
    private Integer clockAbsent;

    /**
     * 本学期考勤总次数
     */
    private Integer clockTotal;

    /**
     * 考勤分数
     */
    private Integer clockScore;

    private static final long serialVersionUID = 1L;
}