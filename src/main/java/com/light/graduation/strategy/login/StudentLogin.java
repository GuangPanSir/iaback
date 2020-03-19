package com.light.graduation.strategy.login;

import com.light.graduation.dto.CheckLoginDTO;
import com.light.graduation.service.studentservice.StudentService;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Light
 * @Date 2020/3/6 19:29
 */
@Component( value = "student" )
public class StudentLogin implements CheckLogin {
	private final RedisTemplate< String, Object > redisTemplate;
	@Resource( name = "studentServiceImpl" )
	private StudentService studentService;
	
	@Contract( pure = true )
	public StudentLogin ( RedisTemplate< String, Object > redisTemplate ) {
		this.redisTemplate = redisTemplate;
	}
	
	@Override
	public Boolean checkLogin ( CheckLoginDTO checkLoginUser ) {
		return this.studentService.checkStudentLogin ( checkLoginUser );
	}
	
	@Override
	public Integer getType ( ) {
		return MemberEnum.STUDENT.getType ( );
	}
	
	@Override
	public String getIdentity ( ) {
		return MemberEnum.STUDENT.getIdentity ( );
	}
	
	@Override
	public HashMap checkUser ( @NotNull HttpSession session , @NotNull CheckLoginDTO checkLoginUser ) {
		String user = ( String ) session.getAttribute ( "userName" );
		
		Map< Object, Object > redisMap = redisTemplate.opsForHash ( ).entries ( "user:" + checkLoginUser.getIdentity ( ) + ":" + checkLoginUser.getUserName ( ) );
		
		HashMap< String, Object > map = new HashMap<> ( 15 );
		
		//eliminate mana
		String password = "userPassword";
		
		//define login status
		boolean checkLogin = false;
		
		//query from cache first
		if ( redisMap.size ( ) != 0 ) {
			//match users from cache
			if ( redisMap.get ( password ).equals ( checkLoginUser.getUserPassword ( ) ) ) {
				checkLogin = true;
			}
		} else {
			//Does not exist in cache, query from hard disk
			checkLogin = this.studentService.checkStudentLogin ( checkLoginUser );
		}
		
		/*if ( checkLogin && redisMap.size () == 0 ) {
			SaveUserToRedis.saveUserToRedis ( redisTemplate , redisMap , checkLoginUser );
		}*/
		
		if ( checkLogin ) {
			SaveToSession.saveToSession ( session , checkLoginUser );
			map.put ( "studentName" , this.studentService.getStudentNameByStudentNumber ( checkLoginUser.getUserName ( ) ) );
			if ( redisMap.size ( ) == 0 ) {
				SaveUserToRedis.saveUserToRedis ( redisTemplate , redisMap , checkLoginUser );
			}
		}
		
		map.put ( "isTrue" , checkLogin );
		map.put ( "identity" , checkLoginUser.getIdentity ( ) );
		map.put ( "major" , this.studentService.getStudentMajor ( checkLoginUser.getUserName ( ) ) );
		
		if ( user == null ) {
			map.put ( "isLogin" , false );
		} else {
			map.put ( "isLogin" , true );
		}
		
		return map;
	}
}
