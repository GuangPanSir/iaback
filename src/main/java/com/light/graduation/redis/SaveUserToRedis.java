package com.light.graduation.redis;

import com.light.graduation.dto.CheckLoginDTO;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;

/**
 * @Author: Light
 * @Date 2020/3/7 17:15
 */
public class SaveUserToRedis {
	/**
	 * 将redis中没有的用户登录信息保存至redis
	 */
	public static void saveUserToRedis ( @NotNull RedisTemplate< String, Object > redisTemplate , @NotNull Map< Object, Object > redisMap , @NotNull CheckLoginDTO checkLoginUser ) {
		redisMap.put ( "userName" , checkLoginUser.getUserName ( ) );
		redisMap.put ( "userPassword" , checkLoginUser.getUserPassword ( ) );
		redisMap.put ( "identity" , checkLoginUser.getIdentity ( ) );
		redisMap.put ( "isFirstLogin" , "1" );
		
		//save to redis
		redisTemplate.opsForHash ( ).putAll ( "user:" + checkLoginUser.identity + ":" + checkLoginUser.getUserName ( ) , redisMap );
	}
}
