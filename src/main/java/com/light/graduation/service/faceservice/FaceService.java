package com.light.graduation.service.faceservice;

import com.arcsoft.face.FaceFeature;
import com.arcsoft.face.FaceInfo;
import com.arcsoft.face.FaceSimilar;
import com.light.graduation.entity.Student;

import java.util.List;

/**
 * @Author: Light
 * @Date 2020/1/14 11:03
 */
public interface FaceService {
	/**
	 * 根据图片获取图片的信息列表
	 * @return 返回人脸信息列表
	 */
	List< FaceInfo > getFaceInfo ( );
	
	/**
	 * 用于检测图片中是否存在人脸
	 * @return 返回是否检测出人脸
	 */
	boolean detectFaces ( );
	
	/**
	 * 返回人脸特征信息
	 * @return 人脸特征信息
	 */
	FaceFeature getFaceFeature ( );
	
	/**
	 * 获取人脸特征值大小
	 * @return 返回特征值大小
	 */
	int faceFeatureLength ( );
	
	/**
	 * 返回图片特征值
	 * @return 图片的特征值
	 */
	byte[] faceFeature (  );
	
	/**
	 * 返回人脸相似分数
	 * @param targetFaceFeature 人脸信息特征
	 * @param faceSimilar 人脸相似度
	 * @return 人脸相似分数
	 */
	float faceSimilarScore ( FaceFeature targetFaceFeature , FaceSimilar faceSimilar );
	
	/**
	 * 返回人脸相似程度
	 * @param targetFaceFeature 人脸信息特征
	 * @param faceSimilar 人脸相似度
	 * @return 在一定的误差内，人脸是否相似
	 */
	boolean faceCompare( FaceFeature targetFaceFeature, FaceSimilar faceSimilar );
	
	/**
	 * 返回人脸相似程度
	 * @param targetImgStr 第一张图片的编码
	 * @param sourceImgStr	第二张图片的编码
	 * @return 在一定的误差内，人脸是否相似
	 */
	boolean faceCompare ( String targetImgStr , String sourceImgStr );
	
	void updateStudentImage ( Student student );
	
	String getStudentFaceImage ( String studentNumber );
}
