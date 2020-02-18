package com.light.graduation.utils;

import org.apache.commons.codec.binary.Base64;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import java.io.*;

/**
 * @Author: Light
 * @Date 2020/1/7 20:34
 * 将图片转换为BASE64格式的数据
 */
public class ImageConvertUtils {
	public static String imageToBase64 ( String imagePath ) {
		InputStream inputStream;
		byte[] data = null;
		try {
			inputStream = new FileInputStream ( imagePath );
			data = new byte[ inputStream.available ( ) ];
			inputStream.read ( data );
			inputStream.close ();
		} catch ( IOException e ) {
			e.printStackTrace ( );
		}
		BASE64Encoder encoder = new BASE64Encoder();
		assert data != null;
		return encoder.encode ( data );
	}
	
	/**
	 * 	base64字符串转化成图片
	 */

	@Contract( "null -> false" )
	public static boolean GenerateImage( String imgStr) {   //对字节数组字符串进行Base64解码并生成图片
		//图像数据为空
		if (imgStr == null)
		{
			return false;
		}
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			//Base64解码
			byte[] b = decoder.decodeBuffer(imgStr);
			for (int i = 0; i < b.length; ++i) {
				//调整异常数据
				if (b[i] < 0) {
					b[i] += 256;
				}
			}
			//生成jpeg图片
			//新生成的图片
			String imgFilePath = "d://222.jpg";
			OutputStream out = new FileOutputStream (imgFilePath);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 图片到byte数组
	 * @param path 图片路径
	 * @return 返回数组
	 */
	public static  byte[] image2byte(String path) {
		byte[] data = null;
		FileImageInputStream input;
		try {
			input = new FileImageInputStream(new File(path));
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int numBytesRead;
			while ((numBytesRead = input.read(buf)) != -1) {
				output.write(buf, 0, numBytesRead);
			}
			data = output.toByteArray();
			output.close();
			input.close();
		} catch ( IOException ex1) {
			ex1.printStackTrace();
		}
		return data;
	}
	
	/**
	 * 将byte数组转换成图片
	 * @param data byte数组的数据
	 * @param path	生成图片的路径
	 */
	public static void byte2image( @NotNull byte[] data, String path) {
		int pathLength = 3;
		if (data.length < pathLength || "".equals( path )) {
			return;
		}
		try {
			FileImageOutputStream imageOutput = new FileImageOutputStream (new File(path));
			imageOutput.write(data, 0, data.length);
			imageOutput.close();
			System.out.println("Make Picture success,Please find image in " + path);
		} catch (Exception ex) {
			System.out.println("Exception: " + ex);
			ex.printStackTrace();
		}
	}
	
	/**
	 * 	byte数组到16进制字符串
	 * @param data byte数组
	 * @return 返回16进制字符串
	 */
	public String byte2string(byte[] data) {
		int maxSize= 200000;
		if (data == null || data.length <= 1) {
			return "0x";
		}
		if (data.length > maxSize) {
			return "0x";
		}
		StringBuilder sb = new StringBuilder ();
		int[] buf = new int[data.length];
		//byte数组转化成十进制
		for (int k = 0; k < data.length; k++) {
			buf[k] = data[k] < 0 ? (data[k] + 256) : (data[k]);
		}
		//十进制转化成十六进制
		for ( int i : buf ) {
			if ( i < 16 ) {
				sb.append ( "0" ).append ( Integer.toHexString ( i ) );
			} else {
				sb.append ( Integer.toHexString ( i ) );
			}
		}
		return "0x" + sb.toString().toUpperCase();
	}
	
	/**
	 * 	base64字符串转byte[]
	 * @param base64Str base64字符串
	 * @return 返回byte数组
	 */
	public static byte[] base64String2ByteFun(String base64Str){
		return Base64.decodeBase64(base64Str);
	}
	
	/**
	 * 	byte[]转base64
	 * @param b byte数组
	 * @return 返回base64字符串
	 */
	public static String byte2Base64StringFun(byte[] b){
		return Base64.encodeBase64String(b);
	}
}
