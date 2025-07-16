package 게시판;

import java.util.Scanner;

import 게시판.dto.Employee;

public class Main {
	static Scanner scanner = new Scanner(System.in);
	
	/**
	 * 메인 메뉴판
	 */
	public static void mainMenu() {
		System.out.println("::::::::::::::::::::: 메인 메뉴 :::::::::::::::::::::");
		// 현재 로그인한 사용자 정보 가져오기
				Employee currentEmployee = EmployeeManager.getCurrentEmployee();
				if (currentEmployee != null && currentEmployee.getUserId() != null) {
					System.out.println("사용자 이름 : " + currentEmployee.getUserId());
				}
		System.out.println("1. 직원 관리");
		System.out.println("2. 게시물 관리");
		System.out.println("3. 회원가입 및 로그인");
		System.out.println("0. 프로그램 종료");
		System.out.print("::::::::: 번호 입력 : ");
	}
	
	/**
	 * 직원 관리 메뉴 실행
	 */
	public static void employeeManagement() {
		System.out.println("회원관리 시스템으로 이동합니다...");
		EmployeeMain.main(null);
	}
	
	/**
	 * 게시물 관리 메뉴 실행
	 */
	public static void boardManagement() {
		System.out.println("게시물 관리 시스템으로 이동합니다...");
		boardMain.main(null);
	}
	
	/**
	 * 회원 관리 메뉴 실행
	 */
	public static void userManagement() {
		System.out.println("회원관리 시스템으로 이동합니다...");
		EmployeeManager.main(null); // EmployeeManager의 main 메소드 호출
	}
	
	public static void main(String[] args) {
		int menuNo = 0;
		
		do {
			// 메인 메뉴판 출력
			mainMenu();
			// 메뉴 번호 입력
			menuNo = scanner.nextInt();
			scanner.nextLine();
			
			// 0 -> 프로그램 종료
			if (menuNo == 0) break;
			
			// 메뉴 선택
			switch (menuNo) {
			case 1: 
				employeeManagement();
				break;
			case 2: 
				boardManagement();
				break;
			case 3:
				userManagement(); // 회원 관리 메뉴 호출
				break;
			default:
				System.out.println("잘못된 메뉴 번호입니다. 다시 선택해주세요.");
				break;
			}
		} while (menuNo != 0);
		
		System.out.println("프로그램을 종료합니다....");
		scanner.close();
	}
}