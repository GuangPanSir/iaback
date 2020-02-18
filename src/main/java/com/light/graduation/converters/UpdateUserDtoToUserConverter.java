package com.light.graduation.converters;

import com.light.usermanage.DTO.UpdateUserDTO;
import com.light.usermanage.pojo.User;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

/**
 * @Author: Light
 * @Date 2020/2/10 23:33
 */
public class UpdateUserDtoToUserConverter implements Converter< UpdateUserDTO, User > {
	@Override
	public User convert ( UpdateUserDTO updateUserDTO ) {
		User user = new User ( );
		if ( StringUtils.isEmpty ( updateUserDTO ) ) {
			return null;
		} else {
			try {
				BeanUtils.copyProperties ( updateUserDTO , user );
				return user;
			} catch ( Exception e ) {
				e.printStackTrace ( );
			}
		}
		return null;
	}
}
