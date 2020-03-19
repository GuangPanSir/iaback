package com.light.graduation.utils;

import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: Light
 * @Date 2020/3/3 15:29
 */
public class DateFormatUtil implements Converter< String, Date > {
	private SimpleDateFormat dateFormat = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss" );
	
	public Date covertTime ( @NotNull Date date ) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss" );
		String s = sdf.format ( date );
		Date formatDate = sdf.parse ( s );
		return formatDate;
	}
	
	@Override
	public Date convert ( @NotNull String s ) {
		if ( StringUtils.isEmpty ( s ) ) {
			return null;
		} else {
			try {
				return dateFormat.parse ( s );
			} catch ( ParseException e ) {
				e.printStackTrace ( );
			}
		}
		return null;
	}
}
