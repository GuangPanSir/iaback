package com.light.graduation.service.studentservice;

import com.light.graduation.dto.CheckLoginDTO;
import com.light.graduation.dto.StudentClockInformationDTO;
import com.light.graduation.entity.Student;
import com.light.graduation.entity.StudentClockIn;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Author: Light
 * @Date 2020/2/19 13:56
 */
public interface StudentService {
	/**
	 * 根据学生编号获取该生相应的信息
	 *
	 * @param studentNumber 学生编号
	 * @return 学生信息
	 */
	Student selectByPrimaryKey ( String studentNumber );
	
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
}
