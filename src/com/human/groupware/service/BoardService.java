package com.human.groupware.service;

import java.util.List;

import com.human.groupware.dto.Board;
import com.human.groupware.dto.Paging;

/**
 * 게시판 프로그램 - 기능 메서드
 * - 게시글 목록
 * - 게시글 조회
 * - 게시글 등록
 * - 게시글 수정
 * - 게시글 삭제
 */
public interface BoardService {
	
	//게시글 목록
	List<Board> list();
	
	//페이징 게시글 목록
	List<Board> listWithPaging(int pageNum, int countList);
	
	//검색 페이징 게시글 목록
	List<Board> listWithPagingAndSearch(int pageNum, int countList, String searchType, String searchKeyword);
	
	//게시글 조회
	Board select(int no);
	
	//게시글 등록
	int insert(Board board);
	
	//게시글 수정
	int update(Board board);
	
	//게시글 삭제
	int delete(int no);

}
