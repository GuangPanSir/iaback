package com.light.graduation;

/**
 * @Author: Light
 * @Date 2020/3/7 11:40
 */

import com.light.graduation.utils.AesEncryptUtil;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Light
 */
public class Test {
	public static void main ( String[] args ) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
		Student student = new Student ( "123" , "123" );
		Class c = student.getClass ( );
		
		student = ( Student ) AesEncryptUtil.desEncrypt ( student );
		
		/*Field[] fields = c.getDeclaredFields ( );
		for ( Field field : fields ) {
			System.out.println ( field );
			field.setAccessible ( true );
			System.out.println ( field.get ( student ) );
			field.set ( student , "456" );
		}*/
		
		
		System.out.println ( student.toString ( ) );
	}
}

@Data
@AllArgsConstructor
class Student{
	public String name;
	public String number;
}