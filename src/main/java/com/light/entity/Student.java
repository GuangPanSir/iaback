package com.light.entity;

import lombok.Data;
import org.jetbrains.annotations.Contract;

/**
 * @Author: Light
 * @Date 2020/1/8 0:08
 */
@Data
public class Student {
	private String studentId;
	private String loginPassword;
	private String studentName;
	private String grade;
	private Integer laterDays;
	private Integer absenceDays;
	private String studentImage;
	
	/**
	 *
	 * @param studentId 用户学号
	 * @param grade 用户班级
	 * @param studentImage 用户人脸的BASE64的数据
	 *                     此构造函数用于匹配人脸
	 */
	@Contract( pure = true )
	public Student ( String studentId , String grade , String studentImage) {
		this.studentId = studentId;
		this.grade = grade;
		this.studentImage = studentImage;
	}
	
	/**
	 *
	 * @param studentId 用户学号
	 * @param grade 用户班级
	 * @param studentImage 用户人脸的BASE64的数据
	 * @param studentName 用户姓名
	 *                     此构造函数用于添加人脸
	 */
	@Contract( pure = true )
	public Student ( String studentId , String studentName , String grade , String studentImage ) {
		this.studentId = studentId;
		this.studentName = studentName;
		this.grade = grade;
		this.studentImage = studentImage;
	}
}
