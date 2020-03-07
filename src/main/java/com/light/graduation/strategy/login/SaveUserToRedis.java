package com.light.graduation.strategy.login;

import com.light.graduation.dto.CheckLoginDTO;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;

/**
 * @Author: Light
 * @Date 2020/3/7 17:15
 */
class SaveUserToRedis {
	static void saveUserToRedis ( @NotNull RedisTemplate< String, Object > redisTemplate , @NotNull Map< Object, Object > redisMap , @NotNull CheckLoginDTO checkLoginUser ) {
		redisMap.put ( "userName" , checkLoginUser.getUserName ( ) );
		redisMap.put ( "userPassword" , checkLoginUser.getUserPassword ( ) );
		redisMap.put ( "identity" , checkLoginUser.getIdentity ( ) );
		
		//save to redis
		redisTemplate.opsForHash ( ).putAll ( "user:" + checkLoginUser.identity + ":" + checkLoginUser.getUserName ( ) , redisMap );
	}
}
