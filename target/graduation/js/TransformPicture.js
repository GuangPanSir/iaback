var video;//视频流对象
var context;//绘制对象
var canvas;//画布对象
$(function() {
    var flag = false;
    //开启摄像头
    $("#open").click(function() {
        //判断摄像头是否打开
        if (!flag) {
            //调用摄像头初始化
            open();
            flag = true;
        }
    });
    //关闭摄像头
    $("#close").click(function() {
        //判断摄像头是否打开
        if (flag) {
            video.srcObject.getTracks()[0].stop();
            flag = false;
        }
    });
    //拍照
    $("#CatchCode").click(function() {
        if (flag) {
            context.drawImage(video, 0, 0, 330, 250);
            CatchCode();
        } else
            alert("请先开启摄像头!");
    });
});
//将当前图像传输到后台
function CatchCode() {
    //获取图像
    var img = getBase64();
    //Ajax将Base64字符串传输到后台处理
    $.ajax({
        type : "POST",
        async : false,
        url : "/Parking/faceLogin/searchFace",
        data : {
            img : img
        },
        dataType : "JSON",
        success : function(data) {
            //返回的结果
        }
    });
};
//开启摄像头
function open() {
    //获取摄像头对象
    canvas = document.getElementById("canvas");
    context = canvas.getContext("2d");
    //获取视频流
    video = document.getElementById("video");
    var videoObj = {
        "video" : true
    }, errBack = function(error) {
        console.log("Video capture error: ", error.code);
    };
    context.drawImage(video, 0, 0, 330, 250);
    //初始化摄像头参数
    if (navigator.getUserMedia || navigator.webkitGetUserMedia
        || navigator.mozGetUserMedia) {
        navigator.getUserMedia = navigator.getUserMedia
            || navigator.webkitGetUserMedia
            || navigator.mozGetUserMedia;
        navigator.getUserMedia(videoObj, function(stream) {
            video.srcObject = stream;
            video.play();
        }, errBack);
    }
}
//将摄像头拍取的图片转换为Base64格式字符串
function getBase64() {
    //获取当前图像并转换为Base64的字符串
    var imgSrc = canvas.toDataURL("image/png");
    //截取字符串
    return imgSrc.substring(22);
};