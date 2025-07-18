package com.groupware.main;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

import com.groupware.dto.AcademicSchedule;
import com.groupware.dto.Board;
import com.groupware.dto.Employee;
import com.groupware.dto.Like;
import com.groupware.dto.Search;
import com.groupware.service.AcademicScheduleService;
import com.groupware.service.AcademicScheduleServiceImpl;
import com.groupware.service.BoardService;
import com.groupware.service.BoardServiceImpl;
import com.groupware.service.LikeService;
import com.groupware.service.LikeServiceImpl;
import com.groupware.service.SearchService;
import com.groupware.service.SearchServiceImpl;

public class boardMain {
	static Scanner scanner = new Scanner(System.in);		//입력 객체
	static List<Board> boardList = null; 					//게시글 목록
	static BoardService boardService = new BoardServiceImpl(); //비즈니스 로직 객체
	static AcademicScheduleService academicScheduleService = new AcademicScheduleServiceImpl();

	/**
	 * 메뉴판
	 * @param args
	 */
	public static void menu() {
		System.out.println(":::::::::::::::: 게시판 ::::::::::::::::");
		if (!EmployeeManager.isLoggedIn()) {
			System.out.println((EmployeeManager.getCurrentEmployee()==null)?" ":(EmployeeManager.getCurrentEmployee().getName()+"님 연결중입니다."));
			System.out.println("1. 로그인");
			System.out.println("2. 회원가입");
			System.out.println("3. 전체 글 조회");
			System.out.println("4. 게시글 상세 조회");
			System.out.println("5. 게시글 검색");
			System.out.println("0. 프로그램 종료");
		} else if (EmployeeManager.isLoggedIn() && !"관리자".equals(EmployeeManager.getCurrentEmployee().getBposition())) {
			System.out.println((EmployeeManager.getCurrentEmployee()==null)?" ":(EmployeeManager.getCurrentEmployee().getName()+"님 연결중입니다."));
			System.out.println("1. 로그아웃");
			System.out.println("2. 전체 글 조회");
			System.out.println("3. 게시글 상세 조회 (좋아요, 댓글)");
			System.out.println("4. 게시글 등록");
			System.out.println("5. 게시글 수정");
			System.out.println("6. 게시글 삭제");
			System.out.println("7. 게시글 검색");
			System.out.println("8. 학사 일정 조회");
			System.out.println("0. 이전 메뉴료");
		} else if (EmployeeManager.isLoggedIn() && "관리자".equals(EmployeeManager.getCurrentEmployee().getBposition())) {
			System.out.println((EmployeeManager.getCurrentEmployee()==null)?" ":(EmployeeManager.getCurrentEmployee().getName()+"님 연결중입니다."));
			System.out.println("1. 로그아웃");
			System.out.println("2. 전체 글 조회");
			System.out.println("3. 게시글 상세 조회 (좋아요, 댓글)");			
			System.out.println("4. 게시글 등록");
			System.out.println("5. 게시글 수정");
			System.out.println("6. 게시글 삭제");
			System.out.println("7. 게시글 검색");
			System.out.println("8. 학사 일정 조회");
			System.out.println("9. 학사 일정 추가");
			System.out.println("0. 이전 메뉴료");
		} else {
			System.out.println((EmployeeManager.getCurrentEmployee()==null)?" ":(EmployeeManager.getCurrentEmployee().getName()+"님 연결중입니다."));
			System.out.println("1. 로그아웃");
			System.out.println("2. 전체 글 조회");
			System.out.println("3. 게시글 상세 조회 (좋아요, 댓글)");
			System.out.println("4. 게시글 등록");
			System.out.println("5. 게시글 수정");
			System.out.println("6. 게시글 삭제");
			System.out.println("7. 게시글 검색");
			System.out.println("8. 학사 일정 조회");
			System.out.println("0. 이전 메뉴료");
		}
		System.out.print("::::::::: 번호 입력 : ");
	}

	public static void select() {
		System.out.println(":::::::::::::::: 게시글 조회 :::::::::::::::::");
		System.out.print("글 번호 : ");
		int no = scanner.nextInt();
		scanner.nextLine(); // 버퍼 비우기
		// 글번호(no)를 전달하여 게시글 정보 데이터 요청
		Board board = boardService.select(no);
		
		// 게시글이 없는 경우
		if (board == null) {
			System.err.println("존재하지 않는 게시글입니다.");
			return;
		}

		// 좋아요 서비스와 댓글 서비스를 여기서 선언합니다.
		LikeService likeService = new LikeServiceImpl();
		
		// 좋아요 개수 표시
		int likeCount = likeService.getLikeCount(no);
		System.out.println("❤️ 좋아요: " + likeCount);

		// 게시글 정보와 댓글 함께 출력
		CommentManager.printWithComments(board);

		// 게시글 조회 후 메뉴
		while (true) {
			System.out.println(":::::::::::::: 메뉴 ::::::::::::::::");
			System.out.println("1. 좋아요 누르기/취소");
			System.out.println("2. 댓글 달기");
			System.out.println("0. 목록으로 돌아가기");
			System.out.print("::::::::: 번호 입력 : ");
			int menuNo = scanner.nextInt();
			scanner.nextLine();

			if (menuNo == 0) {
				break;
			}

			switch (menuNo) {
				case 1:
					// 좋아요 토글 기능 호출
					if (!EmployeeManager.isLoggedIn()) {
						System.out.println("로그인이 필요합니다.");
						break;
					}
					Like like = new Like(no, EmployeeManager.getCurrentEmployee().getBno());
					boolean isLiked = likeService.toggleLike(like);
					if (isLiked) {
						System.out.println("게시글에 좋아요를 눌렀습니다.");
					} else {
						System.out.println("좋아요를 취소했습니다.");
					}
					// 변경된 좋아요 개수 다시 표시
					likeCount = likeService.getLikeCount(no);
					System.out.println("❤️ 좋아요: " + likeCount);
					break;
				case 2:
					// 댓글 등록 기능 호출
					if (!EmployeeManager.isLoggedIn()) {
						System.out.println("로그인이 필요합니다.");
						break;
					}
					CommentManager.insertComment(no, EmployeeManager.getCurrentEmployee().getUserId());
					// 댓글 등록 후, 게시글 다시 조회하여 최신 상태 보여주기
					board = boardService.select(no);
					CommentManager.printWithComments(board);
					break;
				default:
					System.out.println("잘못된 번호입니다.");
					break;
			}
		}
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
		// 작성자는 로그인된 사용자의 ID로 자동 설정
		String writer = EmployeeManager.getCurrentEmployee().getUserId(); 
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

		// boardService.delete()가 댓글과 좋아요를 모두 처리하므로, 아래 라인은 주석 처리하거나 삭제합니다.
		// CommentManager.deleteCommentsByBoard(no);

		//게시글 삭제 요청 (서비스에서 댓글과 좋아요도 함께 삭제)
		int result = boardService.delete(no);
		if (result > 0) {
			System.out.println("게시글을 삭제하였습니다.");
		} else {
			System.out.println("게시글 삭제에 실패하였습니다.");
		}
	}

	public static void main(String[] args) {
		int menuNo = 0;
		SearchService searchService = new SearchServiceImpl();
		int currentPage = 1;
		final int pageSize = 5; // 페이지당 5개씩 표시

		do {
			menu();
			menuNo = scanner.nextInt();
			scanner.nextLine(); // 버퍼 비우기
			if (menuNo == 0) break;

			if (!EmployeeManager.isLoggedIn()) { // 로그아웃 상태
				switch (menuNo) {
					case 1: // 로그인
						EmployeeManager.login();
						break;
					case 2: // 회원가입(계정 등록)
						EmployeeManager.register();
						break;
					case 3: // 전체 글 조회 (페이징)
						listWithPaging(searchService, new Search("", ""), currentPage, pageSize);
						break;
					case 4: // 게시글 상세 조회
						select();
						break;
					case 5: // 게시글 검색
						search(searchService, pageSize);
						break;
					default:
						System.out.println("잘못된 번호입니다.");
						break;
				}
			} else { // 로그인 상태
				switch (menuNo) {
					case 1: // 로그아웃
						EmployeeManager.logout();
						break;
					case 2: // 전체 글 조회 (페이징)
						listWithPaging(searchService, new Search("", ""), currentPage, pageSize);
						break;
					case 3: // 게시글 상세 조회
						select();
						break;
					case 4: // 게시글 등록
						insert();
						break;
					case 5: // 게시글 수정
						update();
						break;
					case 6: // 게시글 삭제
						delete();
						break;
					case 7: // 게시글 검색
						search(searchService, pageSize);
						break;
					case 8: // 학사 일정 조회
						viewAcademicSchedule();
						break;
					case 9: // 학사 일정 추가 (관리자만)
						if (EmployeeManager.isLoggedIn() && "관리자".equals(EmployeeManager.getCurrentEmployee().getBposition())) {
							AdminManager.addAcademicSchedule();
						} else {
							System.out.println("잘못된 번호입니다.");
						}
						break;
					default:
						System.out.println("잘못된 번호입니다.");
						break;
				}
			}
		} while (menuNo != 0);
		System.out.println("게시판을 종료하고 메인 메뉴로 돌아갑니다....");
	}

	

	private static boolean isLoggedIn() {
		if (!EmployeeManager.isLoggedIn()) {
			System.out.println("로그인이 필요합니다.");
			return false;
		}
		return true;
	}
	
	/**
	 * 페이징 처리된 게시글 목록을 출력하는 메소드
	 * @param searchService
	 * @param search
	 * @param currentPage
	 * @param pageSize
	 */
	private static void listWithPaging(SearchService searchService, Search search, int currentPage, int pageSize) {
		while (true) {
			List<Board> boardList = searchService.searchWithPaging(search, currentPage, pageSize);
			
			if (boardList == null || boardList.isEmpty()) {
				System.out.println("조회된 게시글이 없습니다.");
				return;
			}

			System.out.println("============== 게시글 목록 (" + currentPage + "/" + (int)Math.ceil((double)searchService.getTotalCount(search) / pageSize) + " 페이지) ==============");
			// 날짜 포맷을 위한 SimpleDateFormat 객체 생성
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			System.out.println("번호\t| 제목\t\t| 작성자\t| 내용 미리보기\t\t| 등록일");
			System.out.println("----------------------------------------------------------------------------------------------------");

			for (Board b : boardList) {
				String contentPreview = b.getContent();
				if (contentPreview != null && contentPreview.length() > 20) { // 내용 미리보기 20자로 제한
					contentPreview = contentPreview.substring(0, 20) + "...";
				} else if (contentPreview == null || contentPreview.trim().isEmpty()) {
					contentPreview = "(내용 없음)";
				}
				// 출력 형식 조정
				System.out.printf("%d\t| %-10s\t| %-8s\t| %-25s\t| %s%n", 
					b.getBno(), 
					b.getTitle(), 
					b.getWriter(), 
					contentPreview, 
					sdf.format(b.getRegDate()));
			}
			System.out.println("====================================================================================================");

			System.out.println("페이지 이동 (숫자 입력) | 0. 돌아가기");
			System.out.print("::::::::: 번호 입력 : ");
			int pageInput = scanner.nextInt();
			scanner.nextLine(); // 버퍼 비우기

			if (pageInput == 0) {
				break; // 이전 메뉴로 돌아가기
			} else if (pageInput > 0 && pageInput <= (int)Math.ceil((double)searchService.getTotalCount(search) / pageSize)) {
				currentPage = pageInput; // 입력한 페이지로 이동
			} else {
				System.out.println("유효하지 않은 페이지 번호입니다.");
			}
		}
	}
	

	/**
	 * 검색을 수행하는 메소드
	 * @param searchService
	 * @param pageSize
	 */
	private static void search(SearchService searchService, int pageSize) {
		System.out.println(":::::::::::::::: 게시글 검색 ::::::::::::::::");
		System.out.println("1. 제목으로 검색");
		System.out.println("2. 작성자로 검색");
		System.out.println("3. 내용으로 검색");
		System.out.println("4. 제목+내용으로 검색");
		System.out.println("0. 이전 메뉴로");
		System.out.print("::::::::: 번호 입력 : ");

		int choice = scanner.nextInt();
		scanner.nextLine(); // 버퍼 비우기
		String searchType = "";

		switch (choice) {
			case 1: searchType = "title"; break;
			case 2: searchType = "writer"; break;
			case 3: searchType = "content"; break;
			case 4: searchType = "all"; break;
			case 0: return;
			default: System.out.println("잘못된 번호입니다."); return;
		}

		System.out.print("검색어를 입력하세요: ");
		String keyword = scanner.nextLine();

		Search search = new Search(searchType, keyword);
		listWithPaging(searchService, search, 1, pageSize);
	}
	
	private static void viewAcademicSchedule() {
		System.out.println(":::::::::::::::: 학사일정 조회 ::::::::::::::::");
		List<AcademicSchedule> schedules = academicScheduleService.list();
		if (schedules.isEmpty()) {
			System.out.println("등록된 학사 일정이 없습니다.");
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for (AcademicSchedule schedule : schedules) {
				System.out.println("------------------------------------");
				System.out.println("ID: " + schedule.getScheduleId());
				System.out.println("제목: " + schedule.getTitle());
				System.out.println("시작일: " + sdf.format(schedule.getStartDate()));
				System.out.println("마감일: " + sdf.format(schedule.getEndDate()));
				System.out.println("내용: " + schedule.getDescription());
			}
			System.out.println("------------------------------------");
		}
	}

}