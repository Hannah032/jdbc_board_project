package com.groupware.service;

import java.util.List;
import com.groupware.dto.AcademicSchedule;
import com.groupware.dto.Board;

public interface AcademicScheduleService {
    List<AcademicSchedule> list();
    
    int insert(AcademicSchedule academicSchedule);
    
    //학사일정 수정
  	int update(AcademicSchedule academicSchedule);
  	
  	//학사일정 삭제
  	int delete(int scheduleId);
}
