package com.light.graduation.controller;

import com.light.graduation.dto.CurrentTimeInformation;
import com.light.graduation.dto.SearchStudentDto;
import com.light.graduation.dto.StringDTO;
import com.light.graduation.dto.StudentClockDetailDTO;
import com.light.graduation.entity.*;
import com.light.graduation.pojo.CheckStudentClockSelectPojo;
import com.light.graduation.pojo.StudentClockInformationPojo;
import com.light.graduation.pojo.StudentVacatePojo;
import com.light.graduation.redis.UpdateUserToRedis;
import com.light.graduation.service.projectservice.ProjectService;
import com.light.graduation.service.studentservice.StudentService;
import com.light.graduation.service.teacherservice.TeacherService;
import com.light.graduation.utils.AesEncryptUtil;
import com.light.graduation.utils.ImageConvertUtils;
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
 * @Date 2020/2/19 14:22
 */
@Controller
@RequestMapping( "student" )
public class StudentController {
	@Resource( name = "studentServiceImpl" )
	private StudentService studentService;
	
	@Resource( name = "projectServiceImpl" )
	private ProjectService projectService;
	
	@Resource( name = "teacherServiceImpl" )
	private TeacherService teacherService;
	
	private final UpdateUserToRedis updateUserToRedis;
	
	public StudentController ( UpdateUserToRedis updateUserToRedis ) {
		this.updateUserToRedis = updateUserToRedis;
	}
	
	/**
	 * 用于查询用户的所有签到信息，返回前端并进行展示
	 *
	 * @param session 本次用戶的会话
	 * @return 用户签到信息列表
	 */
	@RequestMapping( "clockInformation" )
	@ResponseBody
	public StudentClockInformationPojo selectStudentClockInformation ( @NotNull HttpSession session ) {
		StudentClockInformationPojo studentClockInformationPojo = new StudentClockInformationPojo ( );
		System.out.println ( this.studentService.getStudentClockInformation ( ( String ) session.getAttribute ( "userName" ) ) );
		studentClockInformationPojo.setList ( this.studentService.getStudentClockInformation ( ( String ) session.getAttribute ( "userName" ) ) );
		System.out.println ( studentClockInformationPojo );
		return studentClockInformationPojo;
	}
	
	/**
	 * 检测学生签到选项是否合法
	 * 即检测本专业是否有老师发布签到任务
	 */
	@RequestMapping( "studentCheckClockSelect" )
	@ResponseBody
	public HashMap< String, Object > checkStudentClockSelect ( @NotNull HttpSession session , @RequestBody CheckStudentClockSelectPojo checkStudentClockSelectPojo ) {
		HashMap< String, Object > map = new HashMap<> ( 15 );
		boolean isTrue = this.projectService.checkStudentClockSelect ( checkStudentClockSelectPojo );
		String studentNumber = ( String ) session.getAttribute ( "userName" );
		System.out.println ("==============" + studentNumber );
		map.put ( "isTrue" , isTrue );
		if ( isTrue ) {
			session.setAttribute ( "clockMajor" , checkStudentClockSelectPojo.getMajor ( ) );
			session.setAttribute ( "clockProject" , checkStudentClockSelectPojo.getProject ( ) );
			session.setAttribute ( "clockTeacher" , checkStudentClockSelectPojo.getTeacher ( ) );
		}
		
		//添加课程考勤总体记录
		AttendanceStatistics attendanceStatistics = this.studentService.getStudentProjectClockDetail ( studentNumber , checkStudentClockSelectPojo.getProject ( ) );
		
		if ( attendanceStatistics == null ) {
			AttendanceStatistics record = new AttendanceStatistics ( );
			record.setStudentNumber ( studentNumber );
			record.setClockProject ( checkStudentClockSelectPojo.getProject ( ) );
			record.setClockTeacher ( checkStudentClockSelectPojo.getTeacher ( ) );
			record.setStudentMajor ( checkStudentClockSelectPojo.getMajor ( ) );
			record.setStudentCollege ( this.studentService.getStudentInfo ( studentNumber ).getStudentCollege ( ) );
			this.studentService.insertSelective ( record );
		}
		
		return map;
	}
	
	/**
	 * 学生请假处理
	 */
	@RequestMapping( "vacate" )
	@ResponseBody
	public HashMap studentVacate ( @NotNull HttpSession session , @RequestBody StudentVacatePojo studentVacatePojo ) {
		System.out.println ( studentVacatePojo );
		HashMap< String, Object > hashMap = new HashMap<> ( 15 );
		
		StudentClockIn studentClockIn = new StudentClockIn ( );
		
		String studentNumber = ( String ) session.getAttribute ( "userName" );
		String clockProject = ( String ) session.getAttribute ( "clockProject" );
		String clockMajor = ( String ) session.getAttribute ( "clockMajor" );
		String clockTeacher = ( String ) session.getAttribute ( "clockTeacher" );
		
		AttendanceStatistics attendanceStatistics = this.studentService.getStudentProjectClockDetail ( studentNumber , clockProject );
		attendanceStatistics.setClockAbnormal ( attendanceStatistics.getClockAbnormal ( ) + 1 );
		
		studentClockIn.setClockTime ( new Date ( ) );
		studentClockIn.setClockInProject ( clockProject );
		studentClockIn.setStudentNumber ( studentNumber );
		studentClockIn.setClockInTeacherNumber ( this.teacherService.selectTeacherNumberByTeacherName ( clockTeacher ) );
		studentClockIn.setClockLng ( studentVacatePojo.getLng ( ) );
		studentClockIn.setClockLat ( studentVacatePojo.getLat ( ) );
		studentClockIn.setClockAddress ( studentVacatePojo.getAddress ( ) );
		studentClockIn.setClockAccuracy ( studentVacatePojo.getAccuracy ( ) );
		studentClockIn.setClockState ( "请假" );
		studentClockIn.setErrorReason ( "待审核" );
		studentClockIn.setCertification ( studentVacatePojo.getImg ( ) );
		studentClockIn.setClockInMajor ( clockMajor );
		
		LoginRecord loginRecord = this.projectService.getLastLoginRecord ( new CheckStudentClockSelectPojo ( clockMajor , clockProject , clockTeacher ) );
		
		int isUpdate = this.studentService.checkStudentVacate ( ( String ) session.getAttribute ( "userName" ) );
		
		if ( isUpdate == 0 ) {
			loginRecord.setClockInAbnormal ( loginRecord.getClockInAbnormal ( ) + 1 );
			this.studentService.insertStudentClockIn ( studentClockIn );
			this.teacherService.teacherClockUpdate ( loginRecord );
			hashMap.put ( "state" , "successInsert" );
		} else {
			this.studentService.updateStudentClock ( session , studentClockIn );
			this.teacherService.teacherClockUpdate ( loginRecord );
			hashMap.put ( "state" , "successUpdate" );
		}
		this.studentService.updateSelective ( attendanceStatistics );
		attendanceStatistics = this.studentService.getStudentProjectClockDetail ( studentNumber, clockProject );
		this.studentService.updateStudentClockScore ( attendanceStatistics );
		return hashMap;
	}
	
	/**
	 * 学生的所有签到信息
	 *
	 * @return 前端数据
	 */
	@RequestMapping( "studentClockDetail" )
	@ResponseBody
	public HashMap getStudentClockDetail ( @NotNull @RequestBody StudentClockDetailDTO studentClockDetailDTO ) {
		String studentNumber = studentClockDetailDTO.getStudentNumber ( );
		Date clockTime = studentClockDetailDTO.getClockTime ( );
		HashMap< String, Object > hashMap = new HashMap<> ( 15 );
		StudentClockIn studentClockIn = this.studentService.getProjectLastClockIn ( studentNumber , clockTime );
		String studentName = this.studentService.getStudentNameByStudentNumber ( studentNumber );
		hashMap.put ( "data" , studentClockIn );
		hashMap.put ( "studentName" , studentName );
		return hashMap;
	}
	
	/**
	 * 根据学号查询学生信息
	 */
	@RequestMapping( "searchStudent" )
	@ResponseBody
	public HashMap searchStudent ( @NotNull @RequestBody StringDTO stringDTO ) {
		System.out.println ( stringDTO );
		SearchStudentDto student = this.studentService.searchStudent ( stringDTO.getData ( ) );
		HashMap< String, Object > hashMap = new HashMap<> ( 15 );
		hashMap.put ( "studentInfo" , student );
		return hashMap;
	}
	
	/**
	 * 更新学生数据库中的人脸数据
	 */
	@RequestMapping( "updateStudentFace" )
	@ResponseBody
	public HashMap insertStudentFace ( @NotNull HttpSession session , @RequestBody StringDTO stringDTO ) {
		System.out.println ( stringDTO );
		HashMap< String, Object > hashMap = new HashMap<> ( 15 );
		ImageConvertUtils.GenerateImage ( stringDTO.getData ( ) );
		String studentNumber = ( String ) session.getAttribute ( "userName" );
		//更新成功
		if ( 1 == this.studentService.updateStudentFace ( studentNumber , stringDTO.getData ( ) ) ) {
			hashMap.put ( "isUpdate" , "success" );
		} else {
			//图片中不存在人脸，更新失败
			hashMap.put ( "isUpdate" , "fail" );
		}
		return hashMap;
	}
	
	@PutMapping( "updatePassword" )
	@ResponseStatus( HttpStatus.OK )
	public void updatePassword ( @NotNull HttpSession session , @NotNull @RequestBody StringDTO stringDTO ) {
		String studentNumber = ( String ) session.getAttribute ( "userName" );
		System.out.println ( studentNumber );
		String password = AesEncryptUtil.desEncrypt ( stringDTO.getData ( ) ).trim ( );
		Student student = new Student ( );
		student.setStudentNumber ( studentNumber );
		student.setStudentPassword ( password );
		this.studentService.updateStudentSelective ( student );
		this.updateUserToRedis.updateStudentPassword ( studentNumber , password );
	}
	
	@GetMapping( "getStudentProjects" )
	@ResponseBody
	public HashMap getStudentProjects ( @RequestParam("major")String major) {
		HashMap<String, Object> hashMap = new HashMap<> ( 15 );
		List< MajorToTeacher > list = this.studentService.getMajorProject ( major );
		//将教师工号改成教师姓名
		for ( MajorToTeacher item : list ) {
			item.setTeacherNumber ( this.teacherService.queryTeacherNameByTeacherNumber ( item.getTeacherNumber ( ) ) );
		}
		hashMap.put ( "list" , list );
		return hashMap;
	}
	
	@GetMapping("studentProjectClock")
	@ResponseBody
	public HashMap getStudentProjectClock( @NotNull HttpSession session, @RequestParam("teacherName")String teacherName, @RequestParam( "teacherProject")String teacherProject ) {
		System.out.println ( teacherName + teacherProject );
//		String teacherNumber = this.teacherService.selectTeacherNumberByTeacherName ( teacherName );
		String studentNumber = ( String ) session.getAttribute ( "userName" );
		List< CurrentTimeInformation > list = this.studentService.getStudentProjectClockIn ( studentNumber , teacherProject );
		AttendanceStatistics attendanceStatistics = this.studentService.getStudentProjectClockDetail ( studentNumber , teacherProject );
		
		HashMap<String, Object> hashMap = new HashMap< String, Object > ( 15 );
		hashMap.put ( "clockList" , list );
		hashMap.put ( "attendanceStatistics" , attendanceStatistics );
		return hashMap;
	}
	
}