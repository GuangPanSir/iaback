package com.light.graduation.strategy.login;

import com.light.graduation.dto.CheckLoginDTO;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * @Author: Light
 * @Date 2020/3/6 19:14
 */
public interface CheckLogin {
	/**
	 * 根据身份进行登录
	 *
	 * @param checkLoginUser 登陆者信息
	 * @return 是否登陆成功
	 */
	Boolean checkLogin ( CheckLoginDTO checkLoginUser );
	
	/**
	 * 获取登陆者身份代号
	 *
	 * @return 获取登陆者身份代号
	 */
	Integer getType ( );
	
	/**
	 * 获取登陆者身份
	 *
	 * @return 获取登陆者身份
	 */
	String getIdentity ( );
	
	/**
	 * 包含redis操作的完整校验
	 *
	 * @param checkLoginUser 登陆者的信息
	 * @param session        会话参数
	 * @return 需要返回前端的信息
	 */
	HashMap checkUser ( HttpSession session , CheckLoginDTO checkLoginUser );
}
