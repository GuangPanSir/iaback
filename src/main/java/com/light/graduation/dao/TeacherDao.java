package com.light.graduation.dao;

import com.light.graduation.entity.Teacher;

/**
 * @author Light
 */
public interface TeacherDao {
	/**
	 * 删除指定教师的相关信息
	 *
	 * @param teachernumber 教师编号
	 * @return 数据库的影响条数
	 */
	int deleteByPrimaryKey ( String teachernumber );
	
	/**
	 * 添加教师信息
	 *
	 * @param record 教师信息
	 * @return 数据库的影响条数
	 */
	int insert ( Teacher record );
	
	/**
	 * 选择性地添加教师信息
	 *
	 * @param record 教师信息
	 * @return 数据库的影响条数
	 */
	int insertSelective ( Teacher record );
	
	/**
	 * 根据教师编号是查询教师信息
	 *
	 * @param teachernumber 教师编号
	 * @return 教师信息
	 */
	Teacher selectByPrimaryKey ( String teachernumber );
	
	/**
	 * 选选择性地更新教师信息
	 *
	 * @param record 教师信息
	 * @return 数据库的设计
	 */
	int updateByPrimaryKeySelective ( Teacher record );
	
	/**
	 * 更新教师信息
	 *
	 * @param record 教师信息
	 * @return 数据库的影响条数
	 */
	int updateByPrimaryKey ( Teacher record );
	
	/**
	 * 根据教师编号获取教师登录密码
	 *
	 * @param teacherNumber 教师编号
	 * @return 教师登录密码
	 */
	String getTeacherPassword ( String teacherNumber );
	
	/**
	 * 根据教师姓名获取教师编号
	 *
	 * @param teacherName 教师姓名
	 * @return 教师编号
	 */
	String selectTeacherNumberByTeacherName ( String teacherName );
	
	/**
	 * 根据教师姓名获取教师姓名
	 *
	 * @param teacherNumber 教师编号
	 * @return 教师姓名
	 */
	String queryTeacherNameByTeacherNumber ( String teacherNumber );
}