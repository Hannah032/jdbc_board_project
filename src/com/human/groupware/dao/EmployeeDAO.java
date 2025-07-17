package com.human.groupware.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.human.groupware.dto.Employee;

public class EmployeeDAO extends JDBConnection {

    /**
     * 모든 직원 정보를 조회합니다.
     * @return 직원 목록
     */
    public List<Employee> selectAll() {
        List<Employee> employeeList = new ArrayList<>();
        String sql = "SELECT * FROM employee ORDER BY bno DESC";
        
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Employee employee = new Employee();
                employee.setBno(rs.getInt("bno"));
                employee.setName(rs.getString("name"));
                employee.setDepartment(rs.getString("department"));
                employee.setBposition(rs.getString("bposition"));
                employee.setHireDate(rs.getTimestamp("hire_date"));
                employee.setUpdDate(rs.getTimestamp("upd_date"));
                employeeList.add(employee);
            }
        } catch (SQLException e) {
            System.err.println("직원 목록 조회 중 예외 발생");
            e.printStackTrace();
        } finally {
            close();
        }
        return employeeList;
    }

    /**
     * 특정 직원 정보를 조회합니다.
     * @param no 직원 번호
     * @return 조회된 직원 정보
     */
    public Employee select(int no) {
        Employee employee = null;
        String sql = "SELECT * FROM employee WHERE bno = ?";

        try {
            psmt = con.prepareStatement(sql);
            psmt.setInt(1, no);
            rs = psmt.executeQuery();

            if (rs.next()) {
                employee = new Employee();
                employee.setBno(rs.getInt("bno"));
                employee.setName(rs.getString("name"));
                employee.setDepartment(rs.getString("department"));
                employee.setBposition(rs.getString("bposition"));
                employee.setHireDate(rs.getTimestamp("hire_date"));
                employee.setUpdDate(rs.getTimestamp("upd_date"));
            }
        } catch (SQLException e) {
            System.err.println("직원 정보 조회 중 예외 발생");
            e.printStackTrace();
        } finally {
            close();
        }
        return employee;
    }

    /**
     * 새로운 직원을 등록합니다.
     * @param employee 등록할 직원 정보
     * @return 등록된 행의 수
     */
    public int insert(Employee employee) {
        int result = 0;
        String sql = "INSERT INTO employee (bno, name, department, bposition, user_id, password) VALUES (SEQ_EMPLOYEE.NEXTVAL, ?, ?, ?, ?, ?)";
    
        try {
            psmt = con.prepareStatement(sql);
            psmt.setString(1, employee.getName());
            psmt.setString(2, employee.getDepartment());
            psmt.setString(3, employee.getBposition());
            psmt.setString(4, employee.getUserId());  // 추가
            psmt.setString(5, employee.getPassword()); // 추가
    
            result = psmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("직원 등록 중 예외 발생");
            e.printStackTrace();
        } finally {
            close();
        }
        return result;
    }

    /**
     * 직원 정보를 수정합니다.
     * @param employee 수정할 직원 정보
     * @return 수정된 행의 수
     */
    public int update(Employee employee) {
        int result = 0;
        String sql = "UPDATE employee SET name = ?, department = ?, bposition = ?, password = ?, upd_date = SYSDATE WHERE bno = ?";

        try {
            psmt = con.prepareStatement(sql);
            psmt.setString(1, employee.getName());
            psmt.setString(2, employee.getDepartment());
            psmt.setString(3, employee.getBposition());
            psmt.setString(4, employee.getPassword());
            psmt.setInt(5, employee.getBno());

            result = psmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("직원 정보 수정 중 예외 발생");
            e.printStackTrace();
        } finally {
            close();
        }
        return result;
    }

    /**
     * 직원을 삭제합니다.
     * @param no 삭제할 직원 번호
     * @return 삭제된 행의 수
     */
    public int delete(int no) {
        int result = 0;
        String sql = "DELETE FROM employee WHERE bno = ?";

        try {
            psmt = con.prepareStatement(sql);
            psmt.setInt(1, no);

            result = psmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("직원 삭제 중 예외 발생");
            e.printStackTrace();
        } finally {
            close();
        }
        return result;
    }

    /**
     * 사용자 아이디로 직원 정보를 조회합니다.
     * @param userId 사용자 아이디
     * @return 조회된 직원 정보
     */
    public Employee selectById (String userId) {
        Employee employee = null;
        String sql = "SELECT * FROM employee WHERE user_id = ?";

        try {
            psmt = con.prepareStatement(sql);
            psmt.setString(1, userId);
            rs = psmt.executeQuery();

            if (rs.next()) {
                employee = new Employee();
                employee.setBno(rs.getInt("bno"));
                employee.setName(rs.getString("name"));
                employee.setDepartment(rs.getString("department"));
                employee.setBposition(rs.getString("bposition"));
                employee.setUserId(rs.getString("user_id"));
                employee.setPassword(rs.getString("password"));
                employee.setHireDate(rs.getTimestamp("hire_date"));
                employee.setUpdDate(rs.getTimestamp("upd_date"));
            }
        } catch (SQLException e) {
            System.err.println("직원 정보 조회 중 예외 발생");
            e.printStackTrace();
        } finally {
            close();
        }
        return employee;
    }

	public boolean isDuplicateId(String userId) {
		String sql = "SELECT COUNT(*) FROM employee WHERE user_id = ?";
		int count = 0;
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, userId);
			rs = psmt.executeQuery();
			
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.err.println("아이디 중복 확인 중 예외 발생");
			e.printStackTrace();
		} finally {
			close();
		}
		return count > 0; // 0보다 크면 true (아이디 중복)
	}
}
