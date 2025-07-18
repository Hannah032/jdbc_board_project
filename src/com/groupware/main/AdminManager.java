package com.groupware.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.SimpleFormatter;

import com.groupware.dto.AcademicSchedule;
import com.groupware.dto.Employee;
import com.groupware.service.AcademicScheduleService;
import com.groupware.service.AcademicScheduleServiceImpl;
import com.groupware.service.EmployeeService;
import com.groupware.service.EmployeeServiceImpl;



public class AdminManager {
    private static Scanner scanner = new Scanner(System.in);
    private static EmployeeService employeeService = new EmployeeServiceImpl();
    private static AcademicScheduleService academicScheduleService = new AcademicScheduleServiceImpl();

    static Employee currentEmployee = null;
    /**
     * 관리자 메뉴
     */
    public static void adminMenu() {
        System.out.println(":::::::::::::::: 관리자 메뉴 ::::::::::::::::");
        System.out.println((EmployeeManager.getCurrentEmployee()==null)?" ":(EmployeeManager.getCurrentEmployee().getName()+"님 연결중입니다."));
        System.out.println("1. 전체 직원 목록 조회");
        System.out.println("2. 직원 정보 수정");
        System.out.println("3. 직원 정보 삭제");
        System.out.println("4. 학사 일정 추가");
        System.out.println("5. 학사 일정 수정");
        System.out.println("0. 이전 메뉴로");
        System.out.print("::::::::: 번호 입력 : ");
    }

    /**
     * 전체 직원 목록 조회
     */
    public static void viewAllEmployees() {
        System.out.println(":::::::::::::::: 전체 직원 목록 ::::::::::::::::");
        List<Employee> employees = employeeService.list();
        for (Employee emp : employees) {
            System.out.println("번호: " + emp.getBno() + ", 이름: " + emp.getName() + ", 부서: " + emp.getDepartment() + ", 직급: " + emp.getBposition() + ", 아이디: " + emp.getUserId());
        }
    }

    /**
     * 직원 정보 수정
     */
    public static void updateEmployeeInfo() {
        System.out.println(":::::::::::::::: 직원 정보 수정 ::::::::::::::::");
        System.out.print("수정할 직원의 번호를 입력하세요: ");
        int bno = scanner.nextInt();
        scanner.nextLine(); // 버퍼 비우기

        Employee employee = employeeService.select(bno);
        if (employee == null) {
            System.out.println("해당 번호의 직원이 존재하지 않습니다.");
            return;
        }

        System.out.print("새 이름 (현재: " + employee.getName() + ") : ");
        String name = scanner.nextLine();
        System.out.print("새 부서 (현재: " + employee.getDepartment() + ") : ");
        String department = scanner.nextLine();
        System.out.print("새 직급 (현재: " + employee.getBposition() + ") : ");
        String position = scanner.nextLine();
        System.out.print("새 비밀번호 : ");
        String password = scanner.nextLine();

        // 빈 값이면 기존 값 유지
        if (!name.trim().isEmpty()) employee.setName(name);
        if (!department.trim().isEmpty()) employee.setDepartment(department);
        if (!position.trim().isEmpty()) employee.setBposition(position);
        if (!password.trim().isEmpty()) employee.setPassword(password);

        int result = employeeService.update(employee);
        if (result > 0) {
            System.out.println("직원 정보가 성공적으로 수정되었습니다.");
        } else {
            System.out.println("직원 정보 수정에 실패하였습니다.");
        }
    }

    /**
     * 직원 정보 삭제
     */
    public static void deleteEmployee() {
        System.out.println(":::::::::::::::: 직원 정보 삭제 ::::::::::::::::");
        System.out.print("삭제할 직원의 번호를 입력하세요: ");
        int bno = scanner.nextInt();
        scanner.nextLine(); // 버퍼 비우기

        System.out.print("정말로 삭제하시겠습니까? (y/n): ");
        String confirm = scanner.nextLine().toLowerCase();

        if ("y".equals(confirm)) {
            int result = employeeService.delete(bno);
            if (result > 0) {
                System.out.println("직원 정보가 성공적으로 삭제되었습니다.");
            } else {
                System.out.println("직원 정보 삭제에 실패하였습니다.");
            }
        } else {
            System.out.println("삭제가 취소되었습니다.");
        }
    }

    

    /**
     * 관리자 메뉴 실행
     */
    /**
     * 학사 일정 추가
     */
    public static void addAcademicSchedule() {
        System.out.println(":::::::::::::::: 학사 일정 추가 ::::::::::::::::");
        System.out.print("※ 제목: ");
        String title = scanner.nextLine();

        System.out.print("※ 시작일 (YYYY-MM-DD): ");
        String startDateStr = scanner.nextLine();

        System.out.print("※ 마감일 (YYYY-MM-DD): ");
        String endDateStr = scanner.nextLine();

        System.out.print("※ 내용: ");
        String description = scanner.nextLine();

        try {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            java.util.Date startDate = sdf.parse(startDateStr);
            java.util.Date endDate = sdf.parse(endDateStr);

            AcademicSchedule academicSchedule = new AcademicSchedule(0, title, startDate, endDate, description);
            academicScheduleService.insert(academicSchedule);
            System.out.println("학사 일정이 성공적으로 추가되었습니다.");
        } catch (java.text.ParseException e) {
            System.err.println("날짜 형식이 올바르지 않습니다. YYYY-MM-DD 형식으로 입력해주세요.");
        } catch (Exception e) {
            System.err.println("학사 일정 추가 중 오류가 발생했습니다: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 관리자 메뉴 실행
     */
    public static void adminManagement() {
        int menuNo = 0;
        do {
            adminMenu();
            menuNo = scanner.nextInt();
            scanner.nextLine(); // 버퍼 비우기

            if (menuNo == 0) break;

            switch (menuNo) {
                case 1:
                    viewAllEmployees();
                    break;
                case 2:
                    updateEmployeeInfo();
                    break;
                case 3:
                    deleteEmployee();
                    break;
                case 4:
                    addAcademicSchedule();
                    break;
                case 5:
                	updateAcademicSchedule();
                default:
                    System.out.println("잘못된 메뉴 번호입니다. 다시 선택해주세요.");
                    break;
            }
        } while (menuNo != 0);
    }
    
    private static AcademicSchedule input() { // 게시글 정보 입력
		System.out.print("※ 제목 :");
		String title = scanner.nextLine();
		System.out.print("※ 내용 :");
		String content = scanner.nextLine();
		Date timestamp = null;
		Date timestamp1 = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			System.out.println("※ 시작일 :");
			timestamp = sdf.parse(scanner.nextLine());
			System.out.println("※ 종료일 :");
			timestamp1 = sdf.parse(scanner.nextLine());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		AcademicSchedule academicSchedule = new AcademicSchedule(title, content, timestamp, timestamp1);
		return academicSchedule;
	}

	public static void updateAcademicSchedule() {
		System.out.println(":::::::::::::::: 학사일정 수정 ::::::::::::::::::");

		System.out.print("학사일정 번호 : ");
		int no = scanner.nextInt();
		scanner.nextLine(); // 버퍼 비우기

		AcademicSchedule academicSchedule = input();
		academicSchedule.setScheduleId(no);;

		// 게시글 수정 요청
		int result = academicScheduleService.update(academicSchedule);
		if ( result > 0 ) {
			System.out.println("게시글이 수정되었습니다.");
		} else {
			System.out.println("게시글 수정에 실패하였습니다.");
		}
		
	}
   
}
