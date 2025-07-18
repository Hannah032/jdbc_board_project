package com.groupware.main;

import java.util.Scanner;

import com.groupware.main.boardMain;
import com.groupware.service.EmployeeService;
import com.groupware.main.EmployeeManager;

import com.groupware.main.AdminManager;

import com.groupware.dto.Employee;

public class Main {
	static Scanner scanner = new Scanner(System.in);
	
	//private static EmployeeService employeeService = new EmployeeServiceImpl();
	
	/**
	 * 메인 메뉴판
	 */
	public static void mainMenu() {
		System.out.println("::::::::::::::::::::: 메인 메뉴 :::::::::::::::::::::");
		System.out.println("1. 직원 관리");
		System.out.println("2. 게시물 관리");
		// 관리자 메뉴 추가
		if (EmployeeManager.isLoggedIn() && "관리자".equals(EmployeeManager.getCurrentEmployee().getBposition())) {
			System.out.println("3. 관리자 메뉴");
		}
		System.out.println("0. 프로그램 종료");
		System.out.print("::::::::: 번호 입력 : ");
	}
	
	/**
	 * 직원 관리 메뉴 실행
	 */
	public static void employeeManagement() {
		System.out.println("직원 관리 시스템으로 이동합니다...");
		EmployeeManager.employeeManagement();
	}
	
	/**
	 * 게시물 관리 메뉴 실행
	 */
	public static void boardManagement() {
		System.out.println("게시물 관리 시스템으로 이동합니다...");
		boardMain.main(null);
	}
	
	/**
	 * 관리자 메뉴 실행
	 */
	public static void adminManagement() {
		System.out.println("관리자 메뉴 시스템으로 이동합니다...");
		AdminManager.adminManagement();
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
				if (EmployeeManager.isLoggedIn() && "관리자".equals(EmployeeManager.getCurrentEmployee().getBposition())) {
					adminManagement();
				} else {
					System.out.println("잘못된 메뉴 번호입니다. 다시 선택해주세요.");
				}
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