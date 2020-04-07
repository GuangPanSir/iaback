package com.light.graduation.service.projectservice;

import com.light.graduation.entity.LoginRecord;
import com.light.graduation.pojo.CheckStudentClockSelectPojo;

import java.util.List;

/**
 * @Author: Light
 * @Date 2020/3/4 17:31
 */
public interface ProjectService {
	/**
	 * 获取左右专业
	 *
	 * @return 所有专业
	 */
	List< String > getAllMajors ( );
	
	/**
	 * 根据专业获取此专业的所有课程
	 *
	 * @param major 专业
	 * @return 课程
	 */
	List< String > getAllProjects ( String major );
	
	/**
	 * 根据课程获取所有教授此课程的教师
	 *
	 * @param project 课程
	 * @return 教师
	 */
	List< String > getAllTeachers ( String project );
	
	/**
	 * 检测学生的签到选项是否合法
	 *
	 * @param checkStudentClockSelectPojo 学生签到所选课程及教师
	 * @return 签到选项是否正确
	 */
	Boolean checkStudentClockSelect ( CheckStudentClockSelectPojo checkStudentClockSelectPojo );
	
	/**
	 * 获取教师的最近一次的签到设置记录
	 *
	 * @param checkStudentClockSelectPojo 学生签到的相关信息（专业，课程，教师等相关信息）
	 * @return 签到信息
	 */
	LoginRecord getLastLoginRecord ( CheckStudentClockSelectPojo checkStudentClockSelectPojo );
	
	/**
	 * 获取数据库中教师的最新一条的数据
	 * @param teacherNumber 教师编号
	 * @return 签到设置记录
	 */
	LoginRecord getTeacherLastLoginRecord ( String teacherNumber );
}
