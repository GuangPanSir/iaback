package com.light.graduation.dao;

import com.light.graduation.entity.LoginRecord;
import com.light.graduation.pojo.CheckStudentClockSelectPojo;

public interface LoginRecordDao {
    int insert(LoginRecord record);
	
    int insertSelective(LoginRecord record);
	
	LoginRecord checkStudentClockSelect ( CheckStudentClockSelectPojo checkStudentClockSelectPojo );
}