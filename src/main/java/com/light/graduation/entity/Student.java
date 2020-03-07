package com.light.graduation.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * studentinfo
 * @author 
 */
@Data
public class Student implements Serializable {
    /**
     * 学生学号
     */
    private String studentNumber;

    /**
     * 学生姓名
     */
    private String studentName;

    /**
     * 登录密码（默认为学号）
     */
    private String studentPassword;

    /**
     * 学生学院
     */
    private String studentCollege;

    /**
     * 学生专业
     */
    private String studentMajor;

    /**
     * 学生班级
     */
    private String studentClass;

    /**
     * 性别
     */
    private String studentSex;

    /**
     * 学生的人脸照片（以base64格式存储）
     */
    private String studentFaceImage;

    /**
     * 该学生迟到次数
     */
    private Integer overdueTimes;

    /**
     * 该学生缺席次数
     */
    private Integer absentTimes;

    /**
     * 学生联系方式
     */
    private String studentContact;

    /**
     * 学生邮箱
     */
    private String studentEMail;

    private static final long serialVersionUID = 1L;
}