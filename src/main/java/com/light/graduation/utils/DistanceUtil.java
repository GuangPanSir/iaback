package com.light.graduation.utils;

/**
 * @Author: Light
 * @Date 2020/3/16 11:07
 */
public class DistanceUtil {
	/**
	 * 平均半径,单位：m
	 */
	private static final double EARTH_RADIUS = 6371393;
	
	
	public static double getDistance ( double teacherLng , double teacherLat , double studentLng , double studentLat ) {
		// 经纬度（角度）转弧度。弧度用作参数，以调用Math.cos和Math.sin
		double radiansAX = Math.toRadians ( teacherLng );
		double radiansAY = Math.toRadians ( teacherLat );
		double radiansBX = Math.toRadians ( studentLng );
		double radiansBY = Math.toRadians ( studentLat );
		
		// 公式中“cosβ1cosβ2cos（α1-α2）+sinβ1sinβ2”的部分，得到∠AOB的cos值
		double cos = Math.cos ( radiansAY ) * Math.cos ( radiansBY ) * Math.cos ( radiansAX - radiansBX )
			+ Math.sin ( radiansAY ) * Math.sin ( radiansBY );
		double acos = Math.acos ( cos );
		return EARTH_RADIUS * acos; // 最终结果
	}
}
