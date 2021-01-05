package com.light.graduation.dao;

import com.light.graduation.dto.CurrentTimeInformation;
import com.light.graduation.dto.GetTeacherSettings;
import com.light.graduation.entity.LoginRecord;
import com.light.graduation.entity.Teacher;
import com.light.graduation.pojo.GetTeacherProjects;

import java.util.List;

/**
 * @author Light
 */
public interface TeacherDao {
	/**
	 * 删除指定教师的相关信息
	 *
	 * @param teachernumber 教师编号
	 * @return 数据库的影响条数
	 */
	int deleteByPrimaryKey ( String teachernumber );
	
	/**
	 * 添加教师信息
	 *
	 * @param record 教师信息
	 * @return 数据库的影响条数
	 */
	int insert ( Teacher record );
	
	/**
	 * 选择性地添加教师信息
	 *
	 * @param record 教师信息
	 * @return 数据库的影响条数
	 */
	int insertSelective ( Teacher record );
	
	/**
	 * 根据教师编号是查询教师信息
	 *
	 * @param teachernumber 教师编号
	 * @return 教师信息
	 */
	Teacher selectByPrimaryKey ( String teachernumber );
	
	/**
	 * 选选择性地更新教师信息
	 *
	 * @param record 教师信息
	 * @return 数据库的设计
	 */
	int updateTeacherSelective ( Teacher record );
	
	/**
	 * 更新教师信息
	 *
	 * @param record 教师信息
	 * @return 数据库的影响条数
	 */
	int updateTeacher ( Teacher record );
	
	/**
	 * 根据教师编号获取教师登录密码
	 *
	 * @param teacherNumber 教师编号
	 * @return 教师登录密码
	 */
	String getTeacherPassword ( String teacherNumber );
	
	/**
	 * 根据教师姓名获取教师编号
	 *
	 * @param teacherName 教师姓名
	 * @return 教师编号
	 */
	String selectTeacherNumberByTeacherName ( String teacherName );
	
	/**
	 * 根据教师姓名获取教师姓名
	 *
	 * @param teacherNumber 教师编号
	 * @return 教师姓名
	 */
	String queryTeacherNameByTeacherNumber ( String teacherNumber );
	
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
	 * 根据已给的专业和课程获取授课教师的姓名
	 * @param teacherMajor 教师所教专业
	 * @param teacherProject 教师所教课程
	 * @return 获取教师名称
	 */
	List<String> getMajorToTeacher ( String teacherMajor , String teacherProject );
	
	/**
	 * 筛选课程的签到信息
	 * @param loginRecord 筛选信息
	 * @return 签到信息
	 */
	List< CurrentTimeInformation > getProjectClockDetail ( LoginRecord loginRecord);
}