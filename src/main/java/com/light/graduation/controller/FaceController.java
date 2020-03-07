package com.light.graduation.controller;

import com.light.graduation.dto.CheckFaceImageDTO;
import com.light.graduation.entity.Student;
import com.light.graduation.service.faceservice.FaceService;
import com.light.graduation.utils.ImageConvertUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
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
	
	@PostMapping(value = "check")
	@ResponseBody
	public Map< String, Object > faceCompare ( @NotNull @RequestBody CheckFaceImageDTO imgStr, @NotNull HttpSession session ) {
		System.out.println ( imgStr );
		HashMap< String, Object > map = new HashMap<> ( 15 );
		String studentFaceImage = this.faceService.getStudentFaceImage ( ( String ) session.getAttribute ( "userName" ) );
		boolean faceCompare = this.faceService.faceCompare ( imgStr.getImg ( ) , studentFaceImage );
		map.put ( "isTrue" , faceCompare );
		return map;
	}
}
