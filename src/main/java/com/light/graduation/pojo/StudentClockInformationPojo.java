package com.light.graduation.pojo;

import com.light.graduation.dto.StudentClockInformationDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: Light
 * @Date 2020/3/3 19:11
 */
@Data
@NoArgsConstructor
public class StudentClockInformationPojo {
	private List< StudentClockInformationDTO > list;
}
