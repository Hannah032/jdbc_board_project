package com.human.groupware.service;

import java.util.List;

import com.human.groupware.dto.Comment;

/**
 * 댓글 관리 프로그램 - 기능 메서드
 * - 댓글 목록 (특정 게시글)
 * - 댓글 조회
 * - 댓글 등록
 * - 댓글 수정
 * - 댓글 삭제
 * - 게시글 삭제 시 연관 댓글 삭제
 */
public interface CommentService {
	
	//특정 게시글의 댓글 목록
	List<Comment> listByBoard(int boardNo);
	//댓글 조회
	Comment select(int no);
	//댓글 등록
	int insert(Comment comment);
	//댓글 수정
	int update(Comment comment);
	//댓글 삭제
	int delete(int no);
	//게시글 삭제 시 연관 댓글 삭제
	int deleteByBoard(int boardNo);

}