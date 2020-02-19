package com.light.graduation.entity;

import java.io.Serializable;
import lombok.Data;
import org.springframework.stereotype.Repository;

/**
 * @author Light
 */
@Data
public class Student implements Serializable {
    /**
     * 学生学号
     */
    private String studentNumber;

    /**
     * 登录密码（默认为学号）
     */
    private String studentPassword;

    /**
     * 性别
     */
    private String studentSex;

    /**
     * 学生的人脸照片（以base64格式存储）
     */
    private String studentFaceImage;

    /**
     * 学生姓名
     */
    private String studentName;

    /**
     * 该学生迟到次数
     */
    private Integer overdueTimes;

    /**
     * 该学生缺席次数
     */
    private Integer absentTimes;

    private static final long serialVersionUID = 1L;
}