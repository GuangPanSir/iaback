package com.light.graduation.service.projectservice.impl;

import com.light.graduation.dao.LoginRecordDao;
import com.light.graduation.dao.MajorToTeacherDao;
import com.light.graduation.entity.LoginRecord;
import com.light.graduation.pojo.CheckStudentClockSelectPojo;
import com.light.graduation.service.projectservice.ProjectService;
import org.jetbrains.annotations.Contract;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Light
 * @Date 2020/3/4 17:31
 */
@Service(value = "projectServiceImpl")
public class ProjectServiceImpl implements ProjectService {
	private final MajorToTeacherDao majorToTeacherDao;
	private final LoginRecordDao loginRecordDao;
	
	@Contract( pure = true )
	public ProjectServiceImpl ( MajorToTeacherDao majorToTeacherDao , LoginRecordDao loginRecordDao ) {
		this.majorToTeacherDao = majorToTeacherDao;
		this.loginRecordDao = loginRecordDao;
	}
	
	@Override
	public List< String > getAllMajors ( ) {
		return this.majorToTeacherDao.getAllMajors ( );
	}
	
	@Override
	public List< String > getAllProjects ( String major ) {
		return this.majorToTeacherDao.getAllProjects ( major );
	}
	
	@Override
	public List< String > getAllTeachers ( String project ) {
		return this.majorToTeacherDao.getAllTeachers ( project );
	}
	
	@Override
	public Boolean checkStudentClockSelect ( CheckStudentClockSelectPojo checkStudentClockSelectPojo ) {
		LoginRecord loginRecord = this.loginRecordDao.checkStudentClockSelect ( checkStudentClockSelectPojo );
		Long loginRecordStartTime;
		try {
			loginRecordStartTime = loginRecord.getClockInStartTime ( ).getTime ( );
		} catch ( NullPointerException e ) {
			return false;
		}
		Long localTime = System.currentTimeMillis ( );
		
		//老师设置的签到任务是在十分钟之前发布的
		return localTime - loginRecordStartTime <= 10 * 60 * 1000;
	}
}
