package com.light.graduation.service.studentservice.impl;

import com.light.graduation.dao.StudentDao;
import com.light.graduation.entity.Student;
import com.light.graduation.service.studentservice.StudentService;
import org.jetbrains.annotations.Contract;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Light
 * @Date 2020/2/19 14:20
 */
@Service(value = "studentServiceImpl")
public class StudentServiceImpl implements StudentService {
	private final StudentDao studentDao;
	
	@Contract( pure = true )
	public StudentServiceImpl ( StudentDao studentDao ) {
		this.studentDao = studentDao;
	}
	
	@Override
	public Student selectByPrimaryKey ( String studentNumber ) {
		return this.studentDao.selectByPrimaryKey ( studentNumber );
	}
	
	@Override
	public List< Student > selectAllStudents ( ) {
		return this.studentDao.selectAllStudents ( );
	}
}
