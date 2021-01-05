package com.light.graduation.service.studentservice.impl;

import com.light.graduation.dao.AttendanceStatisticsDao;
import com.light.graduation.dao.QuoteDao;
import com.light.graduation.dao.StudentClockInDao;
import com.light.graduation.dao.StudentDao;
import com.light.graduation.dto.CheckLoginDTO;
import com.light.graduation.dto.CurrentTimeInformation;
import com.light.graduation.dto.SearchStudentDto;
import com.light.graduation.dto.StudentClockInformationDTO;
import com.light.graduation.entity.AttendanceStatistics;
import com.light.graduation.entity.MajorToTeacher;
import com.light.graduation.entity.Student;
import com.light.graduation.entity.StudentClockIn;
import com.light.graduation.pojo.CheckStudentClockSelectPojo;
import com.light.graduation.redis.UpdateUserToRedis;
import com.light.graduation.service.faceservice.impl.FaceServiceImpl;
import com.light.graduation.service.projectservice.ProjectService;
import com.light.graduation.service.studentservice.StudentService;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * @Author: Light
 * @Date 2020/2/19 14:20
 */
@Service( value = "studentServiceImpl" )
public class StudentServiceImpl implements StudentService {
	private final StudentClockInDao studentclockinDao;
	
	private final StudentDao studentDao;
	
	private final QuoteDao quoteDao;
	
	private final ProjectService projectService;
	
	private final AttendanceStatisticsDao attendanceStatisticsDao;
	
	private final UpdateUserToRedis updateUserToRedis;
	
	@Contract( pure = true )
	public StudentServiceImpl ( StudentDao studentDao , StudentClockInDao studentclockinDao , QuoteDao quoteDao , ProjectService projectService , AttendanceStatisticsDao attendanceStatisticsDao , UpdateUserToRedis updateUserToRedis ) {
		this.studentDao = studentDao;
		this.studentclockinDao = studentclockinDao;
		this.quoteDao = quoteDao;
		this.projectService = projectService;
		this.attendanceStatisticsDao = attendanceStatisticsDao;
		this.updateUserToRedis = updateUserToRedis;
	}
	
	@Override
	public List< Student > selectAllStudents ( ) {
		return this.studentDao.selectAllStudents ( );
	}
	
	@Override
	public boolean checkStudentLogin ( @NotNull CheckLoginDTO loginUser ) {
		String studentPassword = this.studentDao.getStudentPassword ( loginUser.getUserName ( ) );
		return loginUser.getUserPassword ( ).equals ( studentPassword );
	}
	
	@Override
	public List< StudentClockInformationDTO > getStudentClockInformation ( String studentNumber ) {
		return this.studentclockinDao.getStudentClockInformation ( studentNumber );
	}
	
	@Override
	public String getStudentMajor ( String studentNumber ) {
		return this.studentDao.getStudentMajor ( studentNumber );
	}
	
	@Override
	public String getQuote ( ) {
		return this.quoteDao.selectQuote ( );
	}
	
	@Override
	public void insertStudentClockIn ( StudentClockIn studentClockIn ) {
		this.studentclockinDao.insertSelective ( studentClockIn );
	}
	
	@Override
	public int checkStudentClock ( HttpSession session , String studentNumber ) {
		StudentClockIn middleStudentClockIn = this.studentclockinDao.getLastClockInformation ( studentNumber );
		if ( middleStudentClockIn == null ) {
			return 1;
		} else {
			long localTime = System.currentTimeMillis ( );
			long lastTime = middleStudentClockIn.getClockTime ( ).getTime ( );
			if ( localTime - lastTime <= 1000 * 60 * 20 ) {
				if ( middleStudentClockIn.getCertification () != null ){
					System.out.println ("请假不允许签到" );
					return 4;
				}else if ( "正常".equals ( middleStudentClockIn.getClockState ( ) ) ) {
					return 2;
				} else if ( this.projectService.checkStudentClockSelect (
					new CheckStudentClockSelectPojo ( ( String ) session.getAttribute ( "clockMajor" ) ,
						( String ) session.getAttribute ( "clockProject" ) ,
						( String ) session.getAttribute ( "clockTeacher" ) ) ) ) {
					return 0;
				} else{
					return 3;
				}
			} else {
				return 1;
			}
		}
	}
	
	@Override
	public void updateStudentClock ( HttpSession session , StudentClockIn studentClockIn ) {
		this.studentclockinDao.updateSelective ( studentClockIn );
	}
	
	@Override
	public String getStudentNameByStudentNumber ( String studentNumber ) {
		return this.studentDao.getStudentNameByStudentNumber ( studentNumber );
	}
	
	@Override
	public StudentClockIn getLastClockInformation ( String studentNumber ) {
		return this.studentclockinDao.getLastClockInformation ( studentNumber );
	}
	
	@Override
	public int checkStudentVacate ( String studentNumber ) {
		StudentClockIn studentClockIn = this.studentclockinDao.getLastClockInformation ( studentNumber );
		if ( studentClockIn == null ) {
			return 0;
		} else {
			long currentTime = System.currentTimeMillis ( );
			long lastTime = studentClockIn.getClockTime ( ).getTime ( );
			if ( currentTime - lastTime <= 1000 * 60 * 20 ) {
				return 1;
			} else {
				return 0;
			}
		}
	}
	
	@Override
	public SearchStudentDto searchStudent ( String studentNumber ) {
		SearchStudentDto student = new SearchStudentDto ( );
		//根据学号查询的学生信息
		SearchStudentDto student1 = this.studentDao.selectStudentByStudentNumber ( studentNumber );
		if(student1 != null){
			student = student1;
		}
		return student;
	}
	
	@Override
	public String getStudentFaceImage ( String studentNumber ) {
		return this.studentDao.getStudentFaceImage ( studentNumber );
	}
	
	@Override
	public Student getStudentInfo ( String studentNumber ) {
		return this.studentDao.selectStudentInfoByStudentNumber ( studentNumber );
	}
	
	@Override
	public Integer getStudentIsFirstLogin ( String studentNumber ) {
		return this.studentDao.getStudentIsFirstLogin ( studentNumber );
	}
	
	@Override
	public int updateStudentFace ( String studentNumber , String faceImg ) {
		FaceServiceImpl faceServiceImpl = new FaceServiceImpl ( faceImg );
		boolean isExistFace = faceServiceImpl.detectFaces ( );
		//存在人脸，执行插入操作
		if(isExistFace){
			this.studentDao.updateStudentFace ( studentNumber , faceImg );
			System.out.println ( "beforeSaveRedis" );
			updateUserToRedis.updateUserIsFirstLogin ( studentNumber );
			return 1;
		}else{
			return 0;
		}
	}
	
	@Override
	public void updateStudentSelective ( Student student ) {
		this.studentDao.updateStudentSelective ( student );
	}
	
	@Override
	public AttendanceStatistics getStudentProjectClockDetail ( String studentNumber , String clockProject ) {
		return this.attendanceStatisticsDao.getStudentProjectClockDetail ( studentNumber , clockProject );
	}
	
	@Override
	public int insertSelective ( AttendanceStatistics record ) {
		return this.attendanceStatisticsDao.insertSelective ( record );
	}
	
	@Override
	public void updateSelective ( AttendanceStatistics attendanceStatistics ) {
		this.attendanceStatisticsDao.updateSelective ( attendanceStatistics );
	}
	
	@Override
	public void updateStudentClockScore ( @NotNull AttendanceStatistics attendanceStatistics ) {
		int clockAbnormal = attendanceStatistics.getClockAbnormal ( );
		int clockOverdue = attendanceStatistics.getClockOverdue ( );
		int clockVacate = attendanceStatistics.getClockVacate ( );
		int clockAbsent = attendanceStatistics.getClockAbsent ( );
		int clockScore = attendanceStatistics.getClockScore ( );
		
		/*
		给出计算规则
		异常签到一次扣10分
		迟到扣五分
		请假一次扣5分
		缺勤扣20，累计缺勤三次直接计做0分
		 */
		if ( clockAbsent >= 3 ) {
			clockScore = 0;
		} else {
			clockScore = 100 - 10 * clockAbnormal - 5 * clockOverdue - 20 * clockAbsent - 5 * clockVacate;
		}
		attendanceStatistics.setClockScore ( clockScore );
		this.updateSelective ( attendanceStatistics );
		
	}
	
	@Override
	public List< MajorToTeacher > getMajorProject ( String major ) {
		return this.studentDao.getMajorProject ( major );
	}
	
	@Override
	public List< CurrentTimeInformation > getStudentProjectClockIn ( String studentNumber , String project ) {
		return this.studentclockinDao.getStudentProjectClockIn ( studentNumber , project );
	}
	
	
	@Override
	public StudentClockIn getProjectLastClockIn ( String studentNumber , Date clockTime ) {
		return this.studentclockinDao.getProjectLastClockIn ( studentNumber , clockTime );
	}
}
