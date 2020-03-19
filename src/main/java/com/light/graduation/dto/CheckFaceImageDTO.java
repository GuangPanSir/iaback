package com.light.graduation.dto;

import lombok.Data;

/**
 * @Author: Light
 * @Date 2020/2/29 17:10
 */
@Data
public class CheckFaceImageDTO {
	private String img;
	private Double lng;
	private Double lat;
	private String address;
	private Integer accuracy;
}
