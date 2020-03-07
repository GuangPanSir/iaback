package com.light.graduation.strategy.login;

import com.light.graduation.dto.CheckLoginDTO;
import lombok.Data;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Light
 * @Date 2020/3/6 19:40
 */
@Component
@Data
public class CheckLoginFactory {
	@Resource( name = "student" )
	private CheckLogin studentLogin;
	
	@Resource( name = "teacher" )
	private CheckLogin teacherLogin;
	
	private Map< String, CheckLogin > map;
	
	@Contract( pure = true )
	@Autowired
	public CheckLoginFactory ( Map< String, CheckLogin > map ) {
		this.map = map;
	}
	
	public HashMap doHandler ( HttpSession session , @NotNull CheckLoginDTO checkLoginDTO ) {
		return map.get ( checkLoginDTO.getIdentity ( ) ).checkUser ( session , checkLoginDTO );
	}
}
