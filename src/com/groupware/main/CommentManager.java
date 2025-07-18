package com.groupware.main;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.groupware.dto.Board;
import com.groupware.dto.Comment;
import com.groupware.service.CommentService;
import com.groupware.service.CommentServiceImpl;
import com.groupware.service.LikeServiceImpl;

public class CommentManager {
	private static Scanner scanner = new Scanner(System.in);
	private static CommentService commentService = new CommentServiceImpl();
	private static LikeServiceImpl likeService = new LikeServiceImpl(); // LikeService 추가
	
	/**
	 * 댓글 등록
	 * @param boardNo 게시글 번호
	 * @param writer 작성자
	 */
	public static void insertComment(int boardNo, String writer) {
		System.out.println("::::::::::::::::: 댓글 등록 :::::::::::::::::::");
		
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
		System.out.println("내용: " + content); // 내용 출력 부분을 명확히 표시
		System.out.println("------------------------------------");
		// 게시글 하단에 좋아요 개수 표시
		int likeCount = likeService.getLikeCount(no);
		System.out.println("❤️ 좋아요: " + likeCount);
		System.out.println("------------------------------------");
		System.out.println("※ 등록일자 : " + reg);
		if(updDate != null) {
			System.out.println("※ 수정일자 : " + upd);
		}
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