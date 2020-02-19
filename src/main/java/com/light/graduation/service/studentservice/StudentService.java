package com.light.graduation.service.studentservice;

import com.light.graduation.entity.Student;

import java.util.List;

/**
 * @Author: Light
 * @Date 2020/2/19 13:56
 */
public interface StudentService {
	Student selectByPrimaryKey( String studentNumber);
	
	List< Student > selectAllStudents ( );
	
}
