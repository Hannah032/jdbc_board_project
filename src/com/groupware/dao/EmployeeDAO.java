package com.groupware.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import com.groupware.dto.Employee;

/**
 * 데이터 접근 객체 (DAO, Data Access Object)
 * - 데이터베이스의 employee 테이블에 직접 접근하여 CRUD(Create, Read, Update, Delete) 작업을 수행합니다.
 */
public class EmployeeDAO extends JDBConnection {
    // DB 연결 및 쿼리 실행을 위한 필드 
    

    /**
     * 전체 직원 정보를 조회합니다.
     * @return 직원 정보 목록 (List<Employee>)
     */
    public List<Employee> list() {
        List<Employee> employeeList = new ArrayList<>();
        String sql = "SELECT * FROM employee";

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Employee employee = new Employee();
                // ResultSet에서 각 컬럼 값을 가져와 Employee 객체에 설정
                employee.setBno(rs.getInt("bno"));
                employee.setName(rs.getString("name"));
                employee.setDepartment(rs.getString("department"));
                employee.setBposition(rs.getString("bposition"));
                employee.setUserId(rs.getString("user_id"));
                employee.setPassword(rs.getString("password"));
                employee.setHireDate(rs.getTimestamp("hire_date"));
                employee.setUpdDate(rs.getTimestamp("upd_date"));

                employeeList.add(employee);
            }
        } catch (SQLException e) {
            System.err.println("직원 목록 조회 중 예외 발생");
            e.printStackTrace();
        }
        return employeeList;
    }

    /**
     * 직원 번호(bno)에 해당하는 직원 정보를 조회합니다.
     * @param bno 조회할 직원 번호
     * @return 조회된 직원 정보 객체, 없으면 null 반환
     */
    public Employee select(int bno) {
        Employee employee = null; // 조회 결과가 없으면 null을 반환하도록 초기화
        String sql = "SELECT * FROM employee WHERE bno = ?";

        try {
            psmt = con.prepareStatement(sql);
            psmt.setInt(1, bno); // 첫 번째 '?'에 직원 번호 바인딩
            rs = psmt.executeQuery();

            if (rs.next()) {
                employee = new Employee();
                // ResultSet에서 각 컬럼 값을 가져와 Employee 객체에 설정
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
        }
        return employee;
    }

    /**
     * 새로운 직원 정보를 데이터베이스에 추가합니다.
     * @param employee 추가할 직원 정보 객체
     * @return 적용된 행의 수 (성공 시 1, 실패 시 0)
     */
    public int insert(Employee employee) {
        int result = 0;
        // Oracle의 SEQ_EMPLOYEE 시퀀스를 사용하여 bno 자동 생성
        String sql = "INSERT INTO employee (bno, name, department, bposition, user_id, password, hire_date) "
                   + "VALUES (SEQ_EMPLOYEE.NEXTVAL, ?, ?, ?, ?, ?, ?)";

        try {
            psmt = con.prepareStatement(sql);
            psmt.setString(1, employee.getName());
            psmt.setString(2, employee.getDepartment());
            psmt.setString(3, employee.getBposition());
            psmt.setString(4, employee.getUserId());
            psmt.setString(5, employee.getPassword());
            // java.util.Date를 java.sql.Timestamp로 변환하여 설정
            psmt.setTimestamp(6, new Timestamp(employee.getHireDate().getTime()));

            result = psmt.executeUpdate(); // INSERT, UPDATE, DELETE 실행 시 사용
        } catch (SQLException e) {
            System.err.println("직원 정보 추가 중 예외 발생");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 기존 직원 정보를 수정합니다.
     * @param employee 수정할 정보가 담긴 직원 객체
     * @return 적용된 행의 수 (성공 시 1, 실패 시 0)
     */
    public int update(Employee employee) {
        int result = 0;
        String sql = "UPDATE employee "
                   + "SET name = ?, department = ?, bposition = ?, password = ?, upd_date = SYSTIMESTAMP "
                   + "WHERE bno = ?";

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
        }
        return result;
    }

    /**
     * 직원 번호(bno)에 해당하는 직원 정보를 삭제합니다.
     * @param bno 삭제할 직원 번호
     * @return 적용된 행의 수 (성공 시 1, 실패 시 0)
     */
    public int delete(int bno) {
        int result = 0;
        String sql = "DELETE FROM employee WHERE bno = ?";

        try {
            psmt = con.prepareStatement(sql);
            psmt.setInt(1, bno);
            result = psmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("직원 정보 삭제 중 예외 발생");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 사용자 아이디(userId)로 직원 정보를 조회합니다. (로그인 처리 시 사용)
     * @param userId 조회할 사용자 아이디
     * @return 조회된 직원 정보 객체, 없으면 null 반환
     */
    public Employee selectById(String userId) {
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
            System.err.println("아이디로 직원 조회 중 예외 발생");
            e.printStackTrace();
        }
        return employee;
    }

    /**
     * 회원가입 시, 사용자 아이디(userId)의 중복 여부를 확인합니다.
     * @param userId 중복 확인할 사용자 아이디
     * @return 중복 시 true, 중복이 아닐 시 false
     */
    public boolean isDuplicateId(String userId) {
        boolean isDuplicate = false;
        String sql = "SELECT COUNT(*) FROM employee WHERE user_id = ?";

        try {
            psmt = con.prepareStatement(sql);
            psmt.setString(1, userId);
            rs = psmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1); // COUNT(*) 결과는 첫 번째 컬럼에 있음
                isDuplicate = (count > 0);
            }
        } catch (SQLException e) {
            System.err.println("아이디 중복 확인 중 예외 발생");
            e.printStackTrace();
        }
        return isDuplicate;
    }
}
