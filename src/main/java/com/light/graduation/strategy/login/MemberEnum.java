package com.light.graduation.strategy.login;

import lombok.Getter;
import org.jetbrains.annotations.Contract;

/**
 * @Author: Light
 * @Date 2020/3/6 19:21
 */
@Getter
public enum MemberEnum {
	/**
	 * 登陆者的身份
	 */
	STUDENT ( 0 , "student" ),
	TEACHER ( 1 , "teacher" ),
	;
	
	int type;
	String identity;
	
	@Contract( pure = true )
	MemberEnum ( int type , String identity ) {
		this.type = type;
		this.identity = identity;
	}
}
