package com.light.graduation.service.studentservice.impl;

import com.light.graduation.dao.StudentClockInDao;
import com.light.graduation.dao.StudentDao;
import com.light.graduation.dto.CheckLoginDTO;
import com.light.graduation.dto.StudentClockInformationDTO;
import com.light.graduation.entity.Student;
import com.light.graduation.service.studentservice.StudentService;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Light
 * @Date 2020/2/19 14:20
 */
@Service( value = "studentServiceImpl" )
public class StudentServiceImpl implements StudentService {
	private final StudentClockInDao studentclockinDao;
	
	private final StudentDao studentDao;
	
	@Contract( pure = true )
	public StudentServiceImpl ( StudentDao studentDao , StudentClockInDao studentclockinDao ) {
		this.studentDao = studentDao;
		this.studentclockinDao = studentclockinDao;
	}
	
	@Override
	public Student selectByPrimaryKey ( String studentNumber ) {
		return this.studentDao.selectByPrimaryKey ( studentNumber );
	}
	
	@Override
	public List< Student > selectAllStudents ( ) {
		return this.studentDao.selectAllStudents ( );
	}
	
	@Override
	public boolean checkStudentLogin ( @NotNull CheckLoginDTO loginUser ) {
		String studentPassword = this.studentDao.getStudentPassword ( loginUser.getUserName ( ) );
		return loginUser.getUserPassword ( ).equals ( studentPassword );
	}
	
	@Override
	public List< StudentClockInformationDTO > getStudentClockInformation ( String studentNumber ) {
		return this.studentclockinDao.getStudentClockInformation ( studentNumber );
	}
	
	@Override
	public String getStudentMajor ( String studentNumber ) {
		return this.studentDao.getStudentMajor ( studentNumber );
	}
}
