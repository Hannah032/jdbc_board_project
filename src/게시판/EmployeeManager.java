package 게시판;

import java.util.List;
import java.util.Scanner;

import 게시판.dto.Employee;
import 게시판.service.EmployeeService;
import 게시판.service.EmployeeServiceImpl;

public class EmployeeManager { //회원가입, 로그인, 로그아웃
	private static Scanner scanner = new Scanner(System.in);
	private static EmployeeService employeeService = new EmployeeServiceImpl();
	
	// 현재 로그인한 직원 정보
	private static Employee currentEmployee = null;

	public static void main(String[] args) {
		int menuNo = 0;
		do {
			employeeMenu();
			menuNo = scanner.nextInt();
			scanner.nextLine();

			if (menuNo == 0) break;

			switch (menuNo) {
				case 1: register(); break;
				case 2: login(); break;
				case 3: logout(); break;
				case 4: viewMyInfo(); break;
				case 5: updateMyInfo(); break;
				case 6: deleteAccount(); break;
				default: System.out.println("잘못된 번호입니다."); break;
			}
		} while (menuNo != 0);
	}
	
	/**
	 * 직원 관리 메뉴
	 */
	public static void employeeMenu() {
		System.out.println(":::::::::::::::: 회원 정보 관리 ::::::::::::::::");
		// 현재 로그인한 사용자 정보 가져오기
				Employee currentEmployee = EmployeeManager.getCurrentEmployee();
				if (currentEmployee != null && currentEmployee.getUserId() != null) {
					System.out.println("사용자 이름 : " + currentEmployee.getUserId());
				}
		System.out.println("1. 계정 등록");
		System.out.println("2. 로그인");
		System.out.println("3. 로그아웃");
		System.out.println("4. 내 정보 조회");
		System.out.println("5. 내 정보 수정");
		System.out.println("6. 계정 삭제");
		System.out.println("0. 이전 메뉴로");
		System.out.print("::::::::: 번호 입력 : ");
	}
	
	/**
	 * 계정 등록
	 */
	public static void register() {
		System.out.println("::::::::::::::::: 계정 등록 :::::::::::::::::::");
		
		System.out.print("※ 이름 : ");
		String name = scanner.nextLine();
		
		System.out.print("※ 부서 : ");
		String department = scanner.nextLine();
		
		System.out.print("※ 직급 : ");
		String position = scanner.nextLine();
		
		System.out.print("※ 아이디 : ");
		String userId = scanner.nextLine();
		
		// 아이디 중복 확인
		if (employeeService.isDuplicateId(userId)) {
			System.out.println("이미 사용 중인 아이디입니다.");
			return;
		}
		
		System.out.print("※ 비밀번호 : ");
		String password = scanner.nextLine();
		
		Employee employee = new Employee(name, department, position, userId, password);
		
		int result = employeeService.register(employee);
		if (result > 0) {
			System.out.println("계정 등록이 완료되었습니다.");
		} else if (result == -1) {
			System.out.println("중복된 아이디입니다.");
		} else {
			System.err.println("계정 등록에 실패하였습니다.");
		}
	}
	
	/**
	 * 로그인
	 */
	public static void login() {
		System.out.println("::::::::::::::::: 로그인 :::::::::::::::::::");
		
		System.out.print("※ 아이디 : ");
		String userId = scanner.nextLine();
		
		System.out.print("※ 비밀번호 : ");
		String password = scanner.nextLine();
		
		Employee employee = employeeService.login(userId, password);
		if (employee != null) {
			// 로그인 성공 후 완전한 정보를 다시 조회
			Employee fullEmployee = employeeService.selectById(userId);
			currentEmployee = fullEmployee != null ? fullEmployee : employee;
			System.out.println("로그인 성공! " + employee.getName() + "님 환영합니다.");
		} else {
			System.out.println("아이디 또는 비밀번호가 일치하지 않습니다.");
		}
	}
	
	/**
	 * 로그아웃
	 */
	public static void logout() {
		if (currentEmployee != null) {
			System.out.println(currentEmployee.getName() + "님 로그아웃되었습니다.");
			currentEmployee = null;
		} else {
			System.out.println("로그인된 직원이 없습니다.");
		}
	}
	
	/**
	 * 내 정보 조회
	 */
	public static void viewMyInfo() {
		if (currentEmployee == null) {
			System.out.println("로그인이 필요합니다.");
			return;
		}
		
		System.out.println(":::::::::::::::: 내 정보 조회 ::::::::::::::::");
		System.out.println("※ 직원 번호 : " + currentEmployee.getBno());
		System.out.println("※ 이름 : " + currentEmployee.getName());
		System.out.println("※ 부서 : " + currentEmployee.getDepartment());
		System.out.println("※ 직급 : " + currentEmployee.getBposition());
		System.out.println("※ 아이디 : " + currentEmployee.getUserId());
		System.out.println("※ 입사일자 : " + currentEmployee.getHireDate());
		if (currentEmployee.getUpdDate() != null) {
			System.out.println("※ 수정일자 : " + currentEmployee.getUpdDate());
		}
	}
	
	/**
	 * 내 정보 수정
	 */
	public static void updateMyInfo() {
		if (currentEmployee == null) {
			System.out.println("로그인이 필요합니다.");
			return;
		}
		
		System.out.println(":::::::::::::::: 내 정보 수정 ::::::::::::::::");
		
		System.out.print("※ 새 이름 (현재: " + currentEmployee.getName() + ") : ");
		String name = scanner.nextLine();
		
		System.out.print("※ 새 부서 (현재: " + currentEmployee.getDepartment() + ") : ");
		String department = scanner.nextLine();
		
		System.out.print("※ 새 직급 (현재: " + currentEmployee.getBposition() + ") : ");
		String position = scanner.nextLine();
		
		System.out.print("※ 새 비밀번호 : ");
		String password = scanner.nextLine();
		
		// 빈 값이면 기존 값 유지
		if (name.trim().isEmpty()) {
			name = currentEmployee.getName();
		}
		if (department.trim().isEmpty()) {
			department = currentEmployee.getDepartment();
		}
		if (position.trim().isEmpty()) {
			position = currentEmployee.getBposition();
		}
		if (password.trim().isEmpty()) {
			password = currentEmployee.getPassword();
		}
		
		Employee updateEmployee = new Employee();
		updateEmployee.setBno(currentEmployee.getBno());
		updateEmployee.setName(name);
		updateEmployee.setDepartment(department);
		updateEmployee.setBposition(position);
		updateEmployee.setUserId(currentEmployee.getUserId());
		updateEmployee.setPassword(password);
		
		int result = employeeService.update(updateEmployee);
		if (result > 0) {
			// 현재 직원 정보도 업데이트
			currentEmployee = employeeService.select(currentEmployee.getBno());
			System.out.println("정보가 수정되었습니다.");
		} else {
			System.out.println("정보 수정에 실패하였습니다.");
		}
	}
	
	/**
	 * 계정 삭제
	 */
	public static void deleteAccount() {
		if (currentEmployee == null) {
			System.out.println("로그인이 필요합니다.");
			return;
		}
		
		System.out.println(":::::::::::::::: 계정 삭제 ::::::::::::::::");
		System.out.print("정말 삭제하시겠습니까? (y/n): ");
		String answer = scanner.nextLine().toLowerCase();
		
		if ("y".equals(answer) || "yes".equals(answer)) {
			int result = employeeService.delete(currentEmployee.getBno());
			if (result > 0) {
				System.out.println("계정 삭제가 완료되었습니다.");
				currentEmployee = null;
			} else {
				System.out.println("계정 삭제에 실패하였습니다.");
			}
		} else {
			System.out.println("계정 삭제가 취소되었습니다.");
		}
	}
	
	/**
	 * 직원 관리
	 */
	public static void employeeManagement() {
		int menuNo = 0;
		
		do {
			// 직원 관리 메뉴판 출력
			employeeMenu();
			//메뉴 번호 입력
			menuNo = scanner.nextInt();
			scanner.nextLine(); // 버퍼 비우기
			// 0 -> 이전 메뉴로
			if (menuNo == 0) break;
			// 메뉴 선택
			switch (menuNo) {
			case 1: register();
				break;
			case 2: login();
				break;
			case 3: logout();
				break;
			case 4: viewMyInfo();
				break;
			case 5: updateMyInfo();
				break;
			case 6: deleteAccount();
				break;
			}
		} while (menuNo != 0);
	}
	
	/**
	 * 현재 로그인한 직원 정보 반환
	 * @return
	 */
	public static Employee getCurrentEmployee() {
		return currentEmployee;
	}
	
	/**
	 * 로그인 상태 확인
	 * @return
	 */
	public static boolean isLoggedIn() {
		return currentEmployee != null;
	}
}