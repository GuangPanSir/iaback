package com.light.graduation.service;

import com.light.graduation.dao.NcistPersonDao;
import com.light.graduation.entity.NcistPerson;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: Light
 * @Date 2020/2/18 15:43
 */
public interface NcistPersonService {
	NcistPerson selectByPrimaryKey(String personNumber);
}
