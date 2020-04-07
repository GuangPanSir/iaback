package com.light.graduation.controller;

import com.light.graduation.dto.CheckFaceImageDTO;
import com.light.graduation.entity.LoginRecord;
import com.light.graduation.entity.Student;
import com.light.graduation.entity.StudentClockIn;
import com.light.graduation.pojo.CheckStudentClockSelectPojo;
import com.light.graduation.service.faceservice.FaceService;
import com.light.graduation.service.projectservice.ProjectService;
import com.light.graduation.service.studentservice.StudentService;
import com.light.graduation.service.teacherservice.TeacherService;
import com.light.graduation.utils.DistanceUtil;
import com.light.graduation.utils.ImageConvertUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Light
 * @Date 2020/2/19 15:16
 */
@Controller
@RequestMapping( "face" )
public class FaceController {
	@Resource( name = "faceServiceImpl" )
	private FaceService faceService;
	
	@Resource( name = "studentServiceImpl" )
	private StudentService studentService;
	
	@Resource( name = "teacherServiceImpl" )
	private TeacherService teacherService;
	
	@Resource( name = "projectServiceImpl" )
	private ProjectService projectService;
	
	@RequestMapping( "update" )
	@ResponseStatus( HttpStatus.OK )
	public void updateStudentImage ( ) {
		Student student = new Student ( );
		String imgStr = ImageConvertUtils.imageToBase64 ( "E:\\IACoding\\graduation\\src\\main\\resources\\img\\潘光健01.jpg" );
		student.setStudentNumber ( "201607054118" );
		student.setStudentFaceImage ( imgStr );
		this.faceService.updateStudentImage ( student );
	}
	
	@PostMapping( value = "check" )
	@ResponseBody
	public Map< String, Object > faceCompare ( @NotNull @RequestBody CheckFaceImageDTO checkFaceImageDTO , @NotNull HttpSession session ) {
		StudentClockIn studentClockIn = new StudentClockIn ( );
		HashMap< String, Object > map = new HashMap<> ( 15 );
		String studentFaceImage = this.faceService.getStudentFaceImage ( ( String ) session.getAttribute ( "userName" ) );
		boolean faceCompare = this.faceService.faceCompare ( checkFaceImageDTO.getImg ( ) , studentFaceImage );
		String quote = this.studentService.getQuote ( );
		
		/*
		  一些签到参数返回代码
		  0 正常签到
		  1 人脸不符
		  2 地理位置不在范围之内
		  3 签到属于迟到
		  4 签到成功，重复签到
		 */
		
//		int status;
		
		map.put ( "isTrue" , faceCompare );
		map.put ( "success" , "anythingElse" );
		map.put ( "late" , "I am ok !" );
		
		String clockProject = ( String ) session.getAttribute ( "clockProject" );
		String clockMajor = ( String ) session.getAttribute ( "clockMajor" );
		String clockTeacher = ( String ) session.getAttribute ( "clockTeacher" );
		
		studentClockIn.setClockInProject ( clockProject );
		studentClockIn.setClockTime ( new Date ( ) );
		studentClockIn.setClockInTeacherNumber ( this.teacherService.selectTeacherNumberByTeacherName ( clockTeacher ) );
		studentClockIn.setStudentNumber ( ( String ) session.getAttribute ( "userName" ) );
		studentClockIn.setClockLng ( checkFaceImageDTO.getLng ( ) );
		studentClockIn.setClockLat ( checkFaceImageDTO.getLat ( ) );
		studentClockIn.setClockAddress ( checkFaceImageDTO.getAddress ( ) );
		studentClockIn.setClockAccuracy ( checkFaceImageDTO.getAccuracy ( ) );
		studentClockIn.setClockInImg ( checkFaceImageDTO.getImg ( ) );
		
		int isStudentClockUpdate = this.studentService.checkStudentClock ( session , ( String ) session.getAttribute ( "userName" ) );
		
		
		LoginRecord loginRecord = this.projectService.getLastLoginRecord ( new CheckStudentClockSelectPojo ( clockMajor , clockProject , clockTeacher ) );
		System.out.println ( "isStudentClockUpdate" + isStudentClockUpdate );
		//已经请假，无需签到
		if ( isStudentClockUpdate == 4 ){
			map.put ( "success" , "successVacate" );
		}else {
			if ( faceCompare ) {
				//处理教师地理位置
				double teacherLng = loginRecord.getClockInLng ( );
				double teacherLat = loginRecord.getClockInLat ( );
				int teacherAccuracy = loginRecord.getClockInAccuracy ( );
				double locationDifference = loginRecord.getLocationDifference ( );
				
				//获取学生地理信息的经纬度信息
				double studentLng = checkFaceImageDTO.getLng ( );
				double studentLat = checkFaceImageDTO.getLat ( );
				int studentAccuracy = checkFaceImageDTO.getAccuracy ( );
				
				//师生实际相距多少米
				double realDistance = DistanceUtil.getDistance ( teacherLng , teacherLat , studentLng , studentLat );
				
				System.out.println ( "realDistance" + realDistance + "   " + ( ( double ) ( teacherAccuracy + studentAccuracy ) / 2 + locationDifference ) );
				
				//判断学生与教师之间的距离是否在教师设置的物产范围内
				//需要用上前端高德定位的精度范围值，因为会存在定位误差
				if ( realDistance > ( ( double ) ( teacherAccuracy + studentAccuracy ) / 2 + locationDifference ) ) {
					studentClockIn.setClockState ( "失败" );
					studentClockIn.setErrorReason ( "位置不符" );
					loginRecord.setClockInAbnormal ( loginRecord.getClockInAbnormal ( ) + 1 );
				} else {
					//迟到的状态
					if ( System.currentTimeMillis ( ) > loginRecord.getClockInEndTime ( ).getTime ( ) && System.currentTimeMillis ( ) < loginRecord.getClockInAbsentTime ( ).getTime ( ) ) {
						studentClockIn.setClockState ( "失败" );
						studentClockIn.setErrorReason ( "迟到" );
						loginRecord.setClockInOverdue ( loginRecord.getClockInOverdue ( ) + 1 );
						map.put ( "late" , "迟到" );
					} else {
						//正常签到
						map.put ( "quote" , quote );
						studentClockIn.setClockState ( "正常" );
						studentClockIn.setErrorReason ( "无" );
						loginRecord.setClockInNormal ( loginRecord.getClockInNormal ( ) + 1 );
					}
				}
			} else {
				//人脸不匹配的状态签到失败
				studentClockIn.setClockState ( "失败" );
				studentClockIn.setErrorReason ( "人脸不符" );
				loginRecord.setClockInAbnormal ( loginRecord.getClockInAbnormal ( ) + 1 );
			}
			if ( isStudentClockUpdate == 0 ) {
				//学生之前签到失败，重新进行签到操作
				this.studentService.updateStudentClock ( session , studentClockIn );
				//重新签到成功
				if ( faceCompare ) {
					loginRecord.setClockInAbnormal ( loginRecord.getClockInAbnormal ( ) - 1 );
					this.teacherService.teacherClockUpdate ( loginRecord );
				}
			} else if ( isStudentClockUpdate == 1 ) {
				//学生第一次进行签到
				this.studentService.insertStudentClockIn ( studentClockIn );
				this.teacherService.teacherClockUpdate ( loginRecord );
			} else if ( isStudentClockUpdate == 2 ) {
				//签到成功，重复签到
				map.put ( "success" , "alreadySuccess" );
			}
		}
		return map;
	}
}
