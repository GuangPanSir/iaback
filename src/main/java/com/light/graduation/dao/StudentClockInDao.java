package com.light.graduation.dao;

import com.light.graduation.dto.CurrentTimeInformation;
import com.light.graduation.dto.StudentClockInformationDTO;
import com.light.graduation.entity.LoginRecord;
import com.light.graduation.entity.StudentClockIn;
import com.light.graduation.pojo.CheckStudentClockSelectPojo;

import java.util.Date;
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
	 * 选择性地更新学生的请假记录（针对之前签到失败的同学而言）
	 *
	 * @param studentClockIn 学生的签到信息
	 * @param VacateDate 请假时间
	 * @return 数据库的影响条数
	 */
	int updateVacateSelective ( StudentClockIn studentClockIn,Date VacateDate );
	
	/**
	 * 根据学生学号获取最近的一条签到记录
	 *
	 * @param studentNumber 学生学号
	 * @return 学生签到记录
	 */
	StudentClockIn getLastClockInformation ( String studentNumber );
	
	/**
	 * 实时获取学生本节课程的签到信息
	 * @param checkStudentClockSelectPojo 教师签到记录
	 * @param teacherStartTime 教师设置签到时间
	 * @param teacherAbsentTime 教师设置签到的截止时间
	 * @return 学生信息列表
	 */
	List< CurrentTimeInformation > getCurrentTimeClockInformation ( CheckStudentClockSelectPojo checkStudentClockSelectPojo, Date teacherStartTime, Date teacherAbsentTime);
	
	/**
	 * 获取学生的指定课程的考勤记录
	 * @param studentNumber 学生学号
	 * @param project 考勤课程
	 * @return 课程的考勤信息
	 */
	List< CurrentTimeInformation > getStudentProjectClockIn ( String studentNumber , String project );
	
	/**
	 * 查询学生课程的最近一次的签到记录
	 * @param studentNumber 学生学号
	 * @param clockTime 签到时间
	 * @return 学生签到记录
	 */
	StudentClockIn getProjectLastClockIn ( String studentNumber , Date clockTime );
	
	/**
	 * 查找缺勤的学生
	 * @param loginRecord 教师考勤记录
	 * @return 缺勤学生
	 */
	List< String > disposeAbsentStudents ( LoginRecord loginRecord );
	
	/**
	 * 插入缺勤学生
	 * @param students 缺勤学生
	 */
	void insertAbsentStudents(List<StudentClockIn> students);
}