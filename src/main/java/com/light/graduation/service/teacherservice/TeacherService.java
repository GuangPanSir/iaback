package com.light.graduation.service.teacherservice;

import com.light.graduation.dto.CheckLoginDTO;
import com.light.graduation.dto.CurrentTimeInformation;
import com.light.graduation.entity.LoginRecord;
import com.light.graduation.pojo.CheckStudentClockSelectPojo;

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
	 * @return 学生信息列表
	 */
	List< CurrentTimeInformation > getCurrentTimeClockInformation ( CheckStudentClockSelectPojo checkStudentClockSelectPojo, Date teacherStartTime);
}
