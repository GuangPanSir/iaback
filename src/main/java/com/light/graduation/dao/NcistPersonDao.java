package com.light.graduation.dao;

import com.light.graduation.entity.NcistPerson;

public interface NcistPersonDao {
    int deleteByPrimaryKey(String personNumber);

    int insert(NcistPerson record);

    int insertSelective(NcistPerson record);

    NcistPerson selectByPrimaryKey(String personNumber);

    int updateByPrimaryKeySelective(NcistPerson record);

    int updateByPrimaryKey(NcistPerson record);
}