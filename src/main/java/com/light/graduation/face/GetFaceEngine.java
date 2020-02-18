package com.light.graduation.face;

import com.arcsoft.face.ActiveFileInfo;
import com.arcsoft.face.EngineConfiguration;
import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.FunctionConfiguration;
import com.arcsoft.face.enums.DetectMode;
import com.arcsoft.face.enums.DetectOrient;
import com.arcsoft.face.enums.ErrorInfo;

/**
 * @Author: Light
 * @Date 2020/1/14 10:13
 */
public class GetFaceEngine {
	private static final String APP_ID = "21J5VxXnYJ6fv6WoTXv5KxkeSrPT65sYC8VEY82fo4Zp";
	private static final String SDK_KEY = "EYXSr2oJinQbe42GGSYMezMjNiS4LrZyyQ41ngm9LA54";
	
	private static FaceEngine faceEngine = new FaceEngine ( "E:\\Chorme\\ArcSoft_ArcFace_Java_Windows_x64_V3.0\\libs\\WIN64" );
	
	private static void initFaceEngine ( ) {
		int errorCode = faceEngine.activeOnline ( APP_ID , SDK_KEY );
		
		if ( errorCode != ErrorInfo.MOK.getValue ( ) && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue ( ) ) {
			System.out.println ( "引擎激活失败" );
		}
		
		
		ActiveFileInfo activeFileInfo = new ActiveFileInfo ( );
		errorCode = faceEngine.getActiveFileInfo ( activeFileInfo );
		if ( errorCode != ErrorInfo.MOK.getValue ( ) && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue ( ) ) {
			System.out.println ( "获取激活文件信息失败" );
		}
		
		//引擎配置
		EngineConfiguration engineConfiguration = new EngineConfiguration ( );
		engineConfiguration.setDetectMode ( DetectMode.ASF_DETECT_MODE_IMAGE );
		engineConfiguration.setDetectFaceOrientPriority ( DetectOrient.ASF_OP_ALL_OUT );
		engineConfiguration.setDetectFaceMaxNum ( 20 );
		engineConfiguration.setDetectFaceScaleVal ( 16 );
		//功能配置
		FunctionConfiguration functionConfiguration = new FunctionConfiguration ( );
		functionConfiguration.setSupportAge ( true );
		functionConfiguration.setSupportFace3dAngle ( true );
		functionConfiguration.setSupportFaceDetect ( true );
		functionConfiguration.setSupportFaceRecognition ( true );
		functionConfiguration.setSupportGender ( true );
		functionConfiguration.setSupportLiveness ( true );
		functionConfiguration.setSupportIRLiveness ( true );
		engineConfiguration.setFunctionConfiguration ( functionConfiguration );
		
		
		//初始化引擎
		errorCode = faceEngine.init ( engineConfiguration );
		
		if ( errorCode != ErrorInfo.MOK.getValue ( ) ) {
			System.out.println ( "初始化引擎失败" );
		}
	}
	
	public static FaceEngine getFaceEngine ( ) {
		initFaceEngine ( );
		return faceEngine;
	}
}
