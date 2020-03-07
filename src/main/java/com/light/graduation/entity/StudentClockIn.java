package com.light.graduation.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * studentclockin
 * @author 
 */
@Data
public class StudentClockIn implements Serializable {
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
打卡时间不在老师设置的时间内
打卡地理位置超出范围
非本人操作（即人脸不匹配）
     */
    private String errorReason;

    /**
     * 请假凭证（上传假条图片）
     */
    private String certification;

    private static final long serialVersionUID = 1L;
}