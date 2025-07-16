package 게시판;

import java.util.List;
import java.util.Scanner;

import 게시판.dto.Employee;
import 게시판.service.EmployeeService;
import 게시판.service.EmployeeServiceImpl;

public class EmployeeMain {
    static Scanner scanner = new Scanner(System.in);
    static EmployeeService employeeService = new EmployeeServiceImpl();

    public static void main(String[] args) {
        while (true) {
            showMenu();
            int menuNo = getMenuNumber();

            switch (menuNo) {
                case 1: list(); break;
                case 2: select(); break;
                case 3: insert(); break;
                case 4: update(); break;
                case 5: delete(); break;
                case 0: 
                    System.out.println("프로그램을 종료합니다.");
                    return;
                default: 
                    System.out.println("잘못된 번호입니다. 다시 입력해주세요."); 
                    break;
            }
        }
    }

    private static void showMenu() {
        System.out.println("\n:::::::::::::::: 직원 관리 ::::::::::::::::");
     // 현재 로그인한 사용자 정보 가져오기
     		Employee currentEmployee = EmployeeManager.getCurrentEmployee();
     		if (currentEmployee != null && currentEmployee.getUserId() != null) {
     			System.out.println("사용자 이름 : " + currentEmployee.getUserId());
     		}
        System.out.println("1. 직원 목록");
        System.out.println("2. 직원 조회");
        System.out.println("3. 직원 등록");
        System.out.println("4. 직원 수정");
        System.out.println("5. 직원 삭제");
        System.out.println("0. 프로그램 종료");
        System.out.print("::::::::: 번호 입력 : ");
    }

    private static int getMenuNumber() {
        while (!scanner.hasNextInt()) {
            System.out.println("숫자를 입력해주세요.");
            scanner.next(); 
            System.out.print("::::::::: 번호 입력 : ");
        }
        int menuNo = scanner.nextInt();
        scanner.nextLine(); 
        return menuNo;
    }

    public static void list() {
        System.out.println("\n:::::::::::::: 직원 목록 ::::::::::::::::");
        List<Employee> employeeList = employeeService.selectAll();
        if (employeeList.isEmpty()) {
            System.out.println("조회된 직원이 없습니다.");
            return;
        }
        for (Employee employee : employeeList) {
            print(employee);
        }
    }

    public static void print(Employee employee) {
        if (employee == null) {
            System.out.println("(조회된 직원이 없습니다)");
            return;
        }
        System.out.println("-----------------------------------");
        System.out.println("직원번호 : " + employee.getBno());
        System.out.println("이름 : " + employee.getName());
        System.out.println("부서 : " + employee.getDepartment());
        System.out.println("직급 : " + employee.getBposition());
        System.out.println("입사일자 : " + employee.getHireDate());
        System.out.println("수정일자 : " + employee.getUpdDate());
        System.out.println("-----------------------------------");
    }

    public static void select() {
        System.out.println("\n:::::::::::::::: 직원 조회 :::::::::::::::::");
        System.out.print("조회할 직원 번호 : ");
        int no = getMenuNumber();
        Employee employee = employeeService.select(no);
        print(employee);
    }

    public static void insert() {
        System.out.println("\n:::::::::::::::: 직원 등록 :::::::::::::::::");
        Employee Employee = inputEmployee();
        int result = employeeService.insert(Employee);
        if (result > 0) {
            System.out.println("새로운 직원이 성공적으로 등록되었습니다.");
        } else {
            System.err.println("직원 등록에 실패하였습니다.");
        }
    }

    public static void update() {
        System.out.println("\n:::::::::::::::: 직원 수정 :::::::::::::::::");
        System.out.print("수정할 직원 번호 : ");
        int no = getMenuNumber();
        Employee employee = employeeService.select(no);
        if (employee == null) {
            System.out.println("해당 번호의 직원이 존재하지 않습니다.");
            return;
        }

        System.out.println("수정할 정보를 입력하세요. (수정하지 않을 항목은 Enter)");
        System.out.print("이름 (" + employee.getName() + ") : ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) employee.setName(name);

        System.out.print("부서 (" + employee.getDepartment() + ") : ");
        String department = scanner.nextLine();
        if (!department.isEmpty()) employee.setDepartment(department);

        System.out.print("직급 (" + employee.getBposition() + ") : ");
        String position = scanner.nextLine();
        if (!position.isEmpty()) employee.setBposition(position);

        int result = employeeService.update(employee);
        if (result > 0) {
            System.out.println("직원 정보가 성공적으로 수정되었습니다.");
        } else {
            System.err.println("직원 정보 수정에 실패하였습니다.");
        }
    }

    public static void delete() {
        System.out.println("\n:::::::::::::::: 직원 삭제 :::::::::::::::::");
        System.out.print("삭제할 직원 번호 : ");
        int no = getMenuNumber();
        int result = employeeService.delete(no);
        if (result > 0) {
            System.out.println("직원 정보가 성공적으로 삭제되었습니다.");
        } else {
            System.err.println("직원 정보 삭제에 실패하였습니다.");
        }
    }

    private static Employee inputEmployee() {
        Employee employee = new Employee();
        System.out.print("이름 : ");
        employee.setName(scanner.nextLine());
        System.out.print("부서 : ");
        employee.setDepartment(scanner.nextLine());
        System.out.print("직급 : ");
        employee.setBposition(scanner.nextLine());
        return employee;
    }
}

