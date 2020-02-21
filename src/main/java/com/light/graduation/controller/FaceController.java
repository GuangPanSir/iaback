package com.light.graduation.controller;

import com.light.graduation.entity.Student;
import com.light.graduation.service.faceservice.FaceService;
import com.light.graduation.utils.ImageConvertUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.annotation.Resource;
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
	
	@RequestMapping( "update" )
	@ResponseStatus( HttpStatus.OK )
	public void updateStudentImage ( ) {
		Student student = new Student ( );
		String imgStr = ImageConvertUtils.imageToBase64 ( "E:\\IACoding\\graduation\\src\\main\\resources\\img\\潘光健01.jpg" );
		student.setStudentNumber ( "201607054118" );
		student.setStudentFaceImage ( imgStr );
		this.faceService.updateStudentImage ( student );
	}
	
	@RequestMapping( "check" )
	@ResponseBody
	public Map< String, Boolean > faceCompare ( @RequestParam("img")String imgStr ) {
		HashMap< String, Boolean > map = new HashMap< String, Boolean > ( 15 );
//		String imgStr = ImageConvertUtils.imageToBase64 ( "E:\\IACoding\\graduation\\src\\main\\resources\\img\\刘亦菲01.jpg" );
		String studentFaceImage = this.faceService.getStudentFaceImage ( "201607054118" );
		boolean faceCompare = this.faceService.faceCompare ( imgStr , studentFaceImage );
		map.put ( "isTrue" , faceCompare );
		return map;
	}
	
}
