package com.light.graduation.dao;

import com.light.graduation.entity.Teacher;

public interface TeacherDao {
	int deleteByPrimaryKey ( String teachernumber );
	
	int insert ( Teacher record );
	
	int insertSelective ( Teacher record );
	
	Teacher selectByPrimaryKey ( String teachernumber );
	
	int updateByPrimaryKeySelective ( Teacher record );
	
	int updateByPrimaryKey ( Teacher record );
	
	String getTeacherPassword ( String teacherNumber );
	
	String selectTeacherNumberByTeacherName ( String teacherName );
	
}