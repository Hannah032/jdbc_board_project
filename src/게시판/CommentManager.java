package 게시판;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import 게시판.dto.Board;
import 게시판.dto.Comment;
import 게시판.service.CommentService;
import 게시판.service.CommentServiceImpl;

public class CommentManager {
	private static Scanner scanner = new Scanner(System.in);
	private static CommentService commentService = new CommentServiceImpl();
	
	/**
	 * 댓글 관리 메뉴
	 */
	public static void commentMenu() {
		System.out.println(":::::::::::::::: 댓글 관리 ::::::::::::::::");
		System.out.println("1. 댓글 등록");
		System.out.println("2. 댓글 수정");
		System.out.println("3. 댓글 삭제");
		System.out.println("0. 이전 메뉴로");
		System.out.print("::::::::: 번호 입력 : ");
	}
	
	/**
	 * 댓글 등록
	 */
	public static void insertComment() {
		System.out.println("::::::::::::::::: 댓글 등록 :::::::::::::::::::");
		
		System.out.print("게시글 번호 : ");
		int boardNo = scanner.nextInt();
		scanner.nextLine(); // 버퍼 비우기
		
		System.out.print("※ 작성자 :");
		String writer = scanner.nextLine();
		System.out.print("※ 내용 :");
		String content = scanner.nextLine();
		
		Comment comment = new Comment(boardNo, writer, content);
		
		// 댓글 등록 요청
		int result = commentService.insert(comment);
		if ( result > 0 ) {
			System.out.println("댓글이 등록되었습니다.");
		} else  {
			System.err.println("댓글 등록에 실패하였습니다.");
		}
	}
	
	/**
	 * 댓글 수정
	 */
	public static void updateComment() {
		System.out.println(":::::::::::::::: 댓글 수정 ::::::::::::::::::");
		
		System.out.print("댓글 번호 : ");
		int no = scanner.nextInt();
		scanner.nextLine(); // 버퍼 비우기
		
		System.out.print("※ 수정할 내용 :");
		String content = scanner.nextLine();
		
		Comment comment = new Comment();
		comment.setBno(no);
		comment.setContent(content);
		
		// 댓글 수정 요청
		int result = commentService.update(comment);
		if ( result > 0 ) {
			System.out.println("댓글이 수정되었습니다.");
		} else {
			System.out.println("댓글 수정에 실패하였습니다.");
		}
	}
	
	/**
	 * 댓글 삭제
	 */
	public static void deleteComment() {
		System.out.println(":::::::::::::::: 댓글 삭제 ::::::::::::::::");
		
		System.out.print("댓글 번호: ");
		int no = scanner.nextInt();
		scanner.nextLine(); // 버퍼 비우기
		
		//댓글 삭제 요청
		int result = commentService.delete(no);
		if (result > 0) {
			System.out.println("댓글을 삭제하였습니다.");
		} else {
			System.out.println("댓글 삭제에 실패하였습니다.");
		}
	}
	
	/**
	 * 댓글 관리
	 */
	public static void commentManagement() {
		int menuNo = 0;
		
		do {
			// 댓글 관리 메뉴판 출력
			commentMenu();
			//메뉴 번호 입력
			menuNo = scanner.nextInt();
			scanner.nextLine(); // 버퍼 비우기
			// 0 -> 이전 메뉴로
			if (menuNo == 0) break;
			// 메뉴 선택
			switch (menuNo) {
			case 1: insertComment();
				break;
			case 2: updateComment();
				break;
			case 3: deleteComment();
				break;
			}
		} while (menuNo != 0);
	}
	
	/**
	 * 게시글과 댓글 함께 출력
	 * @param board
	 */
	public static void printWithComments(Board board) {
		if( board == null ) {
			System.err.println("조회할 수 없는 게시글입니다.");
			return;
		}
		
		// 게시글 출력
		print(board);
		
		// 댓글 목록 조회 및 출력
		List<Comment> commentList = commentService.listByBoard(board.getBno());
		if(commentList != null && !commentList.isEmpty()) {
			System.out.println(":::::::::::::: 댓글 목록 ::::::::::::::::");
			for(Comment comment : commentList) {
				printComment(comment);
			}
		} else {
			System.out.println("댓글이 없습니다.");
		}
	}
	
	/**
	 * 댓글 출력
	 * @param comment
	 */
	public static void printComment(Comment comment) {
		if( comment == null ) {
			return;
		}
		
		int no = comment.getBno();
		String writer = comment.getWriter();
		String content = comment.getContent();
		Date regDate = comment.getRegDate();
		Date updDate = comment.getUpdDate();
		
		// 날짜 포맷
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String reg = sdf.format(regDate);
		String upd = sdf.format(updDate);
		
		System.out.println("┌──────────────────────────────────");
		System.out.println("│ 댓글번호 : " + no);
		System.out.println("│ 작성자 : " + writer);
		System.out.println("│ 내용 : " + content);
		System.out.println("│ 등록일자 : " + reg);
		if(updDate != null) {
			System.out.println("│ 수정일자 : " + upd);
		}
		System.out.println("└──────────────────────────────────");
	}
	
	/**
	 * 게시물 출력 (댓글과 함께 사용하기 위해 public으로 변경)
	 * @param board
	 */
	public static void print(Board board) {
		if( board == null ) {
			System.err.println("조회할 수 없는 게시글입니다.");
			return;
		}
		
		int no = board.getBno();
		String title = board.getTitle();
		String writer = board.getWriter();
		String content = board.getContent();
		Date regDate = board.getRegDate();
		Date updDate = board.getUpdDate();
		// 날짜 포맷
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String reg = sdf.format(regDate);
		String upd = sdf.format(updDate);
		
		System.out.println(":::::::::::::::::::::::::::::::::::");
		System.out.println("※ 글번호 : " + no);
		System.out.println("※ 제목 : " + title);
		System.out.println("※ 작성자 : " + writer);
		System.out.println("------------------------------------");
		System.out.println(" " + content);
		System.out.println("※ 등록일자 : " + reg);
		System.out.println("※ 수정일자 : " + upd);
		System.out.println(":::::::::::::::::::::::::::::::::::");
		System.out.println("");
	}
	
	/**
	 * 게시글 삭제 시 연관된 댓글도 삭제
	 * @param boardNo
	 */
	public static void deleteCommentsByBoard(int boardNo) {
		commentService.deleteByBoard(boardNo);
	}
} 