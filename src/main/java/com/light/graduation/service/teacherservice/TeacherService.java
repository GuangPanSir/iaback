package com.light.graduation.service.teacherservice;

import com.light.graduation.dto.CheckLoginDTO;
import com.light.graduation.dto.CurrentTimeInformation;
import com.light.graduation.dto.GetTeacherSettings;
import com.light.graduation.dto.StudentClockScore;
import com.light.graduation.entity.LoginRecord;
import com.light.graduation.entity.Teacher;
import com.light.graduation.pojo.CheckStudentClockSelectPojo;
import com.light.graduation.pojo.GetTeacherProjects;

import java.util.Date;
import java.util.List;

/**
 * @Author: Light
 * @Date 2020/3/2 16:51
 */
public interface TeacherService {
	/**
	 * 检测教师用户的登录状态
	 *
	 * @param loginUser 登录的用户
	 * @return 是否登陆成功
	 */
	boolean checkTeacherLogin ( CheckLoginDTO loginUser );
	
	/**
	 * 向数据库中插入教师设置的签到相关设置
	 *
	 * @param loginRecord 教师设置的签到相关选项
	 * @return 数据库的影响条数
	 */
	int teacherClockSetting ( LoginRecord loginRecord );
	
	/**
	 * 更新教师之前设置的相关签到信息
	 *
	 * @param loginRecord 教师设置的相关签到设置
	 * @return 数据库的影响条数
	 */
	int teacherClockUpdate ( LoginRecord loginRecord );
	
	/**
	 * 根据教师姓名获取教师编号
	 *
	 * @param teacherName 教师姓名
	 * @return 教师编号
	 */
	String selectTeacherNumberByTeacherName ( String teacherName );
	
	/**
	 * 根据教师姓名获取教师编号
	 *
	 * @param teacherNumber 教师编号
	 * @return 教师姓名
	 */
	String queryTeacherNameByTeacherNumber ( String teacherNumber );
	
	/**
	 * 实时获取学生本节课程的签到信息
	 * @param checkStudentClockSelectPojo 教师签到记录
	 * @param teacherStartTime 教师设置签到时间
	 * @param teacherAbsentTime 教师设置签到的截止时间
	 * @return 学生信息列表
	 */
	List< CurrentTimeInformation > getCurrentTimeClockInformation ( CheckStudentClockSelectPojo checkStudentClockSelectPojo, Date teacherStartTime, Date teacherAbsentTime);
	
	/**
	 * 更新教师信息
	 * @param teacher 要更新的教师信息
	 */
	void updateTeacherPassword ( Teacher teacher );
	
	/**
	 * 查询教师的课程
	 * @param teacherNumber 教师编号
	 * @return 教师所授课程
	 */
	List< GetTeacherProjects > getTeacherProjects ( String teacherNumber );
	
	/**
	 * 获取教师的所有考勤记录
	 * @param getTeacherSettings 教师考勤记录的唯一索引
	 * @return 教师所有的考勤记录
	 */
	List< LoginRecord > getTeacherLoginRecord ( GetTeacherSettings getTeacherSettings);
	
	/**
	 * 筛选课程的签到信息
	 * @param loginRecord 筛选信息
	 * @return 签到信息
	 */
	List< CurrentTimeInformation > getProjectClockDetail ( LoginRecord loginRecord);
	
	/**
	 * 根据考勤记录时间获取考勤记录
	 * @param startTime 开始时间
	 * @return 对应时间的考勤记录
	 */
	LoginRecord getLoginRecordByStartTime ( Date startTime );
	
	/**
	 * 获取学生的总览签到信息
	 *
	 * @param major       考勤专业
	 * @param project     考勤课程
	 * @param teacherName 考勤教师姓名
	 * @return 学生的总览亲到信息
	 */
	List< StudentClockScore > getStudentClockScore ( String major , String project , String teacherName );
	
	/**
	 * 超过最晚截止时间。进行缺勤的插入
	 * @param loginRecord 教师考勤记录表
	 */
	void disposeAbsent(LoginRecord loginRecord);
}
