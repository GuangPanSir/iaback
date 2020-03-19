package com.light.graduation.service.studentservice.impl;

import com.light.graduation.dao.QuoteDao;
import com.light.graduation.dao.StudentClockInDao;
import com.light.graduation.dao.StudentDao;
import com.light.graduation.dto.CheckLoginDTO;
import com.light.graduation.dto.StudentClockInformationDTO;
import com.light.graduation.entity.Student;
import com.light.graduation.entity.StudentClockIn;
import com.light.graduation.pojo.CheckStudentClockSelectPojo;
import com.light.graduation.service.projectservice.ProjectService;
import com.light.graduation.service.studentservice.StudentService;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Author: Light
 * @Date 2020/2/19 14:20
 */
@Service( value = "studentServiceImpl" )
public class StudentServiceImpl implements StudentService {
	private final StudentClockInDao studentclockinDao;
	
	private final StudentDao studentDao;
	
	private final QuoteDao quoteDao;
	
	private final ProjectService projectService;
	
	@Contract( pure = true )
	public StudentServiceImpl ( StudentDao studentDao , StudentClockInDao studentclockinDao , QuoteDao quoteDao , ProjectService projectService ) {
		this.studentDao = studentDao;
		this.studentclockinDao = studentclockinDao;
		this.quoteDao = quoteDao;
		this.projectService = projectService;
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
	
	@Override
	public String getQuote ( ) {
		return this.quoteDao.selectQuote ( );
	}
	
	@Override
	public void insertStudentClockIn ( StudentClockIn studentClockIn ) {
		this.studentclockinDao.insertSelective ( studentClockIn );
	}
	
	@Override
	public int checkStudentClock ( HttpSession session , String studentNumber ) {
		StudentClockIn middleStudentClockIn = this.studentclockinDao.getLastClockInformation ( studentNumber );
		if ( middleStudentClockIn == null ) {
			return 1;
		} else {
			long localTime = System.currentTimeMillis ( );
			long lastTime = middleStudentClockIn.getClockTime ( ).getTime ( );
			if ( localTime - lastTime <= 1000 * 60 * 10 ) {
				if ( "正常".equals ( middleStudentClockIn.getClockState ( ) ) ) {
					return 2;
				} else if ( this.projectService.checkStudentClockSelect ( new CheckStudentClockSelectPojo ( ( String ) session.getAttribute ( "clockMajor" ) , ( String ) session.getAttribute ( "clockProject" ) , ( String ) session.getAttribute ( "clockTeacher" ) ) ) ) {
					return 0;
				} else {
					return 3;
				}
			} else {
				return 1;
			}
		}
	}
	
	@Override
	public void updateStudentClock ( HttpSession session , StudentClockIn studentClockIn ) {
		this.studentclockinDao.updateSelective ( studentClockIn );
	}
	
	@Override
	public String getStudentNameByStudentNumber ( String studentNumber ) {
		return this.studentDao.getStudentNameByStudentNumber ( studentNumber );
	}
}
