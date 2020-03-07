package com.light.graduation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: Light
 * @Date 2020/3/2 16:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckLoginDTO implements Serializable {
	public String userName;
	public String userPassword;
	public String identity;
}
