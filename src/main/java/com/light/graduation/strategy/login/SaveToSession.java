package com.light.graduation.strategy.login;

import com.light.graduation.dto.CheckLoginDTO;
import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpSession;

/**
 * @Author: Light
 * @Date 2020/3/7 17:46
 */
class SaveToSession {
	static void saveToSession ( @NotNull HttpSession session , @NotNull CheckLoginDTO checkLoginUser ) {
		//user information saved to session
		session.setAttribute ( "userName" , checkLoginUser.getUserName ( ) );
		//setting the session life cycle
		session.setMaxInactiveInterval ( 60 * 30 );
	}
}
