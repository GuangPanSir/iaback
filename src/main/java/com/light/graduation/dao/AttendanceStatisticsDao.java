package com.light.graduation.dao;

import com.light.graduation.dto.StudentClockScore;
import com.light.graduation.entity.AttendanceStatistics;

import java.util.List;

/**
 * @author Light
 */
public interface AttendanceStatisticsDao {
	/**
	 * 添加学生的课程考勤信息
	 * @param record 添加记录
	 * @return 添加学生课程的考勤信息
	 */
    int insertSelective( AttendanceStatistics record);
	
	/**
	 * 根据学号获取学生的课程考勤信息
	 * @param studentNumber 学生学号
	 * @param clockProject 考勤课程
	 * @return 课程考勤信息
	 */
	AttendanceStatistics getStudentProjectClockDetail ( String studentNumber ,String clockProject);
	
	/**
	 * 选择性地更新数据表中的内容
	 * @param attendanceStatistics 考勤记录
	 * @return 数据库的影响条数
	 */
	int updateSelective ( AttendanceStatistics attendanceStatistics );
	
	/**
	 * 获取学生的总览签到信息
	 *
	 * @param major       考勤专业
	 * @param project     考勤课程
	 * @param teacherName 考勤教师姓名
	 * @return 一门课程所有学生学生的总览签到信息
	 */
	List< StudentClockScore > getStudentClockScore ( String major , String project , String teacherName );
}