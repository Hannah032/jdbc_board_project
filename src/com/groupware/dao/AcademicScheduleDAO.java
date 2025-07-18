package com.groupware.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.groupware.dto.AcademicSchedule;
import com.groupware.dto.Board;
import com.groupware.service.AcademicScheduleService;

public class AcademicScheduleDAO extends JDBConnection {

    public List<AcademicSchedule> list() {
        List<AcademicSchedule> scheduleList = new ArrayList<>();
        String sql = "SELECT * FROM academic_schedule ORDER BY start_date ASC";

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                AcademicSchedule schedule = new AcademicSchedule();
                schedule.setScheduleId(rs.getInt("schedule_id"));
                schedule.setTitle(rs.getString("title"));
                schedule.setStartDate(rs.getDate("start_date"));
                schedule.setEndDate(rs.getDate("end_date"));
                schedule.setDescription(rs.getString("description"));
                scheduleList.add(schedule);
            }
        } catch (SQLException e) {
            System.err.println("학사 일정 목록 조회 중 예외 발생");
            e.printStackTrace();
        }
        return scheduleList;
    }

	public int insert(AcademicSchedule academicSchedule) {
		int result = 0;
		
		String sql = "insert into academic_schedule (schedule_id, title, description, start_date, end_date)" +
					"values(SEQ_ACADEMIC_SCHEDULE.NEXTVAL, ?, ?, ?, ?)";
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, academicSchedule.getTitle());
			psmt.setString(2, academicSchedule.getDescription());
			psmt.setTimestamp(3, new java.sql.Timestamp(academicSchedule.getStartDate().getTime()));
			psmt.setTimestamp(4, new java.sql.Timestamp(academicSchedule.getEndDate().getTime()));
			result = psmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println("학사 일정 생성 시, 예외 발생");
			e.printStackTrace();
		}
		return result;
	}
    
	public int update(AcademicSchedule academicSchedule) {
		int result = 0; 		// 결과 : 적용된 데이터 개수
		
		String sql = " update academic_schedule "
					+ " \t\tset title = ? "
					+ " \t\t\t,description = ? "
					+ " \t\t\t,start_date = ? "
					+ " \t\t\t,end_date = ? "
					+ " where schedule_id = ? ";		
		try {
			psmt = getValidConnection().prepareStatement(sql); 		// 쿼리 실행 객체 생성
			psmt.setString(1, academicSchedule.getTitle());
			psmt.setString(2, academicSchedule.getDescription());	// 2번
			psmt.setDate(3, new java.sql.Date(academicSchedule.getStartDate().getTime()));	// 3번
			psmt.setDate(4, new java.sql.Date(academicSchedule.getEndDate().getTime()));	// 4번 
			psmt.setInt(5, academicSchedule.getScheduleId());
			result = psmt.executeUpdate();
			// * executeUpdate()
			// SQL(INSERT, UPDATE, DELETE) 실행 시 적용된 데이터 개수를 int 타입으로 받아온다.
			// ex) 게시글 1개 적용 성공 시, result : 1
			//					실패 시, result : 0
		} catch (SQLException e) {
			System.err.println("게시글 수정 시, 예외 발생");
			e.printStackTrace();
		}
		return result;
	}
    
	public int delete(int scheduleId) {
		int result = 0; 		// 결과 : 적용된 데이터 개수
		
		String sql = " delete from academic_schedule "
					+ " where scheduleId = ? ";	
		try {
			psmt = getValidConnection().prepareStatement(sql); 
			psmt.setInt(1, scheduleId);
			result = psmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println("게시글 삭제 시, 예외 발생");
			e.printStackTrace();
		}
		return result;
	}

    
}
