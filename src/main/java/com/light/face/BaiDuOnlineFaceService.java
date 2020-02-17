package com.light.face;

import com.baidu.aip.face.AipFace;
import com.light.entity.Student;
import com.light.utils.GetFaceClient;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * @Author: Light
 * @Date 2020/1/7 20:42
 */
public class BaiDuOnlineFaceService {
	/**
	 * 	百度云账号的客户端
	 */
	private AipFace client = GetFaceClient.getClient ( );
	
	/**
	 *
	 * @param student 需要添加人脸的学生信息
	 *                此功能用于注册人脸信息
	 */
	public void addFace ( @NotNull Student student ) {
		// 传入可选参数调用接口
		HashMap< String, String > options = new HashMap<> ( 15 );
		/*
		  参数名称	是否必选	类型	默认值	说明
		  image	是	String		图片信息(总数据大小应小于10M)，图片上传方式根据image_type来判断。注：组内每个uid下的人脸图片数目上限为20张
		  image_type	是	String		图片类型
		  BASE64:图片的base64值，base64编码后的图片数据，编码后的图片大小不超过2M；
		  URL:图片的 URL地址( 可能由于网络等原因导致下载图片时间过长)；
		  FACE_TOKEN: 人脸图片的唯一标识，调用人脸检测接口时，会为每个人脸图片赋予一个唯一的FACE_TOKEN，同一张图片多次检测得到的FACE_TOKEN是同一个。
		  group_id	是	String		用户组id（由数字、字母、下划线组成），长度限制128B
		  user_id	是	String		用户id（由数字、字母、下划线组成），长度限制128B
		  user_info	否	String		用户资料，长度限制256B
		  quality_control	否	String	NONE	图片质量控制 NONE: 不进行控制 LOW:较低的质量要求 NORMAL: 一般的质量要求 HIGH: 较高的质量要求 默认 NONE
		  liveness_control	否	String	NONE	活体检测控制 NONE: 不进行控制 LOW:较低的活体要求(高通过率 低攻击拒绝率) NORMAL: 一般的活体要求(平衡的攻击拒绝率, 通过率) HIGH: 较高的活体要求(高攻击拒绝率 低通过率) 默认NONE
		  action_type	否	String	APPEND	操作方式 APPEND: 当user_id在库中已经存在时，对此user_id重复注册时，新注册的图片默认会追加到该user_id下,REPLACE : 当对此user_id重复注册时,则会用新图替换库中该user_id下所有图片,默认使用APPEND
		 */
		options.put("user_info", student.getStudentName ());
		options.put("quality_control", "NORMAL");
		options.put("liveness_control", "LOW");
		options.put("action_type", "REPLACE");
		
		String imageType = "BASE64";
		String groupId = student.getGrade ();
		String userId = student.getStudentId ();
		
		// 人脸注册
		JSONObject res = client.addUser(student.getStudentImage (), imageType, groupId, userId, options);
		System.out.println(res.toString(2));
		/*
		  返回值类型及说明
		  字段	必选	类型	说明
		  log_id	是	uint64	请求标识码，随机数，唯一
		  face_token	是	string	人脸图片的唯一标识
		  location	是	array	人脸在图片中的位置
		  +left	是	double	人脸区域离左边界的距离
		  +top	是	double	人脸区域离上边界的距离
		  +width	是	double	人脸区域的宽度
		  +height	是	double	人脸区域的高度
		  +rotation	是	int64	人脸框相对于竖直方向的顺时针旋转角，[-180,180]
		 */
	}
	
	/**
	 *
	 * @param student 需要匹配的用户的相关信息
	 *                此功能用于人脸检测，检测人脸库中是否有次用户的相关人脸信息
	 */
	public void searchFace ( @NotNull Student student ) {
		// 传入可选参数调用接口
		HashMap<String, String> options = new HashMap<> ( 15 );
		/*
		参数名称	是否必选	类型	默认值	说明
		image	是	String		图片信息(总数据大小应小于10M)，图片上传方式根据image_type来判断
		image_type	是	String		图片类型
		BASE64:图片的base64值，base64编码后的图片数据，编码后的图片大小不超过2M；
		URL:图片的 URL地址( 可能由于网络等原因导致下载图片时间过长)；
		FACE_TOKEN: 人脸图片的唯一标识，调用人脸检测接口时，会为每个人脸图片赋予一个唯一的FACE_TOKEN，同一张图片多次检测得到的FACE_TOKEN是同一个。
		group_id_list	是	String		从指定的group中进行查找 用逗号分隔，上限20个
		max_face_num	否	String		最多处理人脸的数目
		默认值为1(仅检测图片中面积最大的那个人脸) 最大值10
		match_threshold	否	String		匹配阈值（设置阈值后，score低于此阈值的用户信息将不会返回） 最大100 最小0 默认80
		此阈值设置得越高，检索速度将会越快，推荐使用默认阈值80
		quality_control	否	String	NONE	图片质量控制 NONE: 不进行控制 LOW:较低的质量要求 NORMAL: 一般的质量要求 HIGH: 较高的质量要求 默认 NONE
		liveness_control	否	String	NONE	活体检测控制 NONE: 不进行控制 LOW:较低的活体要求(高通过率 低攻击拒绝率) NORMAL: 一般的活体要求(平衡的攻击拒绝率, 通过率) HIGH: 较高的活体要求(高攻击拒绝率 低通过率) 默认NONE
		user_id	否	String		当需要对特定用户进行比对时，指定user_id进行比对。即人脸认证功能。
		max_user_num	否	String		查找后返回的用户数量。返回相似度最高的几个用户，默认为1，最多返回50个。
		*/
		options.put("max_face_num", "3");
		options.put("match_threshold", "70");
		options.put("quality_control", "NORMAL");
		options.put("liveness_control", "LOW");
		options.put("user_id", student.getStudentId ());
		options.put("max_user_num", "3");
		
		String imageType = "BASE64";
		String groupIdList = student.getGrade ();
		
		// 人脸搜索
		JSONObject res = client.search( student.getStudentImage () , imageType, groupIdList, options);
		System.out.println(res.toString(2));
		/*
		返回数据类型及说明
		字段	必选	类型	说明
		face_token	是	string	人脸标志
		user_list	是	array	匹配的用户信息列表
		+group_id	是	string	用户所属的group_id
		+user_id	是	string	用户的user_id
		+user_info	是	string	注册用户时携带的user_info
		+score	是	float	用户的匹配得分
		 */
	}
}
