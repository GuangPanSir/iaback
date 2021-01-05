package com.light.graduation.service.studentservice;

import com.light.graduation.dto.CheckLoginDTO;
import com.light.graduation.dto.CurrentTimeInformation;
import com.light.graduation.dto.SearchStudentDto;
import com.light.graduation.dto.StudentClockInformationDTO;
import com.light.graduation.entity.AttendanceStatistics;
import com.light.graduation.entity.MajorToTeacher;
import com.light.graduation.entity.Student;
import com.light.graduation.entity.StudentClockIn;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * @Author: Light
 * @Date 2020/2/19 13:56
 */
public interface StudentService {
	
	/**
	 * 获取所有学生的信息
	 *
	 * @return 所有学生信息
	 */
	List< Student > selectAllStudents ( );
	
	/**
	 * 检测学生的登录状态
	 *
	 * @param loginUser 登录信息
	 * @return 学生的登录状态
	 */
	boolean checkStudentLogin ( CheckLoginDTO loginUser );
	
	/**
	 * 根据学生学号获取该生的所有签到信息
	 *
	 * @param studentNumber 学生学号
	 * @return 该生的签到信息
	 */
	List< StudentClockInformationDTO > getStudentClockInformation ( String studentNumber );
	
	/**
	 * 根据学生学号获取该生专业
	 *
	 * @param studentNumber 学生学号
	 * @return 学生专业
	 */
	String getStudentMajor ( String studentNumber );
	
	/**
	 * 获取名言警句
	 *
	 * @return 名言警句
	 */
	String getQuote ( );
	
	/**
	 * 将学生的签到信息存入数据库
	 *
	 * @param studentClockIn 学生签到信息
	 */
	void insertStudentClockIn ( StudentClockIn studentClockIn );
	
	/**
	 * 判断学生签到是第一次还是重新签到
	 *
	 * @param session       会话
	 * @param studentNumber 学生学号
	 * @return 标志 0 执行更新
	 * 1 执行插入
	 * 2 之前就成功
	 * 3 不做任何操作
	 * 4 如果请假，不允许签到
	 */
	int checkStudentClock ( HttpSession session , String studentNumber );
	
	/**
	 * 更新学生的签到信息，针对之前签到失败的学生而言
	 *
	 * @param session        会话
	 * @param studentClockIn 学生的签到信息
	 */
	void updateStudentClock ( HttpSession session , StudentClockIn studentClockIn );
	
	/**
	 * 根据学生编号获取学生姓名
	 *
	 * @param studentNumber 学生学号
	 * @return 学生姓名
	 */
	String getStudentNameByStudentNumber ( String studentNumber );
	
	/**
	 * 根据学生学号获取最近的一条签到记录
	 *
	 * @param studentNumber 学生学号
	 * @return 学生签到记录
	 */
	StudentClockIn getLastClockInformation ( String studentNumber );
	
	/**
	 * 学生请假是否更新
	 * @param studentNumber 学生学号
	 * @return 0 数据库中没有请假数据，执行插入操作
	 * 			1 执行更新操作
	 */
	int checkStudentVacate ( String studentNumber );
	
	/**
	 * 根据学生学号或学生姓名查询学生信息
	 * @param studentNumber 学生学号或姓名
	 * @return 学生信息
	 */
	SearchStudentDto searchStudent( String studentNumber);
	
	/**
	 * 根据学生学号获取该生的人脸信息
	 *
	 * @param studentNumber 学生学号
	 * @return 学生的人脸信息
	 */
	String getStudentFaceImage ( String studentNumber );
	
	/**
	 * 获取学生信息
	 * @param studentNumber 学生学号
	 * @return 学生信息
	 */
	Student getStudentInfo ( String studentNumber );
	
	/**
	 *  获取学生是否为第一次登录
	 * @param studentNumber 学生学号
	 * @return 登录状态
	 */
	Integer getStudentIsFirstLogin ( String studentNumber );
	
	/**
	 *  更新学生的人脸信息
	 * @param studentNumber 学生学号
	 * @param faceImg 人脸信息
	 * @return 是否更新成功
	 * 0 不存在人脸
	 * 1 存在人脸
	 */
	int updateStudentFace ( String studentNumber,String faceImg );
	
	/**
	 * 选择性地更新学生信息
	 *
	 * @param student 学生信息
	 */
	void updateStudentSelective ( Student student );
	
	/**
	 * 根据学号获取学生的课程考勤信息
	 *
	 * @param studentNumber 学生学号
	 * @param clockProject 考勤课程
	 * @return 课程考勤信息
	 */
	AttendanceStatistics getStudentProjectClockDetail ( String studentNumber , String clockProject );
	
	/**
	 * 添加学生的课程考勤信息
	 * @param record 添加记录
	 * @return 添加学生课程的考勤信息
	 */
	int insertSelective( AttendanceStatistics record);
	
	/**
	 * 选择性地更新数据表中的内容
	 * @param attendanceStatistics 考勤记录
	 */
	void updateSelective ( AttendanceStatistics attendanceStatistics );
	
	/**
	 * 获取学生课程考勤的分数
	 * @param attendanceStatistics 考勤记录单
	 */
	void updateStudentClockScore ( AttendanceStatistics attendanceStatistics );
	
	/**
	 * 获取专业做学的所有课程
	 * @param major 专业
	 * @return 专业课程
	 */
	List< MajorToTeacher > getMajorProject ( String major );
	
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
}
