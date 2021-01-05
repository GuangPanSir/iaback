package com.light.graduation.service.teacherservice.impl;

import com.light.graduation.dao.AttendanceStatisticsDao;
import com.light.graduation.dao.LoginRecordDao;
import com.light.graduation.dao.StudentClockInDao;
import com.light.graduation.dao.TeacherDao;
import com.light.graduation.dto.CheckLoginDTO;
import com.light.graduation.dto.CurrentTimeInformation;
import com.light.graduation.dto.GetTeacherSettings;
import com.light.graduation.dto.StudentClockScore;
import com.light.graduation.entity.AttendanceStatistics;
import com.light.graduation.entity.LoginRecord;
import com.light.graduation.entity.StudentClockIn;
import com.light.graduation.entity.Teacher;
import com.light.graduation.pojo.CheckStudentClockSelectPojo;
import com.light.graduation.pojo.GetTeacherProjects;
import com.light.graduation.service.studentservice.StudentService;
import com.light.graduation.service.teacherservice.TeacherService;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: Light
 * @Date 2020/3/2 16:51
 */
@Service( value = "teacherServiceImpl" )
public class TeacherServiceImpl implements TeacherService{
	private final TeacherDao teacherDao;
	
	private final LoginRecordDao loginRecordDao;
	
	private final StudentClockInDao studentClockInDao;
	
	private final AttendanceStatisticsDao attendanceStatisticsDao;
	
	private final StudentService studentService;
	
	@Contract( pure = true )
	public TeacherServiceImpl ( LoginRecordDao loginRecordDao , TeacherDao teacherDao , StudentClockInDao studentClockInDao , AttendanceStatisticsDao attendanceStatisticsDao , StudentService studentService ) {
		this.loginRecordDao = loginRecordDao;
		this.teacherDao = teacherDao;
		this.studentClockInDao = studentClockInDao;
		this.attendanceStatisticsDao = attendanceStatisticsDao;
		this.studentService = studentService;
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
	public List< CurrentTimeInformation > getCurrentTimeClockInformation ( CheckStudentClockSelectPojo checkStudentClockSelectPojo , Date teacherStartTime, Date teacherAbsentTime ) {
		return this.studentClockInDao.getCurrentTimeClockInformation ( checkStudentClockSelectPojo , teacherStartTime, teacherAbsentTime );
	}
	
	@Override
	public void updateTeacherPassword ( Teacher teacher ) {
		this.teacherDao.updateTeacherSelective ( teacher );
	}
	
	@Override
	public List< GetTeacherProjects > getTeacherProjects ( String teacherNumber ) {
		return this.teacherDao.getTeacherProjects ( teacherNumber );
	}
	
	@Override
	public List< LoginRecord > getTeacherLoginRecord ( GetTeacherSettings getTeacherSettings ) {
		return this.teacherDao.getTeacherLoginRecord ( getTeacherSettings );
	}
	
	@Override
	public List< CurrentTimeInformation > getProjectClockDetail ( LoginRecord loginRecord ) {
		return this.teacherDao.getProjectClockDetail ( loginRecord );
	}
	
	@Override
	public LoginRecord getLoginRecordByStartTime ( Date startTime ) {
		return this.loginRecordDao.getLoginRecordByStartTime ( startTime );
	}
	
	@Override
	public List< StudentClockScore > getStudentClockScore ( String major , String project , String teacherName ) {
		return this.attendanceStatisticsDao.getStudentClockScore ( major , project , teacherName );
	}
	
	@Override
	public void disposeAbsent ( @NotNull LoginRecord loginRecord ) {
		Date clockAbsentTime = loginRecord.getClockInAbsentTime ( );
		String teacherNumber = loginRecord.getTeacherNumber ( );
		String clockInMajor = loginRecord.getClockInMajor ( );
		String clockInProject = loginRecord.getClockInProject ( );
		
		
		//当前时间超过预设的缺勤时间
		if ( System.currentTimeMillis ( ) > clockAbsentTime.getTime ( ) && "0".equals ( loginRecord.getIsFinish ( ) ) ) {
			System.out.println ( "缺勤学生插入" );
			List< String > absentStudents = this.studentClockInDao.disposeAbsentStudents ( loginRecord );
			List< StudentClockIn > absentStudentList = new ArrayList<> ( 15 );
			
			//studentNumber
			for ( String absentStudent : absentStudents ) {
				StudentClockIn item = new StudentClockIn ( );
				item.setClockInMajor ( clockInMajor );
				item.setClockInProject ( clockInProject );
				item.setClockInTeacherNumber ( teacherNumber );
				item.setClockTime ( loginRecord.getClockInAbsentTime ( ) );
				item.setClockState ( "失败" );
				item.setErrorReason ( "缺勤" );
				item.setStudentNumber ( absentStudent );
				absentStudentList.add ( item );
				
				AttendanceStatistics attendanceStatistics= this.attendanceStatisticsDao.getStudentProjectClockDetail ( absentStudent , clockInProject );
				
				if ( attendanceStatistics == null ) {
					AttendanceStatistics record = new AttendanceStatistics ( );
					record.setStudentNumber ( absentStudent );
					record.setClockProject ( clockInProject );
					record.setClockTeacher ( this.teacherDao.queryTeacherNameByTeacherNumber ( teacherNumber ) );
					record.setStudentMajor ( clockInMajor);
					record.setStudentCollege ( this.studentService.getStudentInfo ( absentStudent ).getStudentCollege ( ) );
					this.studentService.insertSelective ( record );
					attendanceStatistics= this.attendanceStatisticsDao.getStudentProjectClockDetail ( absentStudent , clockInProject );
				}
				
				attendanceStatistics.setClockAbsent ( attendanceStatistics.getClockAbsent ( ) + 1 );
				loginRecord.setClockInAbsent ( absentStudents.size ( )  );
				this.attendanceStatisticsDao.updateSelective ( attendanceStatistics );
				this.studentService.updateStudentClockScore ( attendanceStatistics );
				this.loginRecordDao.updateClockSettingSelective ( loginRecord );
			}
			
			absentStudents.forEach ( System.out :: println );
			absentStudentList.forEach ( student -> System.out.println ( student.toString ( ) ) );
			this.studentClockInDao.insertAbsentStudents ( absentStudentList );
			
			loginRecord.setIsFinish ( "1" );
			this.loginRecordDao.updateClockSettingSelective ( loginRecord );
		} else {
			System.out.println ( "当前正在考勤中" );
		}
	}
}
