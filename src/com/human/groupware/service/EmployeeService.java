package com.human.groupware.service;

import java.util.List;

import com.human.groupware.dto.Employee;

/**
 * 직원 관리 비즈니스 로직을 처리하는 서비스 인터페이스
 * - 컨트롤러(Main)와 DAO 사이의 중간 계층 역할을 합니다.
 * - 각 기능에 대한 메소드를 정의합니다.
 */
public interface EmployeeService {

    /**
     * 전체 직원 목록을 조회합니다.
     * @return 직원 정보 목록
     */
    List<Employee> selectAll();

    /**
     * 직원 번호로 특정 직원 정보를 조회합니다.
     * @param no 조회할 직원 번호
     * @return 조회된 직원 정보 객체, 없으면 null
     */
    Employee select(int no);

    /**
     * 새로운 직원 정보를 등록합니다. (관리자용)
     * @param employee 등록할 직원 정보
     * @return 적용된 행의 수 (성공: 1, 실패: 0)
     */
    int insert(Employee employee);

    /**
     * 기존 직원 정보를 수정합니다.
     * @param employee 수정할 정보가 담긴 직원 객체
     * @return 적용된 행의 수 (성공: 1, 실패: 0)
     */
    int update(Employee employee);

    /**
     * 직원 번호로 특정 직원 정보를 삭제합니다.
     * @param no 삭제할 직원 번호
     * @return 적용된 행의 수 (성공: 1, 실패: 0)
     */
    int delete(int no);

    /**
     * 사용자 아이디로 직원 정보를 조회합니다. (주로 로그인 시 내부적으로 사용)
     * @param userId 조회할 사용자 아이디
     * @return 조회된 직원 정보 객체, 없으면 null
     */
    Employee selectById(String userId);

    /**
     * 아이디 중복 여부를 확인합니다. (회원가입 시 사용)
     * @param userId 중복 확인할 사용자 아이디
     * @return 중복 시 true, 아니면 false
     */
    boolean isDuplicateId(String userId);

    /**
     * 로그인을 처리합니다.
     * @param userId 사용자 아이디
     * @param password 사용자 비밀번호
     * @return 로그인 성공 시 해당 Employee 객체, 실패 시 null
     */
    Employee login(String userId, String password);

    /**
     * 새로운 계정을 등록합니다. (회원가입)
     * @param employee 등록할 사용자 정보 (아이디/비밀번호 포함)
     * @return 적용된 행의 수 (성공: 1, 실패: 0)
     */
    int register(Employee employee);
}
