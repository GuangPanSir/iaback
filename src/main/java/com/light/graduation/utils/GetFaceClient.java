package com.light.graduation.utils;

import com.baidu.aip.face.AipFace;
import org.jetbrains.annotations.NotNull;

/**
 * @Author: Light
 * @Date 2020/1/7 19:14
 */
public class GetFaceClient {
	private static final String APP_ID = "18203044";
	private static final String API_KEY = "GpfGIebd54wclVzToHhKxtkP";
	private static final String SECRET_KEY = "9HOTZd4NxwN7tBMHYPXpm3n9S4YfGhNW";
	
	@NotNull
	public static AipFace getClient ( ) {
		AipFace client = new AipFace ( APP_ID , API_KEY , SECRET_KEY );
		// 可选：设置网络连接参数
		client.setConnectionTimeoutInMillis ( 2000 );
		client.setSocketTimeoutInMillis ( 60000 );
		return client;
	}
}
