package com.light.graduation.controller;

import com.light.graduation.dto.CheckLoginDTO;
import com.light.graduation.strategy.login.CheckLoginFactory;
import com.light.graduation.utils.AesEncryptUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * @Author: Light
 * @Date 2020/3/2 17:00
 */
@Controller
@RequestMapping( "login" )
public class LoginController {
	@Resource(name = "checkLoginFactory")
	private CheckLoginFactory checkLoginFactory;
	
	@RequestMapping( value = "login" )
	@ResponseBody
	public HashMap checkLogin ( HttpSession session , @NotNull @RequestBody CheckLoginDTO checkLoginUser ) throws IllegalAccessException {
		//decryption using reflection
		checkLoginUser = ( CheckLoginDTO ) AesEncryptUtil.desEncrypt ( checkLoginUser );
		
		//verify login and return
		return this.checkLoginFactory.doHandler ( session , checkLoginUser );
	}
}