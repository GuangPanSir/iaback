package com.light.graduation.controller;

import com.light.graduation.pojo.CheckStudentClockSelectPojo;
import com.light.graduation.pojo.StudentClockInformationPojo;
import com.light.graduation.service.projectservice.ProjectService;
import com.light.graduation.service.studentservice.StudentService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
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
}
