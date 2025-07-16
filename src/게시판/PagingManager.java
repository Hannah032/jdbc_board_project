package 게시판;

import java.util.List;
import java.util.Scanner;

import 게시판.dto.Board;
import 게시판.dto.Paging;
import 게시판.service.BoardService;
import 게시판.service.BoardServiceImpl;
import 게시판.service.PagingService;
import 게시판.service.PagingServiceImpl;

public class PagingManager {
	private static Scanner scanner = new Scanner(System.in);
	private static BoardService boardService = new BoardServiceImpl();
	private static PagingService pagingService = new PagingServiceImpl();
	
	// 페이징 관련 변수
	private static int currentPage = 1;		// 현재 페이지
	private static final int pageSize = 5;	// 페이지당 게시글 수 (고정)
	private static final int maxPages = 10;	// 최대 페이지 수 (고정)
	private static String searchType = "";	// 검색 타입
	private static String searchKeyword = ""; // 검색 키워드
	
	/**
	 * 게시글 목록 (페이징)
	 */
	public static void list() {
		System.out.println(":::::::::::::: 게시글 목록 ::::::::::::::::");
		
		// 최대 페이지 수 제한
		if (currentPage > maxPages) {
			currentPage = maxPages;
		}
		
		// 검색 조건이 있으면 검색 페이징, 없으면 기본 페이징
		List<Board> boardList;
		if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
			boardList = boardService.listWithPagingAndSearch(currentPage, pageSize, searchType, searchKeyword);
		} else {
			boardList = boardService.listWithPaging(currentPage, pageSize);
		}
		
		printAll(boardList);
		
		// 고정된 페이징 정보 출력
		int totalCount = pageSize * maxPages; // 50개
		Paging paging = pagingService.createPaging(totalCount, pageSize, currentPage);
		printPagingInfo(paging);
		printPagingNavigation(paging);
		
		// 페이지 이동 메뉴 통합
		showPageNavigationMenu(paging);
	}
	
	/**
	 * 페이지 이동 메뉴 - 페이지 번호 직접 입력 방식
	 * 사용자가 1~10 사이의 페이지 번호를 입력하여 해당 페이지로 이동
	 * @param paging 현재 페이징 정보 객체
	 */
	private static void showPageNavigationMenu(Paging paging) {
		System.out.println("\n:::::::::::::: 페이지 이동 ::::::::::::::::");
		
		// 현재 페이지 정보와 사용 가능한 페이지 범위 표시
		System.out.println("현재 페이지: " + currentPage + "/" + maxPages);
		System.out.println("사용 가능한 페이지: 1 ~ " + maxPages);
		
		// 페이지 번호 목록을 시각적으로 표시 (1부터 maxPages까지)
		System.out.print("페이지: ");
		for (int i = 1; i <= maxPages; i++) {
			// 현재 페이지는 [숫자] 형태로 강조 표시
			if (i == currentPage) {
				System.out.print("[" + i + "] ");
			} else {
				// 다른 페이지는 숫자만 표시
				System.out.print(i + " ");
			}
		}
		System.out.println(); // 줄바꿈
		
		// 사용자에게 페이지 번호 입력 요청
		System.out.print("이동할 페이지 번호를 입력하세요 (1-" + maxPages + ", 0=메인메뉴): ");
		
		try {
			// 사용자 입력 받기
			int selectedPage = scanner.nextInt();
			scanner.nextLine(); // 입력 버퍼 비우기 (nextInt() 후 남은 개행문자 제거)
			
			// 입력값에 따른 처리 분기
			if (selectedPage == 0) {
				// 0 입력시 메인 메뉴로 돌아가기
				System.out.println("메인 메뉴로 돌아갑니다.");
				return; // 메서드 종료
			} else if (selectedPage >= 1 && selectedPage <= maxPages) {
				// 유효한 페이지 번호 범위 내의 입력인 경우
				
				// 현재 페이지와 같은 페이지를 선택한 경우
				if (selectedPage == currentPage) {
					System.out.println("이미 " + currentPage + "페이지에 있습니다.");
					// 다시 페이지 이동 메뉴 표시
					showPageNavigationMenu(paging);
					return;
				}
				
				// 다른 페이지로 이동하는 경우
				currentPage = selectedPage; // 현재 페이지 변수 업데이트
				System.out.println(selectedPage + "페이지로 이동합니다.");
				
				// 선택한 페이지의 게시글 목록을 다시 로드하여 표시
				list(); // 재귀 호출로 새로운 페이지 내용 표시
				return;
			} else {
				// 유효하지 않은 페이지 번호 입력 (1~maxPages 범위 밖)
				System.out.println("잘못된 페이지 번호입니다. 1부터 " + maxPages + "까지 입력해주세요.");
				// 다시 페이지 이동 메뉴 표시
				showPageNavigationMenu(paging);
				return;
			}
		} catch (Exception e) {
			// 숫자가 아닌 값을 입력한 경우 예외 처리
			scanner.nextLine(); // 잘못된 입력 버퍼 비우기
			System.out.println("숫자만 입력해주세요.");
			// 다시 페이지 이동 메뉴 표시
			showPageNavigationMenu(paging);
			return;
		}
	}
	
	/**
	 * 페이징 정보 출력 - 현재 페이지 상태를 사용자에게 표시
	 * @param paging 페이징 정보 객체
	 */
	private static void printPagingInfo(Paging paging) {
		// 페이징 관련 정보를 간단하게 표시
		System.out.println("\n=== 페이징 정보 ===");
		System.out.println("현재 페이지: " + paging.getPageNum() + "/" + maxPages);
		System.out.println("페이지당 게시글 수: " + paging.getCountList() + "개");
		System.out.println("전체 게시글 수: " + paging.getTotalCount() + "개 (최대)");
		System.out.println("==================");
	}
	
	/**
	 * 페이징 네비게이션 출력 - 이전/다음 페이지 존재 여부 표시
	 * @param paging 페이징 정보 객체
	 */
	private static void printPagingNavigation(Paging paging) {
		// 이전/다음 페이지 존재 여부를 시각적으로 표시
		System.out.print("네비게이션: ");
		
		// 이전 페이지 존재 여부 표시
		if (paging.isHasPrev()) {
			System.out.print("◀ 이전 ");
		} else {
			System.out.print("   이전 "); // 공백으로 정렬 맞춤
		}
		
		// 현재 페이지 표시
		System.out.print("| " + currentPage + "페이지 |");
		
		// 다음 페이지 존재 여부 표시
		if (paging.isHasNext()) {
			System.out.print(" 다음 ▶");
		} else {
			System.out.print(" 다음   "); // 공백으로 정렬 맞춤
		}
		System.out.println(); // 줄바꿈
	}
	
	private static void printAll(List<Board> list) {
		// 글 목록이 존재하는지 확인
		if(list == null || list.isEmpty()) {
			System.out.println("조회된 글이 없습니다.");
			return;
		}
		// 글 목록 출력
		for (Board board : list) {
			CommentManager.print(board);
		}
	}
	
	/**
	 * 페이징 설정 (페이지 이동만)
	 */
	public static void pagingSettings() {
		System.out.println(":::::::::::::::: 페이징 설정 ::::::::::::::::");
		System.out.println("현재 설정: 페이지당 " + pageSize + "개 (고정), 최대 " + maxPages + "페이지, 현재 페이지: " + currentPage);
		System.out.println("1. 페이지 이동");
		System.out.println("0. 이전 메뉴로");
		System.out.print("::::::::: 번호 입력 : ");
		
		int choice = scanner.nextInt();
		scanner.nextLine(); // 버퍼 비우기
		
		switch (choice) {
			case 1:
				moveToPage();
				break;
			case 0:
				return;
			default:
				System.out.println("잘못된 번호입니다.");
		}
	}
	
	/**
	 * 페이지 이동 (미사용)
	 */
	private static void moveToPage() {
		System.out.print("이동할 페이지 번호를 입력하세요 (1-" + maxPages + "): ");
		int newPage = scanner.nextInt();
		scanner.nextLine(); // 버퍼 비우기
		
		if (newPage >= 1 && newPage <= maxPages) {
			currentPage = newPage;
			System.out.println(currentPage + "페이지로 이동했습니다.");
		} else {
			System.out.println("1부터 " + maxPages + "까지의 페이지 번호를 입력해주세요.");
		}
	}
	
	/**
	 * 검색 조건 초기화
	 */
	private static void resetSearch() {
		searchType = "";
		searchKeyword = "";
		currentPage = 1;
		System.out.println("검색 조건이 초기화되었습니다.");
	}
	
	/**
	 * 게시글 검색 (검색 조건 초기화 포함)
	 */
	public static void searchBoard() {
		System.out.println(":::::::::::::::: 게시글 검색 ::::::::::::::::");
		System.out.println("1. 제목으로 검색");
		System.out.println("2. 작성자로 검색");
		System.out.println("3. 내용으로 검색");
		System.out.println("4. 제목+내용으로 검색");
		System.out.println("5. 검색 조건 초기화");
		System.out.println("0. 이전 메뉴로");
		System.out.print("::::::::: 번호 입력 : ");
		
		int choice = scanner.nextInt();
		scanner.nextLine(); // 버퍼 비우기
		
		switch (choice) {
			case 1:
				searchType = "title";
				break;
			case 2:
				searchType = "writer";
				break;
			case 3:
				searchType = "content";
				break;
			case 4:
				searchType = "all";
				break;
			case 5:
				resetSearch();
				return;
			case 0:
				return;
			default:
				System.out.println("잘못된 번호입니다.");
				return;
		}
		
		System.out.print("검색어를 입력하세요: ");
		searchKeyword = scanner.nextLine();
		currentPage = 1; // 검색 시 1페이지로 이동
		
		System.out.println("검색 조건이 설정되었습니다: " + searchType + " - " + searchKeyword);
	}
	
	// Getter 메서드들
	public static int getCurrentPage() {
		return currentPage;
	}
	
	public static int getPageSize() {
		return pageSize;
	}
	
	public static String getSearchType() {
		return searchType;
	}
	
	public static String getSearchKeyword() {
		return searchKeyword;
	}
}