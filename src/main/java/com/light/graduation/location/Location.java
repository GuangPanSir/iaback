package com.light.graduation.location;

import com.baidubce.services.dumap.DuMapClient;
import com.baidubce.services.dumap.model.IPLocationParam;
import com.light.graduation.utils.GetLocationClient;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import static com.light.graduation.utils.GetLocationClient.APP_ID;
import static com.light.graduation.utils.UnicodeToUtf8.*;

/**
 * @Author: Light
 * @Date 2020/1/8 8:19
 */
public class Location {
	
	private DuMapClient client;
	
	private void createDuMapClient() {
		client = GetLocationClient.getLocationClient ();
	}
	
	@NotNull
	@SneakyThrows
	private String callDuMapService() {
		
		createDuMapClient ();
		
		IPLocationParam params = IPLocationParam.builder ( )
//			.ip ( "211.81.169.113" )
//			.ip ( "27.189.226.82" )
//			.ip ( "106.9.122.10" )
			.ip ( "169.254.141.53/16" )
			.coor ( "bd09ll" )
			.build ( );
		
		String location = client.locate(APP_ID, params);
		location = loadConvert ( location );
		return location;
	}
	
	public String createDuMapClientAndQuery() {
		return callDuMapService();
	}
}
