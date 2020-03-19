package com.light.graduation.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: Light
 * @Date 2020/2/10 23:33
 */
public class StringToDateConverter implements Converter< String, Date > {
	private static final SimpleDateFormat FORMAT = new SimpleDateFormat ( "YYYY-MM-dd" );
	
	@Override
	public Date convert ( String s ) {
		if ( StringUtils.isEmpty ( s ) ) {
			return null;
		} else {
			try {
				return FORMAT.parse ( s );
			} catch ( ParseException e ) {
				e.printStackTrace ( );
			}
		}
		return null;
	}
}
