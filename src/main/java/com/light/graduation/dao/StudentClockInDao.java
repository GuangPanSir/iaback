package com.light.graduation.dao;

import com.light.graduation.dto.StudentClockInformationDTO;
import com.light.graduation.entity.StudentClockIn;

import java.util.List;

/**
 * @author Light
 */
public interface StudentClockInDao {
	/**
	 * 添加学生的签到信息
	 *
	 * @param record 学生签到的信息
	 * @return 数据库的影响条数
	 */
	int insert ( StudentClockIn record );
	
	/**
	 * 选择性地添加学生的签到信息
	 *
	 * @param record 学生的签到信息
	 * @return 数据库的影响条数
	 */
	int insertSelective ( StudentClockIn record );
	
	/**
	 * 根据学生学号获取该生的所有签到记录
	 *
	 * @param studentNumber 学生学号
	 * @return 学生的签到记录
	 */
	List< StudentClockInformationDTO > getStudentClockInformation ( String studentNumber );
	
	/**
	 * 选择性地更新学生的签到记录（针对之前签到失败的同学而言）
	 *
	 * @param studentClockIn 学生的签到信息
	 * @return 数据库的影响条数
	 */
	int updateSelective ( StudentClockIn studentClockIn );
	
	/**
	 * 根据学生学号获取最近的一条签到记录
	 *
	 * @param studentNumber 学生学号
	 * @return 学生签到记录
	 */
	StudentClockIn getLastClockInformation ( String studentNumber );
	
}