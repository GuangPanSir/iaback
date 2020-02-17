function getLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(showPosition, showError);
    } else {
        alert("浏览器不支持地理定位。");
    }
}

getLocation();

function showError(error) {
    switch (error.code) {
        case error.PERMISSION_DENIED:
            alert("定位失败,用户拒绝请求地理定位");
            break;
        case error.POSITION_UNAVAILABLE:
            alert("定位失败,位置信息是不可用");
            break;
        case error.TIMEOUT:
            alert("定位失败,请求获取用户位置超时");
            break;
        case error.UNKNOWN_ERROR:
            alert("定位失败,定位系统失效");
            break;
    }
};

function showPosition(position) {
    var latlon = position.coords.latitude + ',' + position.coords.longitude;
    console.log(latlon);
    console.log("原始位置精度：" + position.coords.accuracy);
    //baidu
    var url = "http://api.map.baidu.com/geocoder/v2/?ak=hCBcm8H8opRLdC0f6OibbGavC0pne1uc&callback=renderReverse&location=" + latlon + "&output=json&pois=0";
    $.ajax({
        type: "GET",
        dataType: "jsonp",
        url: url,
        beforeSend: function () {
            $("#baidu_geo").html('正在定位...');
        },
        success: function (json) {
            console.log(json);
            $("#baidu_geo").html(json.result.formatted_address);
            var ggPoint = new BMap.Point(json.result.location.lng, json.result.location.lat);//创建标点
            //地图初始化
            var bm = new BMap.Map("allmap");
            bm.centerAndZoom(ggPoint, 15);
            bm.addControl(new BMap.NavigationControl());//控件

            //添加gps marker和label
            var markergg = new BMap.Marker(ggPoint);
            bm.addOverlay(markergg); //添加GPS marker
            var labelgg = new BMap.Label("未转换的原始坐标（错误）", {offset: new BMap.Size(20, -10)});
            markergg.setLabel(labelgg); //添加GPS label


            //坐标转换完之后的回调函数
            translateCallback = function (data) {
                if (data.status === 0) {
                    var marker = new BMap.Marker(data.points[0]);
                    bm.addOverlay(marker);
                    var label = new BMap.Label("转换后的百度坐标（正确）", {offset: new BMap.Size(20, -10)});
                    marker.setLabel(label); //添加百度label
                    bm.setCenter(data.points[0]);
                }
            }
            var convertor = new BMap.Convertor();//这个类就是转换的对象
            var pointArr = [];
            pointArr.push(ggPoint);
            convertor.translate(pointArr, 1, 5, translateCallback)//通过调用回调函数来进行转换。
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            $("#baidu_geo").html(latlon + "地址位置获取失败");
        }
    });
};
