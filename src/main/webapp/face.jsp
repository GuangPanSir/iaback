<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<title>智慧校园</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no"/>
	<script type="text/javascript" src="./js/jquery-3.4.1.js"></script>
	<script type="text/javascript" src="./js/TransformPicture.js"></script>
	<!--<script type="text/javascript"
	        src="http://api.map.baidu.com/api?v=2.0&ak=3wjxAPfp3FZZZENBASO8VKfoCTKAuv6P"></script>-->
	<style type="text/css">
		body, html, #allmap {
			width: 100%;
			height: 800px;;
			overflow: hidden;
			margin: 0;
		}
		
		#golist {
			display: none;
		}
		
		@media (max-device-width: 780px) {
			#golist {
				display: block !important;
			}
		}
		
		.container {
			width: 1080px;
			margin: 0 auto;
		}
		
		.title {
			width: 100%;
			text-align: center;
		}
		
		h1 {
			color: #2280E8;
		}
	</style>
</head>
<body>
<div id="baidu_geo"></div>
<!--<div class="content">-->
<!--	<div id="allmap"></div>-->
<!--</div>-->
<p align="center">
	<button id="open">开启摄像头</button>
	<button id="close">关闭摄像头</button>
	<button id="CatchCode">拍照</button>
</p>
<div align="center" style="float: left;">
	<video id="video" width="800px" height="800px" autoplay></video>
	<canvas hidden="hidden" id="canvas" width="626" height="800"></canvas>
</div>
<!--<script type="text/javascript" src="js/Location.js"></script>-->

</body>
</html>
