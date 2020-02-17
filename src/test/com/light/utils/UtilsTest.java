package com.light.utils;

import com.baidu.aip.face.AipFace;
import com.baidu.aip.face.MatchRequest;
import com.light.entity.Student;
import com.light.face.BaiDuOnlineFaceService;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;

/**
 * @Author: Light
 * @Date 2020/1/7 19:12
 */

public class UtilsTest {
	
	private AipFace client = GetFaceClient.getClient ( );
	
	@Test
	public void matchFace ( ) {
		String url1 = "img\\潘光健01.jpg";
		String url2 = "img\\潘光健03.jpg";
		String encode1 = ImageConvertUtils.imageToBase64 ( url1 );
		String encode2 = ImageConvertUtils.imageToBase64 ( url2 );
		
		MatchRequest req1 = new MatchRequest(encode1, "BASE64");
		MatchRequest req2 = new MatchRequest(encode2, "BASE64");
		ArrayList<MatchRequest> requests = new ArrayList<> ( );
		requests.add(req1);
		requests.add(req2);
		
		JSONObject res = client.match(requests);
		System.out.println(res.toString(2));
	}
	
	@Test
	public void addFaceTest ( ) {
		String url1 = "img\\潘光健02.jpg";
		String encode1 = ImageConvertUtils.imageToBase64 ( url1 );
		Student student = new Student ( "201607054118", "潘光健", "IOTB161", encode1);
		new BaiDuOnlineFaceService ().addFace ( student );
	}
	
	@Test
	public void searchFaceTest ( ) {
		String url1 = "img\\刘亦菲01.jpg";
		String encode1 = ImageConvertUtils.imageToBase64 ( url1 );
		Student student = new Student ( "girl", "funny", encode1);
		new BaiDuOnlineFaceService ().searchFace ( student );
	}
}
