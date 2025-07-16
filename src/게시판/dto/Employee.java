package 게시판.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 직원 정보 DTO (Data Transfer Object) 클래스
 * - 데이터베이스의 'employee' 테이블과 매핑됩니다.
 * - Lombok 라이브러리를 사용하여 getter, setter, 생성자 등을 자동으로 생성합니다.
 */
@Data 
@AllArgsConstructor 
@NoArgsConstructor  
public class Employee {

    private int bno;            // 직원 번호 (Primary Key)
    private String name;        // 이름
    private String department;  // 부서
    private String bposition;   // 직급
    private String userId;      // 로그인 아이디 (Unique)
    private String password;    // 로그인 비밀번호
    private Date hireDate;      // 입사일자
    private Date updDate;       // 마지막 수정일자


   

    /**
     * 회원가입 시 사용하는 생성자 (이름, 부서, 직급, 아이디, 비밀번호)
     * @param name
     * @param department
     * @param bposition
     * @param userId
     * @param password
     */
    public Employee(String name, String department, String bposition, String userId, String password) {
        this.name = name;
        this.department = department;
        this.bposition = bposition;
        this.userId = userId;
        this.password = password;
    }

    /**
     * 직원 등록시 사용하는 생성자 (이름, 부서, 직급, 아이디, 비밀번호, 입사일)
     * @param name
     * @param department
     * @param bposition
     * @param userId
     * @param password
     */
	public Employee(String name, String department, String bposition, String userId, String password, Date hireDate) {
		this.name = name;
		this.department = department;
		this.bposition = bposition;
		this.userId = userId;
		this.password = password;
		this.hireDate = hireDate;
	}
}
