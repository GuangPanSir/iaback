package com.light.graduation.controller;

import com.light.graduation.dto.CurrentTimeInformation;
import com.light.graduation.dto.StringDTO;
import com.light.graduation.dto.VacateCheckDTO;
import com.light.graduation.entity.LoginRecord;
import com.light.graduation.entity.StudentClockIn;
import com.light.graduation.pojo.CheckStudentClockSelectPojo;
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
import java.util.List;

/**
 * @Author: Light
 * @Date 2020/3/30 11:27
 */
@Controller
@RequestMapping("teacher")
public class TeacherController {
	@Resource
	private TeacherService teacherService;
	
	@Resource
	private ProjectService projectService;
	
	@Resource
	private StudentService studentService;
	
	@RequestMapping( "studentClockInformation" )
	@ResponseBody
	public HashMap< String, Object > getStudentClockInformation ( @NotNull HttpSession session ) {
		HashMap< String, Object > hashMap = new HashMap<> ( 15 );
		
		String teacherNumber = ( String ) session.getAttribute ( "userName" );
		String teacherName = this.teacherService.queryTeacherNameByTeacherNumber ( teacherNumber );
		String teacherMajor = ( String ) session.getAttribute ( "teacherMajor" );
//		String teacherProject = ( String ) session.getAttribute ( "teacherProject" );
		
		LoginRecord loginRecord = this.projectService.getTeacherLastLoginRecord ( teacherNumber );
		
		if(loginRecord == null){
			hashMap.put ( "list","null" );
		}else{
			CheckStudentClockSelectPojo checkStudentClockSelectPojo = new CheckStudentClockSelectPojo ( teacherMajor , loginRecord.getClockInProject () , teacherName );
			Date teacherStartTime = loginRecord.getClockInStartTime ( );
			List< CurrentTimeInformation > list = this.teacherService.getCurrentTimeClockInformation ( checkStudentClockSelectPojo , teacherStartTime );
			hashMap.put ( "loginRecord" , loginRecord );
			hashMap.put ( "list" , list );
		}
		return hashMap;
	}
	
	@RequestMapping( "studentVacate" )
	@ResponseBody
	public HashMap<String, Object> checkVacate ( @RequestBody VacateCheckDTO vacateCheckDTO, @NotNull HttpSession session ) {
		System.out.println ( vacateCheckDTO );
		HashMap<String, Object> hashMap = new HashMap< > ( 15 );
		
		String teacherNumber = ( String ) session.getAttribute ( "userName" );
		int vacateDecision = Integer.parseInt ( vacateCheckDTO.getVacateDecision ( ) );
		LoginRecord loginRecord = this.projectService.getTeacherLastLoginRecord ( teacherNumber );
		StudentClockIn studentClockIn = this.studentService.getLastClockInformation ( vacateCheckDTO.getStudentNumber ( ) );
		
		studentClockIn.setIsCheck ( 1 );
		
		if ( vacateDecision == 1 ) {
			studentClockIn.setErrorReason ( "批准请假" );
			studentClockIn.setIsPermit ( 1 );
			loginRecord.setClockInAbnormal ( loginRecord.getClockInAbnormal ( ) - 1 );
			loginRecord.setClockInNormal ( loginRecord.getClockInNormal ( ) + 1 );
		}else if ( vacateDecision == 2 ){
			studentClockIn.setErrorReason ( "不允请假" );
			studentClockIn.setIsPermit ( 0 );
			loginRecord.setClockInAbnormal ( loginRecord.getClockInAbnormal ( ) - 1 );
			loginRecord.setClockInNormal ( loginRecord.getClockInNormal ( ) + 1 );
		}
		studentClockIn.setDisposeTime ( new Date ( ) );
		this.teacherService.teacherClockUpdate ( loginRecord );
		this.studentService.updateStudentClock ( session , studentClockIn );
		hashMap.put ( "isOK" , true );
		return hashMap;
	}
	
	@RequestMapping( "clockInByTeacher" )
	@ResponseBody
	public HashMap clockInByTeacher ( @NotNull HttpSession session , @NotNull @RequestBody StringDTO stringDTO ) {
		HashMap<String, Object> hashMap = new HashMap<  > ( 15 );
		
		String teacherNumber = ( String ) session.getAttribute ( "userName" );
		LoginRecord loginRecord = this.projectService.getTeacherLastLoginRecord ( teacherNumber );
		StudentClockIn studentClockIn = this.studentService.getLastClockInformation ( stringDTO.getData ( ) );
		
		//第一次操作，执行插入
		if ( studentClockIn == null ) {
			//执行插入操作
			studentClockIn = new StudentClockIn ( );
			studentClockIn.setClockTime ( new Date ( ) );
			studentClockIn.setStudentNumber ( stringDTO.getData ( ) );
			studentClockIn.setClockInTeacherNumber ( teacherNumber );
			studentClockIn.setClockInProject ( loginRecord.getClockInProject ( ) );
			loginRecord.setClockInNormal ( loginRecord.getClockInNormal ( ) + 1 );
			studentClockIn.setClockState ( "正常" );
			studentClockIn.setErrorReason ( "教师手动签到" );
			this.studentService.insertStudentClockIn ( studentClockIn );
			hashMap.put ( "result" , "插入成功" );
		}else if (studentClockIn.getClockTime ( ).getTime ( ) > loginRecord.getClockInStartTime ( ).getTime ( )
			&& studentClockIn.getClockTime ( ).getTime ( ) < loginRecord.getClockInAbsentTime ( ).getTime ( ) ) {
			//签到成功，憨货才去找老师
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
		}  else if(studentClockIn.getClockTime ( ).getTime ( ) < loginRecord.getClockInStartTime ( ).getTime ( )){
			//数据库中没有本节课的签到记录
			studentClockIn.setClockTime ( new Date ( ) );
			studentClockIn.setStudentNumber ( stringDTO.getData ( ) );
			studentClockIn.setClockInTeacherNumber ( teacherNumber );
			loginRecord.setClockInNormal ( loginRecord.getClockInNormal ( ) + 1 );
			studentClockIn.setClockInProject ( loginRecord.getClockInProject ( ) );
			studentClockIn.setClockState ( "正常" );
			studentClockIn.setErrorReason ( "教师手动签到" );
			this.studentService.insertStudentClockIn ( studentClockIn );
			hashMap.put ( "result" , "插入成功" );
		}else{
			//在最晚截止时间后
			hashMap.put ( "result" , "憨货，你已经签到过了" );
		}
		this.teacherService.teacherClockUpdate ( loginRecord );
		
		return hashMap;
	}
}
