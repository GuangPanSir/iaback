package com.light.graduation.controller;

import com.light.graduation.entity.NcistPerson;
import com.light.graduation.service.NcistPersonService;
import com.light.graduation.service.impl.NcistPersonServiceImpl;
import org.jetbrains.annotations.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	public NcistPerson queryForNcistPerson ( ) {
		NcistPerson ncistPerson = this.ncistPersonService.selectByPrimaryKey ( "201607054118" );
		System.out.println ( ncistPerson.toString ( ) );
		return ncistPerson;
	}
}
