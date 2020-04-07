package com.light.graduation.service.teacherservice.impl;

import com.light.graduation.dao.LoginRecordDao;
import com.light.graduation.dao.StudentClockInDao;
import com.light.graduation.dao.TeacherDao;
import com.light.graduation.dto.CheckLoginDTO;
import com.light.graduation.dto.CurrentTimeInformation;
import com.light.graduation.entity.LoginRecord;
import com.light.graduation.pojo.CheckStudentClockSelectPojo;
import com.light.graduation.service.teacherservice.TeacherService;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author: Light
 * @Date 2020/3/2 16:51
 */
@Service( value = "teacherServiceImpl" )
public class TeacherServiceImpl implements TeacherService {
	private final TeacherDao teacherDao;
	
	private final LoginRecordDao loginRecordDao;
	
	private final StudentClockInDao studentClockInDao;
	
	@Contract( pure = true )
	public TeacherServiceImpl ( LoginRecordDao loginRecordDao , TeacherDao teacherDao , StudentClockInDao studentClockInDao ) {
		this.loginRecordDao = loginRecordDao;
		this.teacherDao = teacherDao;
		this.studentClockInDao = studentClockInDao;
	}
	
	@Override
	public boolean checkTeacherLogin ( @NotNull CheckLoginDTO loginUser ) {
		String teacherPassword = this.teacherDao.getTeacherPassword ( loginUser.getUserName ( ) );
		return loginUser.getUserPassword ( ).equals ( teacherPassword );
	}
	
	@Override
	public int teacherClockSetting ( LoginRecord loginRecord ) {
		return this.loginRecordDao.insertSelective ( loginRecord );
	}
	
	@Override
	public String selectTeacherNumberByTeacherName ( String teacherName ) {
		return this.teacherDao.selectTeacherNumberByTeacherName ( teacherName );
	}
	
	@Override
	public String queryTeacherNameByTeacherNumber ( String teacherNumber ) {
		return this.teacherDao.queryTeacherNameByTeacherNumber ( teacherNumber );
	}
	
	@Override
	public int teacherClockUpdate ( LoginRecord loginRecord ) {
		return this.loginRecordDao.updateClockSettingSelective ( loginRecord );
	}
	
	@Override
	public List< CurrentTimeInformation > getCurrentTimeClockInformation ( CheckStudentClockSelectPojo checkStudentClockSelectPojo , Date teacherStartTime ) {
		return this.studentClockInDao.getCurrentTimeClockInformation ( checkStudentClockSelectPojo , teacherStartTime );
	}
}
