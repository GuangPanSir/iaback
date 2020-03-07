package com.light.graduation.service.teacherservice;

import com.light.graduation.dto.CheckLoginDTO;
import com.light.graduation.entity.LoginRecord;

/**
 * @Author: Light
 * @Date 2020/3/2 16:51
 */
public interface TeacherService {
	boolean checkTeacherLogin ( CheckLoginDTO loginUser);
	
	int teacherClockSetting ( LoginRecord loginRecord );
	
	String selectTeacherNumberByTeacherName ( String teacherName );
}
