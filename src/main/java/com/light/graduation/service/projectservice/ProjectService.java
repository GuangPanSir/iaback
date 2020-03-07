package com.light.graduation.service.projectservice;

import com.light.graduation.pojo.CheckStudentClockSelectPojo;

import java.util.List;

/**
 * @Author: Light
 * @Date 2020/3/4 17:31
 */
public interface ProjectService {
	List< String > getAllMajors ( );
	
	List< String > getAllProjects ( String major);
	
	List< String > getAllTeachers ( String project);
	
	Boolean checkStudentClockSelect ( CheckStudentClockSelectPojo checkStudentClockSelectPojo );
}
