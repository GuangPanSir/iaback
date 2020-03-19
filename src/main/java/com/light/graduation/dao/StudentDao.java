package com.light.graduation.dao;

import com.light.graduation.entity.Student;

import java.util.List;

/**
 * @author Light
 */
public interface StudentDao {
	/**
	 * 删除给定的学生额相关信息
	 *
	 * @param studentNumber 学生学号
	 * @return 数据库的影响条数
	 */
	int deleteByPrimaryKey ( String studentNumber );
	
	/**
	 * 添加学生信息
	 *
	 * @param record 学生信息
	 * @return 数据库的影响条数
	 */
	int insert ( Student record );
	
	/**
	 * 选择性地添加学生信息
	 *
	 * @param record 学生信息
	 * @return 数据库的影响条数
	 */
	int insertSelective ( Student record );
	
	/**
	 * 根据学生学号查询该生的信息
	 *
	 * @param studentNumber 学生学号
	 * @return 学生信息
	 */
	Student selectByPrimaryKey ( String studentNumber );
	
	/**
	 * 选择性地更新学生信息
	 *
	 * @param record 学生信息
	 * @return 数据库的影响条数
	 */
	int updateByPrimaryKeySelective ( Student record );
	
	/**
	 * 更新学生信息
	 *
	 * @param record 学生信息
	 * @return 数据库的影响条数
	 */
	int updateByPrimaryKey ( Student record );
	
	/**
	 * 获取所有学生的信息
	 *
	 * @return 学生信息
	 */
	List< Student > selectAllStudents ( );
	
	/**
	 * 根据学生学号获取该生的人脸信息
	 *
	 * @param studentNumber 学生学号
	 * @return 学生的人脸信息
	 */
	String getStudentFaceImage ( String studentNumber );
	
	/**
	 * 根据学生学号获取该生的密码
	 *
	 * @param studentNumber 学生学号
	 * @return 学生的登录密码
	 */
	String getStudentPassword ( String studentNumber );
	
	/**
	 * 根据学生学号获取该生的专业
	 *
	 * @param studentNumber 学生学号
	 * @return 学生专业
	 */
	String getStudentMajor ( String studentNumber );
	
	/**
	 * 根据学生学号获取学生姓名
	 *
	 * @param studentNumber 学生学号
	 * @return 学生姓名
	 */
	String getStudentNameByStudentNumber ( String studentNumber );
}