package com.light.graduation.dao;

import com.light.graduation.entity.Quote;

/**
 * @author Light
 */
public interface QuoteDao {
	/**
	 * 添加名言警句
	 *
	 * @param record 名言警句
	 * @return 数据库的影响条数
	 */
	int insert ( Quote record );
	
	/**
	 * 选择性地添加名言警句
	 *
	 * @param record 名言警句
	 * @return 数据库的影响条数
	 */
	int insertSelective ( Quote record );
	
	/**
	 * 随机从数据库中查询一条名言警句
	 *
	 * @return 一条名言警句
	 */
	String selectQuote ( );
}