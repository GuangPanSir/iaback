package com.light.graduation.controller;

import com.light.graduation.entity.NcistPerson;
import com.light.graduation.service.NcistPersonService;
import org.jetbrains.annotations.Contract;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Light
 * @Date 2020/2/18 15:48
 */
@Controller
@RequestMapping("ncist")
public class NcistPersonController {
	private final NcistPersonService ncistPersonService;
	
	@Contract( pure = true )
	public NcistPersonController ( NcistPersonService ncistPersonService ) {
		this.ncistPersonService = ncistPersonService;
	}
	
	@RequestMapping( "select" )
	@ResponseBody
	public Map< String, Object > queryForNcistPerson ( ) {
		NcistPerson ncistPerson = this.ncistPersonService.selectByPrimaryKey ( "201607054118" );
		HashMap< String, Object > map = new HashMap<> ( 15 );
		map.put ( "data" , ncistPerson );
		map.put ( "status" , 200 );
		return map;
	}
}
