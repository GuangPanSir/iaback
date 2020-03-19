package com.light.graduation.dao;

import com.light.graduation.entity.MajorToTeacher;

import java.util.List;

/**
 * @author Light
 */
public interface MajorToTeacherDao {
	/**
	 * 添加教师所教授的专业及课程
	 *
	 * @param record 教师教授的专业及课程
	 * @return 数据库的影响条数
	 */
	int insert ( MajorToTeacher record );
	
	/**
	 * 选择性地添加教师所教授的专业及课程
	 *
	 * @param record 教师教授的专业及课程
	 * @return 数据库的影响条数
	 */
	int insertSelective ( MajorToTeacher record );
	
	/**
	 * 查询全校的所有专业
	 *
	 * @return 专业集合
	 */
	List< String > getAllMajors ( );
	
	/**
	 * 查询所有该专业的所有课程
	 *
	 * @param major 专业
	 * @return 课程
	 */
	List< String > getAllProjects ( String major );
	
	/**
	 * 查询所有教授此门课程的教师
	 *
	 * @param project 课程
	 * @return 教师
	 */
	List< String > getAllTeachers ( String project );
	
}