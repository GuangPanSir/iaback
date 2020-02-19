package com.light.graduation.controller;

import com.light.graduation.entity.Student;
import com.light.graduation.service.studentservice.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
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
	
	@RequestMapping( "list" )
	@ResponseBody
	public List< Student > selectAllStudents ( ) {
		return this.studentService.selectAllStudents ( );
	}
	
}
