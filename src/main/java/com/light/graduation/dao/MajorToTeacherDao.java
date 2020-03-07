package com.light.graduation.dao;

import com.light.graduation.entity.MajorToTeacher;

import java.util.List;

public interface MajorToTeacherDao {
    int insert(MajorToTeacher record);

    int insertSelective(MajorToTeacher record);
	
	List< String > getAllMajors ( );
	
	List< String > getAllProjects ( String major );
	
	List< String > getAllTeachers ( String project );
	
}