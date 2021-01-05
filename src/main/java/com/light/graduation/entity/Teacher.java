package com.light.graduation.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Light
 */
@Data
public class Teacher implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 教师工号
	 */
	private String teacherNumber;
	/**
	 * 教师登录密码
	 */
	private String teacherPassword;
	/**
	 * 教师姓名
	 */
	private String teacherName;
	/**
	 * 教师性别
	 */
	private String teacherSex;
	/**
	 * 教师联系方式
	 */
	private String teacherContact;
	/**
	 * 教师邮箱
	 */
	private String teacherEmail;
}