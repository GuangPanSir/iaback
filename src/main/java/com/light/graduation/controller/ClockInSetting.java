package com.light.graduation.controller;

import com.light.graduation.dto.StringDTO;
import com.light.graduation.entity.LoginRecord;
import com.light.graduation.pojo.TeacherClockSettingPojo;
import com.light.graduation.service.projectservice.ProjectService;
import com.light.graduation.service.teacherservice.TeacherService;
import com.light.graduation.utils.DateFormatUtil;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Light
 * @Date 2020/3/4 17:27
 */
@Controller
@RequestMapping("clock")
public class ClockInSetting {
	private final ProjectService projectService;
	private final TeacherService teacherService;
	
	@Contract( pure = true )
	public ClockInSetting ( ProjectService projectService , TeacherService teacherService ) {
		this.projectService = projectService;
		this.teacherService = teacherService;
	}
	
	@RequestMapping("clockSelect/major")
	@ResponseBody
	public List<String> getAllMajors(){
		return this.projectService.getAllMajors ( );
	}
	
	@RequestMapping("clockSelect/project")
	@ResponseBody
	public List<String> getAllProjects ( @RequestBody StringDTO major ){
		System.out.println ( major );
		return this.projectService.getAllProjects ( major.getData () );
	}
	
	@RequestMapping("clockSelect/teacher")
	@ResponseBody
	public List<String> getAllTeachers ( @RequestBody StringDTO project ){
		System.out.println ( project );
		return this.projectService.getAllTeachers ( project.getData () );
	}
	
	/**
	 * 用于将教师对于签到选项的设置添加进数据库
	 * 本部分具体实施起来我也用了很麻烦的方法，下面面具体来说说
	 *
	 * 首先是函数参数Pojo对象，用于接收前端post携带的数据，其成员变量全部为String
	 * 由于我们要用到Date类型的数据，所以又新建了一个中间对象，用于将Pojo的String对象转换为我们想要的对象DTO
	 * 由于添加进数据库的对象我LoginRecord，所以我们还要将DTO对象转化为数据库操作的对象
	 *
	 * 数据库添加完成之后发现问题，数据库的时间总是比我们插入的时间要少8个小时
	 * 一开始以为是数据处理是出现了问题，后来调试时发现，预编译时的数据是正确的
	 * 所以就觉得可能是时区的问题，后来修改了相关参数，perfect！
	 *
	 * @param teacherClockSettingPojo 前端发来的教师的设置项
	 * @return 返回后端的操作状态
	 * @throws ParseException 格式转化出错
	 */
	@RequestMapping("clockSelect/teacherSetting")
	@ResponseBody
	public Map<String, Object> teacherClockSetting ( @NotNull HttpSession session, @RequestBody TeacherClockSettingPojo teacherClockSettingPojo ) throws ParseException {
		System.out.println ( teacherClockSettingPojo );
		
		Integer deadLine = Integer.valueOf ( teacherClockSettingPojo.getDeadLine ( ) );
		Double locationDifference = Double.valueOf ( teacherClockSettingPojo.getDistance ( ) );
		Date teacherSetLastTime = new Date(Long.parseLong ( teacherClockSettingPojo.getLastTime () ));
		
		teacherSetLastTime = new DateFormatUtil ( ).covertTime ( teacherSetLastTime );
		
		String teacherNumber = ( String ) session.getAttribute ( "userName" );
		
		LoginRecord loginRecord = new LoginRecord ( );
		
		loginRecord.setClockInMajor ( teacherClockSettingPojo.getMajor ());
		loginRecord.setClockInProject ( teacherClockSettingPojo.getProject ());
		loginRecord.setTeacherNumber ( teacherNumber);
		loginRecord.setClockInEndTime ( teacherSetLastTime);
		loginRecord.setClockInAbsentTime ( new Date ( teacherSetLastTime.getTime () + 1000 * 60 * deadLine ) );
		loginRecord.setLocationDifference ( locationDifference );
		
		loginRecord.setClockInStartTime ( new Date (  ) );
		
		System.out.println ( loginRecord );
		
		int flag = this.teacherService.teacherClockSetting ( loginRecord );
		HashMap< String, Object > map = new HashMap<> ( 15 );
		map.put ( "isOK" , flag == 1 );
		return map;
		
	}
}
