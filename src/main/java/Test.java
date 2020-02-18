import com.arcsoft.face.FaceFeature;
import com.arcsoft.face.FaceSimilar;
import com.light.graduation.face.faceserviceimpl.FaceServiceImpl;
import com.light.graduation.utils.ImageConvertUtils;

import static com.arcsoft.face.toolkit.ImageFactory.getRGBData;

/**
 * @Author: Light
 * @Date 2020/1/14 9:29
 */
public class Test {
	public static void main ( String[] args ) {
		FaceServiceImpl faceServiceImpl01 = new FaceServiceImpl ( );
		String imgStr01 = ImageConvertUtils.imageToBase64 ( "img\\潘光健01.jpg" );
		faceServiceImpl01.setImgStr ( imgStr01 );
		System.out.println ( faceServiceImpl01.getFaceInfo (   ) );
		FaceFeature faceFeature01 = faceServiceImpl01.getFaceFeature (   );
		FaceServiceImpl faceServiceImpl02 = new FaceServiceImpl ( );
		String imgStr02 = ImageConvertUtils.imageToBase64 ( "img\\潘光健02.jpg" );
		faceServiceImpl02.setImgStr ( imgStr02 );
		FaceFeature faceFeature02 = faceServiceImpl02.getFaceFeature (   );
		boolean faceCheck = faceServiceImpl01.faceCompare ( faceFeature02 , new FaceSimilar ( ) );
		System.out.println ( faceCheck );
	}
}
