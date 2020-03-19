package com.light.graduation.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Light
 */
@Data
public class Quote implements Serializable {
	private static final long serialVersionUID = 1L;
	private String quote;
}