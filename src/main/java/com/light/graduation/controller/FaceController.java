package com.light.graduation.controller;

import com.light.graduation.entity.Student;
import com.light.graduation.service.faceservice.FaceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Light
 * @Date 2020/2/19 15:16
 */
@Controller
@RequestMapping( "face" )
public class FaceController {
	@Resource( name = "faceServiceImpl" )
	private FaceService faceService;
	
	@RequestMapping( "update" )
	@ResponseBody
	public void updateStudentImage ( ) {
		/*Student student = new Student ( );
		String imgStr = ImageConvertUtils.imageToBase64 ( "img/潘光健02.jpg" );
		student.setStudentNumber ( "201607054118" );
		student.setStudentFaceImage ( imgStr );*/
		this.faceService.selectAll ( );
	}
	
	@RequestMapping( "list" )
	@ResponseBody
	public List< Student > selectAll ( ) {
		return this.faceService.selectAll ( );
	}
	
}
