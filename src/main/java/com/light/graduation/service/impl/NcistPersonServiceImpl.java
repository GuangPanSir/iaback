package com.light.graduation.service.impl;

import com.light.graduation.dao.NcistPersonDao;
import com.light.graduation.entity.NcistPerson;
import com.light.graduation.service.NcistPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Light
 * @Date 2020/2/18 15:44
 */
@Service
public class NcistPersonServiceImpl implements NcistPersonService {
	@Autowired
	private NcistPersonDao ncistPersonDao;
	
	@Override
	public NcistPerson selectByPrimaryKey ( String personNumber ) {
		return this.ncistPersonDao.selectByPrimaryKey ( personNumber );
	}
}
