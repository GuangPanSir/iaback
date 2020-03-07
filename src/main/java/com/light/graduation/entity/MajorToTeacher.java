package com.light.graduation.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * majortoteacher
 * @author 
 */
@Data
public class MajorToTeacher implements Serializable {
    /**
     * 教师工号
     */
    private String teacherNumber;

    /**
     * 教师教授的专业
     */
    private String teacherMajor;

    /**
     * 教师教授的课程
     */
    private String teacherProject;

    private static final long serialVersionUID = 1L;
}