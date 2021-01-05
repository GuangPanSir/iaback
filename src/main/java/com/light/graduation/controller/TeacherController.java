package com.light.graduation.controller;

import com.light.graduation.dto.*;
import com.light.graduation.entity.AttendanceStatistics;
import com.light.graduation.entity.LoginRecord;
import com.light.graduation.entity.StudentClockIn;
import com.light.graduation.entity.Teacher;
import com.light.graduation.pojo.CheckStudentClockSelectPojo;
import com.light.graduation.redis.UpdateUserToRedis;
import com.light.graduation.service.projectservice.ProjectService;
import com.light.graduation.service.studentservice.StudentService;
import com.light.graduation.service.teacherservice.TeacherService;
import com.light.graduation.utils.AesEncryptUtil;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: Light
 * @Date 2020/3/30 11:27
 */
@Controller
@RequestMapping( "teacher" )
public class TeacherController {
	@Resource
	private TeacherService teacherService;
	
	@Resource
	private ProjectService projectService;
	
	@Resource
	private StudentService studentService;
	
	private final UpdateUserToRedis updateUserToRedis;
	
	@Contract( pure = true )
	public TeacherController ( UpdateUserToRedis updateUserToRedis ) {
		this.updateUserToRedis = updateUserToRedis;
	}
	
	/**
	 * 获取最新的考勤课程的信息
	 */
	@RequestMapping( "studentClockInformation" )
	@ResponseBody
	public HashMap< String, Object > getStudentClockInformation ( @NotNull HttpSession session ) {
		HashMap< String, Object > hashMap = new HashMap<> ( 15 );
		
		String teacherNumber = ( String ) session.getAttribute ( "userName" );
		String teacherName = this.teacherService.queryTeacherNameByTeacherNumber ( teacherNumber );
		String teacherMajor = ( String ) session.getAttribute ( "teacherMajor" );
//		String teacherProject = ( String ) session.getAttribute ( "teacherProject" );
		
		LoginRecord loginRecord = this.projectService.getTeacherLastLoginRecord ( teacherNumber );
		
		if ( loginRecord == null ) {
			hashMap.put ( "list" , "null" );
		} else {
			//判断时间是否在最晚截止时间之后，如果是，则进行缺勤学生的插入
			this.teacherService.disposeAbsent ( loginRecord );
			CheckStudentClockSelectPojo checkStudentClockSelectPojo = new CheckStudentClockSelectPojo ( teacherMajor , loginRecord.getClockInProject ( ) , teacherName );
			Date teacherStartTime = loginRecord.getClockInStartTime ( );
			Date teacherAbsentTime = loginRecord.getClockInAbsentTime ( );
			List< CurrentTimeInformation > list = this.teacherService.getCurrentTimeClockInformation ( checkStudentClockSelectPojo , teacherStartTime, teacherAbsentTime );
			hashMap.put ( "loginRecord" , loginRecord );
			hashMap.put ( "list" , list );
		}
		return hashMap;
	}
	
	/**
	 * 对学生请假进行处理
	 */
	@RequestMapping( "studentVacate" )
	@ResponseBody
	public HashMap< String, Object > checkVacate ( @NotNull @RequestBody VacateCheckDTO vacateCheckDTO , @NotNull HttpSession session ) {
		HashMap< String, Object > hashMap = new HashMap<> ( 15 );
		
		String teacherNumber = ( String ) session.getAttribute ( "userName" );
		String studentNumber = vacateCheckDTO.getStudentNumber ( );
		int vacateDecision = Integer.parseInt ( vacateCheckDTO.getVacateDecision ( ) );
		LoginRecord loginRecord = this.projectService.getTeacherLastLoginRecord ( teacherNumber );
		StudentClockIn studentClockIn = this.studentService.getLastClockInformation ( studentNumber );
		AttendanceStatistics attendanceStatistics = this.studentService.getStudentProjectClockDetail ( studentNumber , loginRecord.getClockInProject ( ) );
		
		studentClockIn.setIsCheck ( 1 );
		
		if ( vacateDecision == 1 ) {
			//正常请假算作是正常签到
			studentClockIn.setErrorReason ( "批准请假" );
			studentClockIn.setIsPermit ( 1 );
			loginRecord.setClockInAbnormal ( loginRecord.getClockInAbnormal ( ) - 1 );
			loginRecord.setClockInNormal ( loginRecord.getClockInNormal ( ) + 1 );
			attendanceStatistics.setClockAbnormal ( attendanceStatistics.getClockAbnormal ( ) - 1 );
			attendanceStatistics.setClockVacate ( attendanceStatistics.getClockVacate ( ) + 1 );
		} else if ( vacateDecision == 2 ) {
			//不允请假算作是异常签到
			studentClockIn.setErrorReason ( "不允请假" );
			studentClockIn.setIsPermit ( 0 );
		}
		studentClockIn.setDisposeTime ( new Date ( ) );
		this.teacherService.teacherClockUpdate ( loginRecord );
		this.studentService.updateStudentClock ( session , studentClockIn );
		this.studentService.updateSelective ( attendanceStatistics );
		attendanceStatistics = this.studentService.getStudentProjectClockDetail ( studentNumber , loginRecord.getClockInProject ( ) );
		this.studentService.updateStudentClockScore ( attendanceStatistics );
		hashMap.put ( "isOK" , true );
		return hashMap;
	}
	
	/**
	 * 教师对相关签到课程进行设置
	 * 包括添加及更新
	 */
	@RequestMapping( "clockInByTeacher" )
	@ResponseBody
	public HashMap clockInByTeacher ( @NotNull HttpSession session , @NotNull @RequestBody StringDTO stringDTO ) {
		HashMap< String, Object > hashMap = new HashMap<> ( 15 );
		
		String studentNumber = stringDTO.getData ( );
		String teacherNumber = ( String ) session.getAttribute ( "userName" );
		LoginRecord loginRecord = this.projectService.getTeacherLastLoginRecord ( teacherNumber );
		StudentClockIn studentClockIn = this.studentService.getLastClockInformation ( studentNumber );
		//教师手动签到算作正常签到
		AttendanceStatistics attendanceStatistics = this.studentService.getStudentProjectClockDetail ( studentNumber , loginRecord.getClockInProject ( ) );
		
		if ( attendanceStatistics == null ) {
			AttendanceStatistics record = new AttendanceStatistics ( );
			record.setStudentNumber ( studentNumber );
			record.setClockProject ( loginRecord.getClockInProject ( ) );
			record.setClockTeacher ( this.teacherService.queryTeacherNameByTeacherNumber ( teacherNumber ) );
			record.setStudentMajor ( loginRecord.getClockInMajor () );
			record.setStudentCollege ( this.studentService.getStudentInfo ( studentNumber ).getStudentCollege ( ) );
			this.studentService.insertSelective ( record );
		}
		
		attendanceStatistics = this.studentService.getStudentProjectClockDetail ( studentNumber , loginRecord.getClockInProject ( ) );
		
		attendanceStatistics.setClockNormal ( attendanceStatistics.getClockNormal ( ) + 1 );
		this.studentService.updateSelective ( attendanceStatistics );
		attendanceStatistics = this.studentService.getStudentProjectClockDetail ( studentNumber , loginRecord.getClockInProject ( ) );
		
		this.studentService.updateStudentClockScore ( attendanceStatistics );
		//第一次操作，执行插入
		if ( studentClockIn == null || studentClockIn.getClockTime () .before ( loginRecord.getClockInStartTime () ) ) {
			//执行插入操作
			studentClockIn = new StudentClockIn ( );
			studentClockIn.setClockTime ( new Date ( ) );
			studentClockIn.setStudentNumber ( studentNumber );
			studentClockIn.setClockInTeacherNumber ( teacherNumber );
			studentClockIn.setClockInProject ( loginRecord.getClockInProject ( ) );
			loginRecord.setClockInNormal ( loginRecord.getClockInNormal ( ) + 1 );
			studentClockIn.setClockState ( "正常" );
			studentClockIn.setErrorReason ( "教师手动签到" );
			studentClockIn.setClockInMajor ( loginRecord.getClockInMajor ( ) );
			this.studentService.insertStudentClockIn ( studentClockIn );
			hashMap.put ( "result" , "插入成功" );
		} else if ( studentClockIn.getClockTime ( ).getTime ( ) > loginRecord.getClockInStartTime ( ).getTime ( )
			&& studentClockIn.getClockTime ( ).getTime ( ) < loginRecord.getClockInAbsentTime ( ).getTime ( ) ) {
			//自己已经签到成功，憨货才去找老师
			if ( "正常".equals ( studentClockIn.getClockState ( ) ) ) {
				hashMap.put ( "result" , "无需重复签到" );
			} else {
				//手机出毛病了，找老师签到
				studentClockIn.setClockTime ( new Date ( ) );
				studentClockIn.setClockState ( "正常" );
				studentClockIn.setErrorReason ( "教师手动签到" );
				loginRecord.setClockInAbnormal ( loginRecord.getClockInAbnormal ( ) - 1 );
				loginRecord.setClockInNormal ( loginRecord.getClockInNormal ( ) + 1 );
				this.studentService.updateStudentClock ( session , studentClockIn );
				hashMap.put ( "result" , "更新成功" );
			}
		} else if ( studentClockIn.getClockTime ( ).getTime ( ) < loginRecord.getClockInStartTime ( ).getTime ( ) ) {
			//数据库中没有本节课的签到记录
			studentClockIn.setClockTime ( new Date ( ) );
			studentClockIn.setStudentNumber ( studentNumber );
			studentClockIn.setClockInTeacherNumber ( teacherNumber );
			loginRecord.setClockInNormal ( loginRecord.getClockInNormal ( ) + 1 );
			studentClockIn.setClockInProject ( loginRecord.getClockInProject ( ) );
			studentClockIn.setClockState ( "正常" );
			studentClockIn.setErrorReason ( "教师手动签到" );
			this.studentService.insertStudentClockIn ( studentClockIn );
			hashMap.put ( "result" , "插入成功" );
		} else {
			//在最晚截止时间后
			hashMap.put ( "result" , "憨货，你已经签到过了" );
		}
		this.teacherService.teacherClockUpdate ( loginRecord );
		
		return hashMap;
	}
	
	@PutMapping( "updatePassword" )
	@ResponseStatus( HttpStatus.OK )
	public void updatePassword ( @NotNull HttpSession session , @NotNull @RequestBody StringDTO stringDTO ) {
		String teacherNumber = ( String ) session.getAttribute ( "userName" );
		String password = AesEncryptUtil.desEncrypt ( stringDTO.getData ( ) ).trim ( );
		Teacher teacher = new Teacher ( );
		teacher.setTeacherPassword ( password );
		teacher.setTeacherNumber ( teacherNumber );
		this.teacherService.updateTeacherPassword ( teacher );
		this.updateUserToRedis.updateTeacherPassword ( teacherNumber , password );
	}
	
	
	@GetMapping( value = "getTeacherProjects" )
	@ResponseBody
	public HashMap< String, Object > getTeacherProjects ( @RequestParam( "data" ) String data ) {
		System.out.println ( data );
		HashMap< String, Object > hashMap = new HashMap<> ( 15 );
		String teacherNumber = this.teacherService.selectTeacherNumberByTeacherName ( data );
		List list = this.teacherService.getTeacherProjects ( teacherNumber );
		hashMap.put ( "projects" , list );
		return hashMap;
	}
	
	
	@GetMapping( "getTeacherSetting" )
	@ResponseBody
	public HashMap< String, Object > getTeacherSetting ( @NotNull HttpSession session , @RequestParam( "teacherMajor" ) String teacherMajor , @RequestParam( "teacherProject" ) String teacherProject ) {
		System.out.println ( teacherMajor + teacherProject );
		HashMap< String, Object > hashMap = new HashMap<> ( 15 );
		
		String teacherNumber = ( String ) session.getAttribute ( "userName" );
		GetTeacherSettings getTeacherSettings = new GetTeacherSettings ( );
		getTeacherSettings.setTeacherNumber ( teacherNumber );
		getTeacherSettings.setTeacherMajor ( teacherMajor );
		getTeacherSettings.setTeacherProject ( teacherProject );
		List< LoginRecord > loginRecordList = this.teacherService.getTeacherLoginRecord ( getTeacherSettings );
		
		System.out.println ( loginRecordList );
		
		hashMap.put ( "list" , loginRecordList );
		
		return hashMap;
	}
	
	@GetMapping( "getProjectClockDetail" )
	@ResponseBody
	public HashMap getProjectClockDetail ( @NotNull HttpSession session , @RequestParam( "clockInProject" ) String clockInProject ,
										   @RequestParam( "clockInStartTime" ) String clockInStartTime ,
										   @RequestParam( "clockInAbsentTime" ) String clockInAbsentTime ) {
		System.out.println ( clockInProject + clockInStartTime );
		String clockInTeacherNumber = ( String ) session.getAttribute ( "userName" );
		LoginRecord loginRecord = new LoginRecord ( );
		loginRecord.setTeacherNumber ( clockInTeacherNumber );
		loginRecord.setClockInProject ( clockInProject );
		loginRecord.setClockInStartTime ( new Date ( Long.parseLong ( clockInStartTime ) ) );
		loginRecord.setClockInAbsentTime ( new Date ( Long.parseLong ( clockInAbsentTime ) ) );
		
		System.out.println ( loginRecord );
		
		List< CurrentTimeInformation > list = this.teacherService.getProjectClockDetail ( loginRecord );
		
		HashMap< String, Object > hashMap = new HashMap<> ( 15 );
		
		hashMap.put ( "projectClockDetail" , list );
		hashMap.put ( "loginRecord" , this.teacherService.getLoginRecordByStartTime ( new Date ( Long.parseLong ( clockInStartTime ) ) ) );
		return hashMap;
	}
	
	@GetMapping( "getStudentClockScore" )
	@ResponseBody
	public HashMap getStudentClockScore ( @RequestParam( "major" ) String major , @RequestParam( "project" ) String project , @NotNull HttpSession session ) {
		String teacherNumber = ( String ) session.getAttribute ( "userName" );
		String teacherName = this.teacherService.queryTeacherNameByTeacherNumber ( teacherNumber );
		HashMap<String, Object> hashMap = new HashMap< > ( 15 );
		List< StudentClockScore > list = this.teacherService.getStudentClockScore ( major , project , teacherName );
		
		hashMap.put ( "listData" , list );
		
		return hashMap;
	}
}
