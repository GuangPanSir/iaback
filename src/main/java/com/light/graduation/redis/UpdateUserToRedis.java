package com.light.graduation.redis;

import org.jetbrains.annotations.Contract;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author: Light
 * @Date 2020/4/9 11:50
 */
@Component
public class UpdateUserToRedis {
	private final RedisTemplate< String, Object > redisTemplate;
	
	@Contract( pure = true )
	public UpdateUserToRedis ( RedisTemplate< String, Object > redisTemplate ) {
		this.redisTemplate = redisTemplate;
	}
	
	/**
	 * 修改密码
	 *
	 * @param number   用户编号
	 * @param password 修改后的密码
	 * @param identity 用户身份
	 */
	public void updateUserPassword ( String number , String password , String identity ) {
		this.redisTemplate.opsForHash ( ).put ( "user:" + identity + number , "password" , password );
	}
	
	/**
	 * 修改redis缓存中是否有学生的人脸信息
	 *
	 * @param studentNumber 学生学号
	 */
	public void updateUserIsFirstLogin ( String studentNumber ) {
		this.redisTemplate.opsForHash ( ).put ( "user:student:" + studentNumber , "isFirstLogin" , "0" );
	}
	
	/**
	 * 更新教师的登录密码
	 * @param teacherNumber 教师编号
	 * @param password 更新后的密码
	 */
	public void updateTeacherPassword ( String teacherNumber , String password ) {
		this.redisTemplate.opsForHash ( ).put ( "user:teacher:" + teacherNumber , "userPassword" , password );
	}
	
	/**
	 * 更新教师的登录密码
	 * @param studentNumber 教师编号
	 * @param password 更新后的密码
	 */
	public void updateStudentPassword ( String studentNumber , String password ) {
		this.redisTemplate.opsForHash ( ).put ( "user:student:" + studentNumber , "userPassword" , password );
	}
}
