package com.light.graduation.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * @author Light
 */
@Data
public class NcistPerson implements Serializable {
    /**
     * 学生或老师信息工号
     */
    private String personNumber;

    /**
     * 身份（学生或教师）
     */
    private String identity;

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
     * 人员性别
     */
    private String personSex;

    /**
     * 人员姓名
     */
    private String personName;

    private static final long serialVersionUID = 1L;
}