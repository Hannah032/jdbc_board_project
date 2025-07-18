package com.groupware.service;

import java.util.List;

import com.groupware.dao.CommentsDAO;
import com.groupware.dto.Comment;

/**
 * 댓글 관리 기능 - 비즈니스 로직 클래스
 */
public class CommentServiceImpl implements CommentService {

	private CommentsDAO commentDAO = new CommentsDAO();
	
	@Override
	public List<Comment> listByBoard(int boardNo) {
		// DAO 객체로 특정 게시글의 댓글 목록 요청
		List<Comment> commentList = commentDAO.listByBoard(boardNo);
		// 댓글 목록 데이터 반환
		return commentList;
	}

	@Override
	public Comment select(int bno) {
		// 댓글 번호 no 를 DB로 넘겨주고 댓글 정보 요청
		Comment comment = commentDAO.select(bno);
		// 댓글 정보 반환
		return comment;
	}

	@Override
	public int insert(Comment comment) {
		// 댓글 정보를 전달하여 DB 에 데이터 등록 요청
		int result = commentDAO.insert(comment);
		// 적용된 데이터 개수를 반환
		// - result(결과) :    0 -> 데이터 등록 실패
		// 				 :    1 -> 데이터 등록 성공
		if( result > 0 ) System.out.println("댓글이 등록되었습니다!");
		else System.out.println("댓글 등록에 실패하였습니다!");
		return result;
	}

	@Override
	public int update(Comment comment) {
		int result = commentDAO.update(comment);
		// - result(결과) :    0 -> 데이터 수정 실패
		// 				 :    1 -> 데이터 수정 성공
		if( result > 0 ) System.out.println("댓글이 수정되었습니다!");
		else System.out.println("댓글 수정에 실패하였습니다!");
		return result;
	}

	@Override
	public int delete(int no) {
		int result = commentDAO.delete(no);
		// - result(결과) :    0 -> 데이터 삭제 실패
		// 				 :    1 -> 데이터 삭제 성공
		if( result > 0 ) System.out.println("댓글이 삭제되었습니다!");
		else System.out.println("댓글 삭제에 실패하였습니다!");
		return result;	
	}
	
	@Override
	public int deleteByBoard(int boardNo) {
		int result = commentDAO.deleteByBoard(boardNo);
		// - result(결과) :    0 -> 데이터 삭제 실패
		// 				 :    1 이상 -> 삭제된 댓글 개수
		if( result > 0 ) System.out.println("게시글과 연관된 댓글 " + result + "개가 삭제되었습니다!");
		else System.out.println("삭제할 댓글이 없습니다!");
		return result;	
	}
}