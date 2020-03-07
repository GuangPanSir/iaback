package com.light.graduation.converters;

import com.light.graduation.dto.TeacherClockSettingDTO;
import com.light.graduation.entity.LoginRecord;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

/**
 * @Author: Light
 * @Date 2020/3/5 16:04
 */
public class TeacherClockSettingDTOToLoginRecord implements Converter< TeacherClockSettingDTO, LoginRecord > {
	@Override
	public LoginRecord convert ( @NotNull TeacherClockSettingDTO teacherClockSettingDTO ) {
		LoginRecord loginRecord = new LoginRecord ( );
		if ( StringUtils.isEmpty ( teacherClockSettingDTO ) ) {
			return null;
		} else {
			try {
				BeanUtils.copyProperties ( teacherClockSettingDTO , loginRecord );
				return loginRecord;
			} catch ( Exception e ) {
				e.printStackTrace ( );
			}
		}
		return null;
	}
}
