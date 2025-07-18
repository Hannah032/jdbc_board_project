package com.groupware.service;

import java.util.List;
import com.groupware.dao.EmployeeDAO;
import com.groupware.dto.Employee;

/**
 * EmployeeService 인터페이스를 구현한 서비스 클래스
 * - 비즈니스 로직의 실제 구현 내용을 담고 있습니다.
 * - DAO 객체를 사용하여 데이터베이스 작업을 수행합니다.
 */
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeDAO employeeDAO = new EmployeeDAO();

    /**
     * 전체 직원 목록을 조회합니다.
     * @return 직원 정보 목록
     */
    @Override
    public List<Employee> list() {
        System.out.println("서비스: 전체 직원 목록을 조회합니다.");
        List<Employee> employeeList = employeeDAO.list();
        return employeeList;
    }

    /**
     * 직원 번호로 특정 직원 정보를 조회합니다.
     * @param bno 조회할 직원 번호
     * @return 조회된 직원 정보 객체, 없으면 null
     */
    @Override
    public Employee select(int bno) {
        System.out.println("서비스: 직원 번호(" + bno + ")로 정보를 조회합니다.");
        Employee employee = employeeDAO.select(bno);
        return employee;
    }

    /**
     * 새로운 직원 정보를 등록합니다. (관리자용)
     * @param employee 등록할 직원 정보
     * @return 적용된 행의 수 (성공: 1, 실패: 0)
     */
    @Override
    public int insert(Employee employee) {
        System.out.println("서비스: 새로운 직원 정보를 등록합니다.");
        int result = employeeDAO.insert(employee);
        if (result > 0) {
            System.out.println("DB에 데이터가 성공적으로 등록되었습니다.");
        } else {
            System.out.println("DB 데이터 등록에 실패하였습니다.");
        }
        return result;
    }

    /**
     * 기존 직원 정보를 수정합니다.
     * @param employee 수정할 정보가 담긴 직원 객체
     * @return 적용된 행의 수 (성공: 1, 실패: 0)
     */
    @Override
    public int update(Employee employee) {
        System.out.println("서비스: 직원 번호(" + employee.getBno() + ")의 정보를 수정합니다.");
        int result = employeeDAO.update(employee);
        if (result > 0) {
            System.out.println("DB 데이터가 성공적으로 수정되었습니다.");
        } else {
            System.out.println("DB 데이터 수정에 실패하였습니다.");
        }
        return result;
    }

    /**
     * 직원 번호로 특정 직원 정보를 삭제합니다.
     * @param no 삭제할 직원 번호
     * @return 적용된 행의 수 (성공: 1, 실패: 0)
     */
    @Override
    public int delete(int no) {
        System.out.println("서비스: 직원 번호(" + no + ")의 정보를 삭제합니다.");
        int result = employeeDAO.delete(no);
        if (result > 0) {
            System.out.println("DB 데이터가 성공적으로 삭제되었습니다.");
        } else {
            System.out.println("DB 데이터 삭제에 실패하였습니다.");
        }
        return result;
    }

    /**
     * 사용자 아이디로 직원 정보를 조회합니다.
     * @param userId 조회할 사용자 아이디
     * @return 조회된 직원 정보 객체, 없으면 null
     */
    @Override
    public Employee selectById(String userId) {
        System.out.println("서비스: 사용자 아이디(" + userId + ")로 정보를 조회합니다.");
        return employeeDAO.selectById(userId);
    }

    /**
     * 아이디 중복 여부를 확인합니다.
     * @param userId 중복 확인할 사용자 아이디
     * @return 중복 시 true, 아니면 false
     */
    @Override
    public boolean isDuplicateId(String userId) {
        System.out.println("서비스: 사용자 아이디(" + userId + ")의 중복 여부를 확인합니다.");
        return employeeDAO.isDuplicateId(userId);
    }

    /**
     * 로그인을 처리합니다.
     * @param userId 사용자 아이디
     * @param password 사용자 비밀번호
     * @return 로그인 성공 시 해당 Employee 객체, 실패 시 null
     */
    @Override
    public Employee login(String userId, String password) {
        System.out.println("서비스: 로그인 처리를 시작합니다. (ID: " + userId + ")");
        // 1. 아이디로 직원 정보 조회
        Employee employee = employeeDAO.selectById(userId);

        // 2. 직원 정보가 없는 경우 (아이디가 존재하지 않음)
        if (employee == null) {
            System.err.println("로그인 실패: 존재하지 않는 아이디입니다.");
            return null;
        }

        // 3. 비밀번호를 비교
        // employee.getPassword() : DB에 저장된 비밀번호
        // password             : 사용자가 입력한 비밀번호
        if (employee.getPassword().equals(password)) {
            System.out.println("로그인 성공!");
            return employee; // 아이디와 비밀번호가 모두 일치하면 직원 정보 반환
        }

        // 4. 비밀번호가 일치하지 않는 경우
        System.err.println("로그인 실패: 비밀번호가 일치하지 않습니다.");
        return null;
    }

    /**
     * 새로운 계정을 등록합니다. (회원가입)
     * @param employee 등록할 사용자 정보 (아이디/비밀번호 포함)
     * @return 적용된 행의 수 (성공: 1, 실패: 0, 아이디 중복: -1)
     */
    @Override
    public int register(Employee employee) {
        System.out.println("서비스: 회원가입 처리를 시작합니다. (ID: " + employee.getUserId() + ")");
        // 1. 아이디 중복 확인
        if (employeeDAO.isDuplicateId(employee.getUserId())) {
            System.err.println("회원가입 실패: 이미 존재하는 아이디입니다.");
            return -1; // 아이디가 중복되면 -1 반환
        }

        // 2. 중복되지 않았으면 DB에 정보 등록
        System.out.println("사용 가능한 아이디입니다. DB에 등록을 시도합니다.");
        return employeeDAO.insert(employee);
    }
}
