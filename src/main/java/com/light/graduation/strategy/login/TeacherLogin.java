package com.light.graduation.strategy.login;

import com.light.graduation.dto.CheckLoginDTO;
import com.light.graduation.service.teacherservice.TeacherService;
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
 * @Date 2020/3/6 19:31
 */
@Component( value = "teacher" )
public class TeacherLogin implements CheckLogin {
	private final RedisTemplate< String, Object > redisTemplate;
	@Resource( name = "teacherServiceImpl" )
	private TeacherService teacherService;
	
	@Contract( pure = true )
	public TeacherLogin ( RedisTemplate< String, Object > redisTemplate ) {
		this.redisTemplate = redisTemplate;
	}
	
	@Override
	public Boolean checkLogin ( CheckLoginDTO checkLoginUser ) {
		return this.teacherService.checkTeacherLogin ( checkLoginUser );
	}
	
	@Override
	public Integer getType ( ) {
		return MemberEnum.TEACHER.getType ( );
	}
	
	@Override
	public String getIdentity ( ) {
		return MemberEnum.TEACHER.getIdentity ( );
	}
	
	@Override
	public HashMap checkUser ( @NotNull HttpSession session , @NotNull CheckLoginDTO checkLoginUser ) {
		
		Map< Object, Object > redisMap = redisTemplate.opsForHash ( ).entries ( "user:" + checkLoginUser.getIdentity ( ) + ":" + checkLoginUser.getUserName ( ) );
		
		String user = ( String ) session.getAttribute ( "userName" );
		
		HashMap< String, Object > map = new HashMap<> ( 15 );
		
		String password = "userPassword";
		
		boolean checkLogin = false;
		
		if ( redisMap.size ( ) != 0 ) {
			//match users from cache
			if ( redisMap.get ( password ).equals ( checkLoginUser.getUserPassword ( ) ) ) {
				checkLogin = true;
			}
		} else {
			checkLogin = this.teacherService.checkTeacherLogin ( checkLoginUser );
		}
		
		if ( checkLogin && redisMap.size ( ) == 0 ) {
			SaveUserToRedis.saveUserToRedis ( redisTemplate , redisMap , checkLoginUser );
		}
		
		if ( checkLogin ) {
			SaveToSession.saveToSession ( session , checkLoginUser );
			String teacherName = this.teacherService.queryTeacherNameByTeacherNumber ( checkLoginUser.getUserName ( ) );
			map.put ( "teacherName" , teacherName );
		}
		
		map.put ( "isTrue" , checkLogin );
		map.put ( "identity" , checkLoginUser.getIdentity ( ) );
		
		if ( user == null ) {
			map.put ( "isLogin" , false );
		} else {
			map.put ( "isLogin" , true );
		}
		
		System.out.println ( "teacherLogin" + session.getAttribute ( "userName" ) );
		
		return map;
	}
}
