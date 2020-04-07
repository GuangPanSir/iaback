package com.light.graduation.controller;

import com.light.graduation.dto.SearchStudentDto;
import com.light.graduation.dto.StringDTO;
import com.light.graduation.entity.LoginRecord;
import com.light.graduation.entity.StudentClockIn;
import com.light.graduation.pojo.CheckStudentClockSelectPojo;
import com.light.graduation.pojo.StudentClockInformationPojo;
import com.light.graduation.pojo.StudentVacatePojo;
import com.light.graduation.service.projectservice.ProjectService;
import com.light.graduation.service.studentservice.StudentService;
import com.light.graduation.service.teacherservice.TeacherService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;

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
	
	@Resource( name = "teacherServiceImpl")
	private TeacherService teacherService;
	
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
	public HashMap< String, Object > checkStudentClockSelect ( HttpSession session , @RequestBody CheckStudentClockSelectPojo checkStudentClockSelectPojo ) {
		System.out.println ( checkStudentClockSelectPojo );
		HashMap< String, Object > map = new HashMap<> ( 15 );
		boolean isTrue = this.projectService.checkStudentClockSelect ( checkStudentClockSelectPojo );
		map.put ( "isTrue" , isTrue );
		if ( isTrue ) {
			session.setAttribute ( "clockMajor" , checkStudentClockSelectPojo.getMajor ( ) );
			session.setAttribute ( "clockProject" , checkStudentClockSelectPojo.getProject ( ) );
			session.setAttribute ( "clockTeacher" , checkStudentClockSelectPojo.getTeacher ( ) );
		}
		return map;
	}
	
	/**
	 * 学生请假处理
	 */
	@RequestMapping( "vacate" )
	@ResponseBody
	public HashMap studentVacate ( @NotNull HttpSession session , @RequestBody StudentVacatePojo studentVacatePojo ) {
		System.out.println (studentVacatePojo );
		HashMap< String, Object > hashMap = new HashMap<> ( 15 );
		
		StudentClockIn studentClockIn = new StudentClockIn ( );
		
		String clockProject = ( String ) session.getAttribute ( "clockProject" );
		String clockMajor = ( String ) session.getAttribute ( "clockMajor" );
		String clockTeacher = ( String ) session.getAttribute ( "clockTeacher" );
		
		studentClockIn.setClockTime ( new Date ( ) );
		studentClockIn.setClockInProject ( clockProject );
		studentClockIn.setStudentNumber ( ( String ) session.getAttribute ( "userName" ) );
		studentClockIn.setClockInTeacherNumber ( this.teacherService.selectTeacherNumberByTeacherName ( clockTeacher ) );
		studentClockIn.setClockLng ( studentVacatePojo.getLng ( ) );
		studentClockIn.setClockLat ( studentVacatePojo.getLat ( ) );
		studentClockIn.setClockAddress ( studentVacatePojo.getAddress ( ) );
		studentClockIn.setClockAccuracy ( studentVacatePojo.getAccuracy ( ) );
		studentClockIn.setClockState ( "请假" );
		studentClockIn.setErrorReason ( "待审核" );
		studentClockIn.setCertification ( studentVacatePojo.getImg ( ) );
		
		LoginRecord loginRecord = this.projectService.getLastLoginRecord ( new CheckStudentClockSelectPojo ( clockMajor , clockProject , clockTeacher ) );
		
		int isUpdate = this.studentService.checkStudentVacate ( ( String ) session.getAttribute ( "userName" ) );
		
		if(isUpdate == 0){
			loginRecord.setClockInAbnormal ( loginRecord.getClockInAbnormal ( ) + 1 );
			this.studentService.insertStudentClockIn ( studentClockIn );
			this.teacherService.teacherClockUpdate ( loginRecord );
			hashMap.put ( "state" , "successInsert" );
		}else{
			this.studentService.updateStudentClock ( session , studentClockIn );
			this.teacherService.teacherClockUpdate ( loginRecord );
			hashMap.put ( "state" , "successUpdate" );
		}
		
		return hashMap;
	}
	
	@RequestMapping( "studentClockDetail" )
	@ResponseBody
	public HashMap getStudentClockDetail ( @NotNull @RequestBody StringDTO stringDTO ) {
		HashMap< String, Object > hashMap = new HashMap<> ( 15 );
		StudentClockIn studentClockIn = this.studentService.getLastClockInformation ( stringDTO.getData ( ) );
		String studentName = this.studentService.getStudentNameByStudentNumber ( stringDTO.getData ( ) );
		hashMap.put ( "data" , studentClockIn );
		hashMap.put ( "studentName" , studentName );
		return hashMap;
	}
	
	@RequestMapping( "searchStudent" )
	@ResponseBody
	public HashMap searchStudent ( @NotNull @RequestBody StringDTO stringDTO ) {
		System.out.println ( stringDTO );
		SearchStudentDto student = this.studentService.searchStudent ( stringDTO.getData ( ) );
		HashMap< String, Object > hashMap = new HashMap<> ( 15 );
		hashMap.put ( "studentInfo" , student );
		return hashMap;
	}
}
