package com.light.graduation.dao;

import com.light.graduation.dto.StudentClockInformationDTO;
import com.light.graduation.entity.StudentClockIn;

import java.util.List;

public interface StudentClockInDao {
    int insert( StudentClockIn record);

    int insertSelective( StudentClockIn record);
	
	List< StudentClockInformationDTO > getStudentClockInformation ( String studentNumber );
}