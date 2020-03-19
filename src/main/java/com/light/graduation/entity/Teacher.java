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
	private String teachernumber;
	/**
	 * 教师登录密码
	 */
	private String teacherpassword;
	/**
	 * 教师姓名
	 */
	private String teachername;
	/**
	 * 教师性别
	 */
	private String teachersex;
	/**
	 * 教师联系方式
	 */
	private String teachercontact;
	/**
	 * 教师邮箱
	 */
	private String teacheremail;
}