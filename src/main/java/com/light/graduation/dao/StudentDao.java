package com.light.graduation.dao;

import com.light.graduation.entity.Student;

import java.util.List;

public interface StudentDao {
    int deleteByPrimaryKey(String studentNumber);

    int insert( Student record);

    int insertSelective( Student record);

    Student selectByPrimaryKey( String studentNumber);

    int updateByPrimaryKeySelective( Student record);

    int updateByPrimaryKey( Student record);
	
	List< Student > selectAllStudents ( );
}