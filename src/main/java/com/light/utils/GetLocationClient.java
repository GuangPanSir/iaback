package com.light.utils;


import com.baidubce.BceClientConfiguration;
import com.baidubce.Protocol;
import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.dumap.DuMapClient;
import lombok.Data;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * @Author: Light
 * @Date 2020/1/8 8:08
 */
@Data
public class GetLocationClient {
	private static final String ACCESS_KEY_ID = "ed23c6c6dfac4cf1ba0e83861f66781c";
	private static final String SECRET_ACCESS_KEY = "8ffed517de9840869e6d68e649bf2aa4";
	public static final String APP_ID = "41a855f0-5890-4252-9ad2-7bdcd5825e1e";
	
	@NotNull
	@Contract( " -> new" )
	public static DuMapClient getLocationClient ( ) {
		// 初始化配置
		BceClientConfiguration config = new BceClientConfiguration ();
		
		// 设置网络协议
		config.setProtocol ( Protocol.HTTPS );
		// 设置允许打开的最大连接数
		config.setMaxConnections(10);
		
		// 设置建立连接的超时时间
		config.setConnectionTimeoutInMillis(5000);
		
//		config.setProxyDomain ( "http://lbs.baidubce.com" );
		
		config.setEndpoint ( "http://lbs.baidubce.com" );
		
		DuMapClient client = new DuMapClient ( config.withCredentials ( new DefaultBceCredentials ( ACCESS_KEY_ID , SECRET_ACCESS_KEY ) ) );
		return client;
	}
}
