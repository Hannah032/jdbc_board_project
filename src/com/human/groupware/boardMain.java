package com.human.groupware;

import java.util.List;
import java.util.Scanner;

import com.human.groupware.dto.Board;
import com.human.groupware.dto.Employee;
import com.human.groupware.service.BoardService;
import com.human.groupware.service.BoardServiceImpl;

public class boardMain {
	static Scanner scanner = new Scanner(System.in);
	static List<Board> boardList = null;
	static BoardService boardService = new BoardServiceImpl();

	// 로그인 상태 관리 - EmployeeManager에서 가져오도록 수정
	// static Employee currentEmployee = null; // 이 줄 삭제

	/**
	 * 메뉴판
	 * @param args
	 */
	public static void menu() {
		System.out.println(":::::::::::::::: 게시판 ::::::::::::::::");
		
		// 현재 로그인한 사용자 정보 가져오기
		Employee currentEmployee = EmployeeManager.getCurrentEmployee();
		if (currentEmployee != null && currentEmployee.getUserId() != null) {
			System.out.println("사용자 이름 : " + currentEmployee.getUserId());
		}
		
		System.out.println("1. 전체 글 조회");
		System.out.println("2. 게시글 조회 (검색)");
		System.out.println("3. 게시글 등록");
		System.out.println("4. 게시글 수정");
		System.out.println("5. 게시글 삭제");
		System.out.println("6. 댓글 관리");
		System.out.println("7. 게시글 검색");
		System.out.println("0. 프로그램 종료");
		System.out.print("::::::::: 번호 입력 : ");
	}

	public static void select() {
		System.out.println(":::::::::::::::: 게시글 조회 :::::::::::::::::");
		System.out.print("글 번호 : ");
		int no = scanner.nextInt();
		scanner.nextLine(); // 버퍼 비우기
		// 글번호(no)를 전달하여 게시글 정보 데이터 요청
		Board board = boardService.select(no);
		// 게시글 정보와 댓글 함께 출력
		CommentManager.printWithComments(board);
	}

	public static void insert() { // 게시글 등록
		System.out.println("::::::::::::::::: 게시글 등록 :::::::::::::::::::");

		Board board = input();
		// 게시글 등록 요청
		int result = boardService.insert(board);
		if ( result > 0 ) {
			System.out.println(" 게시글이 등록되었습니다.");
		} else  {
			System.err.println(" 게시글 등록에 실패하였습니다.");
		}
	}

	private static Board input() { // 게시글 정보 입력
		System.out.print("※ 제목 :");
		String title = scanner.nextLine();
		
		// 현재 로그인한 사용자 정보 가져오기
		Employee currentEmployee = EmployeeManager.getCurrentEmployee();
		String writer;
		if (currentEmployee != null && currentEmployee.getUserId() != null) {
			// 로그인된 사용자의 userId를 작성자로 설정
			writer = currentEmployee.getUserId();
			System.out.println("※ 작성자 : " + writer + " (자동 설정)");
		} else {
			// 로그인되지 않은 경우 수동 입력 (안전장치)
			System.out.print("※ 작성자 :");
			writer = scanner.nextLine();
		}
		
		System.out.print("※ 내용 :");
		String content = scanner.nextLine();

		Board board = new Board(title, writer, content);
		return board;
	}

	public static void update() {
		System.out.println(":::::::::::::::: 게시물 수정 ::::::::::::::::::");

		System.out.print("게시글 번호 : ");
		int no = scanner.nextInt();
		scanner.nextLine(); // 버퍼 비우기

		Board board = input();
		board.setBno(no);

		// 게시글 수정 요청
		int result = boardService.update(board);
		if ( result > 0 ) {
			System.out.println("게시글이 수정되었습니다.");
		} else {
			System.out.println("게시글 수정에 실패하였습니다.");
		}
	}

	public static void delete() { // 게시글 삭제
		System.out.println(":::::::::::::::: 게시글 삭제 ::::::::::::::::");

		System.out.print("게시글 번호: ");
		int no = scanner.nextInt();
		scanner.nextLine(); // 버퍼 비우기

		// 게시글과 연관된 댓글 먼저 삭제
		CommentManager.deleteCommentsByBoard(no);

		//게시글 삭제 요청
		int result = boardService.delete(no);
		if (result > 0) {
			System.out.println("게시글을 삭제하였습니다.");
		} else {
			System.out.println("게시글 삭제에 실패하였습니다.");
		}
	}

	public static void main(String[] args) {
		int menuNo = 0;
		do {
			menu();
			menuNo = scanner.nextInt();
			scanner.nextLine(); // 버퍼 비우기
			if (menuNo == 0) break;
			switch (menuNo) {
				case 1: // 전체 글 조회
					if (!isLoggedIn()) break;
					PagingManager.list();
					break;
				case 2: // 게시글 조회(검색)
					if (!isLoggedIn()) break;
					select();
					break;
				case 3: // 게시글 등록
					if (!isLoggedIn()) break;
					insert();
					break;
				case 4: // 게시글 수정
					if (!isLoggedIn()) break;
					update();
					break;
				case 5: // 게시글 삭제
					if (!isLoggedIn()) break;
					delete();
					break;
				case 6: // 댓글 관리
					if (!isLoggedIn()) break;
					CommentManager.commentManagement();
					break;
				case 7: // 게시글 검색
					if (!isLoggedIn()) break;
					PagingManager.searchBoard();
					break;
			}
		} while (menuNo != 0);
		System.out.println("시스템을 종료합니다....");
	}

	private static boolean isLoggedIn() {
		// EmployeeManager의 로그인 상태를 확인
		if (!EmployeeManager.isLoggedIn()) {
			System.out.println("로그인이 필요합니다.");
			return false;
		}
		return true;
	}
}