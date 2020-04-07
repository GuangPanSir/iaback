package com.light.graduation.dao;

import com.light.graduation.entity.LoginRecord;
import com.light.graduation.pojo.CheckStudentClockSelectPojo;

/**
 * @author Light
 */
public interface LoginRecordDao {
	/**
	 * 插入教师的签到相关设置信息
	 *
	 * @param record 教师设置的相关签到信息
	 * @return 数据库的影响条数
	 */
	int insert ( LoginRecord record );
	
	/**
	 * 选择性的插入教师的签到相关设置信息
	 *
	 * @param record 教师设置的相关签到信息
	 * @return 数据库的影响条数
	 */
	int insertSelective ( LoginRecord record );
	
	/**
	 * 根据专业，课程，教师查询出最近的教师设置记录
	 *
	 * @param checkStudentClockSelectPojo 学生签到信息的选择信息
	 * @return 教师的签到设置记录
	 */
	LoginRecord checkStudentClockSelect ( CheckStudentClockSelectPojo checkStudentClockSelectPojo );
	
	/**
	 * 选择性地更新教师签到信息的设置
	 *
	 * @param loginRecord 教师签到信息的设置
	 * @return 数据库的影响条数
	 */
	int updateClockSettingSelective ( LoginRecord loginRecord );
	
	/**
	 * 获取数据库中教师的最新一条的数据
	 * @param teacherNumber 教师编号
	 * @return 签到设置记录
	 */
	LoginRecord getTeacherLastLoginRecord ( String teacherNumber );
}