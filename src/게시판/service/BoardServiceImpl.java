package 게시판.service;

import java.util.List;

import 게시판.dao.BoardDAO;
import 게시판.dto.Board;
import 게시판.dto.Paging;
import 게시판.service.PagingService;

/**
 * 게시판 기능 - 비즈니스 로직 클래스
 */
public class BoardServiceImpl implements BoardService {

	private BoardDAO boardDAO = new BoardDAO();
	private PagingService pagingService = new PagingServiceImpl();
	
	@Override
	public List<Board> list() {
		// DAO 객체로 게시글 목록 요청하고 List<Board>
		List<Board> boardList = boardDAO.list();
		// 게시글 목록 데이터 반환
		return boardList;
	}

	@Override
	public Board select(int bno) {
		// 게시글 번호 no 를 DB로 넘겨주고 게시글 정보 요청
		Board board = boardDAO.select(bno);
		// 게시글 정보 반환
		return board;
	}

	@Override
	public int insert(Board board) {
		// 게시글 정보를 전달하여 DB 에 데이터 등록 요청
		int result = boardDAO.insert(board);
		// 적용된 데이터 개수를 반환
		// - result(결과) :    0 -> 데이터 등록 실패
		// 				 :    1 -> 데이터 등록 성공
		if( result > 0 ) System.out.println("데이터 등록 성공!");
		else System.out.println("데이터 등록 실패!");
		return result;
	}

	@Override
	public int update(Board board) {
		int result = boardDAO.update(board);
		// - result(결과) :    0 -> 데이터 수정 실패
		// 				 :    1 -> 데이터 수정 성공
		if( result > 0 ) System.out.println("데이터 수정 성공!");
		else System.out.println("데이터 수정 실패!");
		return result;
	}

	@Override
	public int delete(int bno) {
		int result = boardDAO.delete(bno);
		// - result(결과) :    0 -> 데이터 수정 실패
		// 				 :    1 -> 데이터 수정 성공
		if( result > 0 ) System.out.println("데이터 삭제 성공!");
		else System.out.println("데이터 삭제 실패!");
		return result;	
	}
	
	@Override
	public List<Board> listWithPaging(int pageNum, int countList) {
		// 고정된 총 게시글 수 (페이지당 5개 * 최대 10페이지 = 50개)
		int totalCount = 50;
		
		// 페이징 객체 생성 및 계산
		Paging paging = pagingService.createPaging(totalCount, countList, pageNum);
		
		// 페이징된 게시글 목록 조회
		List<Board> boardList = boardDAO.listWithPaging(paging);
		
		return boardList;
	}
	
	@Override
	public List<Board> listWithPagingAndSearch(int pageNum, int countList, String searchType, String searchKeyword) {
		// 고정된 총 게시글 수 (페이지당 5개 * 최대 10페이지 = 50개)
		int totalCount = 50;
		
		// 페이징 객체 생성 및 계산 (검색 조건 포함)
		Paging paging = pagingService.createPagingWithSearch(totalCount, countList, pageNum, searchType, searchKeyword);
		
		// 검색 조건과 페이징된 게시글 목록 조회
		List<Board> boardList = boardDAO.listWithPagingAndSearch(paging);
		
		return boardList;
	}
}
