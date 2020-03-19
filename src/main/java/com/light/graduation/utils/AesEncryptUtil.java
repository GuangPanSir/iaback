package com.light.graduation.utils;

import org.apache.commons.codec.binary.Base64;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.Field;

/**
 * @Author: Light
 */

public class AesEncryptUtil {
	
	/**
	 * 使用AES-128-CBC加密模式，key需要为16位,key和iv可以相同！
	 */
	private static String KEY = "1234567890123456";
	
	private static String IV = "1234567890123456";
	
	
	/**
	 * 加密方法
	 *
	 * @param data 要加密的数据
	 * @param key  加密key
	 * @param iv   加密iv
	 * @return 加密的结果
	 */
	@Nullable
	private static String encrypt ( String data , String key , String iv ) {
		try {
			
			//"算法/模式/补码方式"NoPadding PkcsPadding
			Cipher cipher = Cipher.getInstance ( "AES/CBC/NoPadding" );
			int blockSize = cipher.getBlockSize ( );
			
			byte[] dataBytes = data.getBytes ( );
			int plaintextLength = dataBytes.length;
			if ( plaintextLength % blockSize != 0 ) {
				plaintextLength = plaintextLength + ( blockSize - ( plaintextLength % blockSize ) );
			}
			
			byte[] plaintext = new byte[ plaintextLength ];
			System.arraycopy ( dataBytes , 0 , plaintext , 0 , dataBytes.length );
			
			SecretKeySpec keyspec = new SecretKeySpec ( key.getBytes ( ) , "AES" );
			IvParameterSpec ivspec = new IvParameterSpec ( iv.getBytes ( ) );
			
			cipher.init ( Cipher.ENCRYPT_MODE , keyspec , ivspec );
			byte[] encrypted = cipher.doFinal ( plaintext );
			
			return new Base64 ( ).encodeToString ( encrypted );
			
		} catch ( Exception e ) {
			e.printStackTrace ( );
			return null;
		}
	}
	
	/**
	 * 解密方法
	 *
	 * @param data 要解密的数据
	 * @param key  解密key
	 * @param iv   解密iv
	 * @return 解密的结果
	 */
	@Nullable
	private static String desEncrypt ( String data , String key , String iv ) {
		try {
			byte[] encrypted1 = new Base64 ( ).decode ( data );
			
			Cipher cipher = Cipher.getInstance ( "AES/CBC/NoPadding" );
			SecretKeySpec keyspec = new SecretKeySpec ( key.getBytes ( ) , "AES" );
			IvParameterSpec ivspec = new IvParameterSpec ( iv.getBytes ( ) );
			
			cipher.init ( Cipher.DECRYPT_MODE , keyspec , ivspec );
			
			byte[] original = cipher.doFinal ( encrypted1 );
			return new String ( original );
		} catch ( Exception e ) {
			e.printStackTrace ( );
			return null;
		}
	}
	
	/**
	 * 使用默认的key和iv加密
	 *
	 * @param data 原始数据
	 * @return 加密数据
	 */
	public static String encrypt ( String data ) {
		return encrypt ( data , KEY , IV );
	}
	
	/**
	 * 使用默认的key和iv解密
	 *
	 * @param data 加密数据
	 * @return 解密
	 */
	public static String desEncrypt ( String data ) {
		return desEncrypt ( data , KEY , IV );
	}
	
	@NotNull
	@Contract( pure = true )
	public static Object desEncrypt ( @NotNull Object object ) throws IllegalAccessException {
		Class c = object.getClass ( );
		Field[] fs = c.getDeclaredFields ( );
		
		for ( Field field : fs ) {
			field.setAccessible ( true );
			Object o = field.get ( object );
			field.set ( object , desEncrypt ( ( String ) o ).trim ( ) );
		}
		
		return object;
	}
	
}