package com.light.graduation.face.faceserviceimpl;

import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.FaceFeature;
import com.arcsoft.face.FaceInfo;
import com.arcsoft.face.FaceSimilar;
import com.arcsoft.face.toolkit.ImageInfo;
import com.light.graduation.face.FaceService;
import com.light.graduation.face.GetFaceEngine;
import com.light.graduation.utils.ImageConvertUtils;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.arcsoft.face.toolkit.ImageFactory.getRGBData;

/**
 * @Author: Light
 * @Date 2020/1/14 11:04
 */
@Data
public class FaceServiceImpl implements FaceService {
	/**
	 * 获取人脸识别引擎
	 */
	private FaceEngine faceEngine = GetFaceEngine.getFaceEngine ( );
	
	private String imgStr;
	
	/**
	 * 图片信息
	 */
	public ImageInfo imageInfo = new ImageInfo ();
	
	/**
	 * 获取人脸信息
	 */
	public List< FaceInfo > faceInfoList = new ArrayList<>();
	
	/**
	 * 人脸信息
	 */
	public FaceFeature faceFeature = new FaceFeature (  );
	
	@Override
	public boolean detectFaces (   ) {
		//首先对标志位赋初值，图片中识别到人脸
		boolean flag = true;
		if ( getFaceInfo (  ).size ( ) == 0 ) {
			flag = false;
		}
		return flag;
	}
	
	@Override
	public int faceFeatureLength (   ) {
		return faceFeature (   ).length;
	}
	
	@Override
	public byte[] faceFeature (  ) {
		faceInfoList = getFaceInfo (  );
		faceEngine.extractFaceFeature ( imageInfo.getImageData ( ) , imageInfo.getWidth ( ) , imageInfo.getHeight ( ) , imageInfo.getImageFormat ( ) , faceInfoList.get ( 0 ) , faceFeature );
		return faceFeature.getFeatureData ( );
	}
	
	@Override
	public FaceFeature getFaceFeature (   ) {
		faceFeature (  );
		return faceFeature;
	}
	
	@Override
	public List< FaceInfo > getFaceInfo (   ) {
		//首先对标志位赋初值，图片中识别到人脸
		byte[] imageByte = ImageConvertUtils.base64String2ByteFun ( imgStr );
		imageInfo = getRGBData ( imageByte );
		if ( imageInfo == null ) {
			System.out.println ( "图片错误" );
		}
		faceEngine.detectFaces ( imageInfo.getImageData ( ) , imageInfo.getWidth ( ) , imageInfo.getHeight ( ) , imageInfo.getImageFormat ( ) , faceInfoList );
		System.out.println ( imageInfo );
		return faceInfoList;
	}
	
	@Override
	public float faceSimilarScore ( @NotNull FaceFeature targetFaceFeature , FaceSimilar faceSimilar ) {
		targetFaceFeature.setFeatureData ( targetFaceFeature.getFeatureData ( ) );
		this.faceFeature.setFeatureData ( this.faceFeature.getFeatureData ( ) );
		faceEngine.compareFaceFeature ( targetFaceFeature , this.faceFeature , faceSimilar );
		return faceSimilar.getScore ( );
	}
	
	@Override
	public boolean faceCompare ( FaceFeature targetFaceFeature , FaceSimilar faceSimilar ) {
		boolean flag = false;
		float checkSimilarScore = 0.8f;
		if ( faceSimilarScore ( targetFaceFeature,  faceSimilar ) > checkSimilarScore ) {
			flag = true;
		}
		return flag;
	}
}
