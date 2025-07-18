package com.groupware.service;

import java.util.List;
import com.groupware.dao.AcademicScheduleDAO;
import com.groupware.dto.AcademicSchedule;

public class AcademicScheduleServiceImpl implements AcademicScheduleService {

    private AcademicScheduleDAO academicScheduleDAO = new AcademicScheduleDAO();

    @Override
    public List<AcademicSchedule> list() {
        return academicScheduleDAO.list();
    }

	@Override
	public int insert(AcademicSchedule academicSchedule) {
		int result = academicScheduleDAO.insert(academicSchedule);
		
		if( result > 0 ) System.out.println("데이터 등록 성골");
		else System.out.println("데이터 등록 실패!");
		return result;
	}

	@Override
	public int update(AcademicSchedule academicSchedule) {
		int result = academicScheduleDAO.update(academicSchedule);
		// - result(결과) :    0 -> 데이터 수정 실패
		// 				 :    1 -> 데이터 수정 성공
		if( result > 0 ) System.out.println("데이터 수정 성공!");
		else System.out.println("데이터 수정 실패!");
		return result;
	}

	@Override
	public int delete(int scheduleId) {
		int result = academicScheduleDAO.delete(scheduleId);
		
		
		return 0;
	}
}
