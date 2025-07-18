package com.groupware.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.groupware.dto.Board;
import com.groupware.dto.Paging;
/**
 * 데이터 접근 객체
 * - 게시글 데이터를 접근
 */
public class BoardDAO extends JDBConnection {
	/**
	 * 데이터 목록
	 * @return
	 */
	public List<Board> list() {
		// 게시글 목록을 담을 컬렉션 객체 생성
		List<Board> boardList = new ArrayList<Board>();
		
		// SQL 작성
		String sql = " select * "
				+ " from board ";
		
		try {
			// 1. SQL 실행 객체 생성 - Statement (stmt)
			stmt = getValidConnection().createStatement();
			
			// 2. SQL 실행 요청 -> 결과 ResultSet (rs)
			rs = stmt.executeQuery(sql);
			
			// 3. 조회된 결과를 리스트(boardList)에 추가
			while(rs.next()) {				// next() : 조회 결과의 다음 데이터로 이동
				Board board = new Board();
				
				// 결과 데이터 가져오기
				// rs.getXXX("컬럼명")   : 해당 컬럼의 데이터를 반환
				board.setBno(rs.getInt("bno"));
				board.setTitle(rs.getString("title"));
				board.setWriter(rs.getString("writer"));
				board.setContent(rs.getString("content"));
				board.setRegDate(rs.getTimestamp("reg_date"));
				board.setUpdDate(rs.getTimestamp("upd_date"));
				
				boardList.add(board);
			}
		} catch (SQLException e) {
			System.err.println("게시글 목록 조회 시, 예외 발생");
			e.printStackTrace();
		}
		
		return boardList;
	}
	/**
	 * 데이터 조회
	 * @param no
	 * @return
	 */
	public Board select(int bno) {
		// 게시글 정보 객체 생성
		Board board = new Board();
		
		// SQL 작성
		String sql = " select *"
				+ " from board"
				+ " where bno = ? "; // no가 ? 인 데이터만 조회
		
		// 데이터 조회 : SQL 실행 객체 생성 -> SQL 실행 요청 -> 조회 결과 -> 반환
		try {
			// 1. SQL 실행 객체 생성 - PreparedStatement (pstmt)
			psmt = getValidConnection().prepareStatement(sql);
			
			// ? 동적 파라미터 바인딩
			// * psmt.setXXX( 순서번호, 매핑할 값 );
			psmt.setInt( 1, bno); 	// 1번째 ? 파라미터에 매핑
			
			// SQL 실행 요청
			rs = psmt.executeQuery();
			
			// 조회 결과 1건 가져오기
			if (rs.next()) {
				// 결과 데이터 가져오기
				// rs.getXXX("컬럼명")   : 해당 컬럼의 데이터를 반환
				board.setBno(rs.getInt("bno"));
				board.setTitle(rs.getString("title"));
				board.setWriter(rs.getString("writer"));
				board.setContent(rs.getString("content"));
				board.setRegDate(rs.getTimestamp("reg_date"));
				board.setUpdDate(rs.getTimestamp("upd_date"));
			}
		} catch (SQLException e)  {
			System.err.println("게시글 조회 시, 예외 발생");
			e.printStackTrace();
		}
		// 게시글 정보 1건 반환
		return board;
	}
	
	public int insert(Board board) {
		int result = 0; 		// 결과 : 적용된 데이터 개수
		
		String sql = " insert into board (bno, title, writer, content) "
					+ " values(SEQ_BOARD.NEXTVAL, ?, ?, ? )";
		try {
			psmt = getValidConnection().prepareStatement(sql); 		// 쿼리 실행 객체 생성
			psmt.setString(1, board.getTitle());	// 1번 ? 에 title(제목) 매핑
			psmt.setString(2, board.getWriter());	// 2번 ? 에 writer(작성자) 매핑
			psmt.setString(3, board.getContent());	// 3번 ? 에 content(내용) 매핑
			result = psmt.executeUpdate();
			// * executeUpdate()
			// SQL(INSERT, UPDATE, DELETE) 실행 시 적용된 데이터 개수를 int 타입으로 받아온다.
			// ex) 게시글 1개 적용 성공 시, result : 1
			//					실패 시, result : 0
		} catch (SQLException e) {
			System.err.println("게시글 생성 시, 예외 발생");
			e.printStackTrace();
		}
		return result;
	}

	public int update(Board board) {
		int result = 0; 		// 결과 : 적용된 데이터 개수
		
		String sql = " update board "
					+ "\t\tset title = ? "
					+ "\t\t\t,writer = ? "
					+ "\t\t\t,content = ? "
					+ "\t\t\t,upd_date = SYSDATE "
					+ " where bno = ? ";		
		try {
			psmt = getValidConnection().prepareStatement(sql); 		// 쿼리 실행 객체 생성
			psmt.setString(1, board.getTitle());	// 1번 ? 에 title(제목) 매핑
			psmt.setString(2, board.getWriter());	// 2번 ? 에 writer(작성자) 매핑
			psmt.setString(3, board.getContent());	// 3번 ? 에 content(내용) 매핑
			psmt.setInt(4, board.getBno());	// 4번 ? 에 no(게시글번호) 매핑
			result = psmt.executeUpdate();
			// * executeUpdate()
			// SQL(INSERT, UPDATE, DELETE) 실행 시 적용된 데이터 개수를 int 타입으로 받아온다.
			// ex) 게시글 1개 적용 성공 시, result : 1
			//					실패 시, result : 0
		} catch (SQLException e) {
			System.err.println("게시글 수정 시, 예외 발생");
			e.printStackTrace();
		}
		return result;
	}

	public int delete(int bno) {
		int result = 0; 		// 결과 : 적용된 데이터 개수
		
		String sql = " delete from board "
					+ " where bno = ? ";	
		try {
			psmt = getValidConnection().prepareStatement(sql); 
			psmt.setInt(1, bno);
			result = psmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println("게시글 삭제 시, 예외 발생");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 전체 게시글 수 조회
	 * @return 전체 게시글 수
	 */
	public int getTotalCount() {
		int totalCount = 0;
		
		String sql = " SELECT COUNT(*) FROM board ";
		
		try {
			stmt = getValidConnection().createStatement();
			rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
				totalCount = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.err.println("전체 게시글 수 조회 시, 예외 발생");
			e.printStackTrace();
		}
		
		return totalCount;
	}
	
	/**
	 * 검색 조건에 따른 전체 게시글 수 조회
	 * @param searchType 검색 타입 (title, writer, content)
	 * @param searchKeyword 검색 키워드
	 * @return 검색된 게시글 수
	 */
	public int getTotalCount(String searchType, String searchKeyword) {
		int totalCount = 0;
		
		String sql = " SELECT COUNT(*) FROM board WHERE 1=1 ";
		
		// 검색 조건 추가
		if (searchType != null && searchKeyword != null && !searchKeyword.trim().isEmpty()) {
			switch (searchType) {
				case "title":
					sql += " AND title LIKE ? ";
					break;
				case "writer":
					sql += " AND writer LIKE ? ";
					break;
				case "content":
					sql += " AND content LIKE ? ";
					break;
				default:
					sql += " AND (title LIKE ? OR content LIKE ?) ";
					break;
			}
		}
		
		try {
			psmt = con.prepareStatement(sql);
			
			// 검색 파라미터 바인딩
			if (searchType != null && searchKeyword != null && !searchKeyword.trim().isEmpty()) {
				if ("title".equals(searchType) || "writer".equals(searchType) || "content".equals(searchType)) {
					psmt.setString(1, "%" + searchKeyword + "%");
				} else {
					psmt.setString(1, "%" + searchKeyword + "%");
					psmt.setString(2, "%" + searchKeyword + "%");
				}
			}
			
			rs = psmt.executeQuery();
			
			if (rs.next()) {
				totalCount = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.err.println("검색 게시글 수 조회 시, 예외 발생");
			e.printStackTrace();
		}
		
		return totalCount;
	}
	
	/**
	 * 페이징을 적용한 게시글 목록 조회
	 * @param paging 페이징 정보
	 * @return 페이징된 게시글 목록
	 */
	public List<Board> listWithPaging(Paging paging) {
		List<Board> boardList = new ArrayList<Board>();
		
		// 페이징 쿼리 수정: ROWNUM 별칭(rnum)을 사용하여 특정 범위의 행을 선택합니다.
		// 안쪽 쿼리에서 bno 내림차순으로 정렬 후 ROWNUM을 할당하고,
		// 바깥쪽 쿼리에서 rnum을 기준으로 원하는 페이지의 범위를 조회합니다.
		String sql = " SELECT * FROM ( "
				+ "     SELECT ROWNUM AS rnum, b.* FROM ( "
				+ "         SELECT * FROM board "
				+ "         ORDER BY bno DESC "
				+ "     ) b WHERE ROWNUM <= ? " // 해당 페이지의 마지막 행 번호
				+ " ) WHERE rnum >= ? ";      // 해당 페이지의 시작 행 번호
		
		try {
			psmt = con.prepareStatement(sql);
			// Paging 객체에서 계산된 시작과 끝 행 번호를 사용합니다.
			psmt.setInt(1, paging.getEndRow());   // ex) 1페이지: 5, 2페이지: 10
			psmt.setInt(2, paging.getStartRow()); // ex) 1페이지: 1, 2페이지: 6
			
			rs = psmt.executeQuery();
			
			while (rs.next()) {
				Board board = new Board();
				board.setBno(rs.getInt("bno"));
				board.setTitle(rs.getString("title"));
				board.setWriter(rs.getString("writer"));
				board.setContent(rs.getString("content"));
				board.setRegDate(rs.getTimestamp("reg_date"));
				board.setUpdDate(rs.getTimestamp("upd_date"));
				
				boardList.add(board);
			}
		} catch (SQLException e) {
			System.err.println("페이징 게시글 목록 조회 시, 예외 발생");
			e.printStackTrace();
		}
		
		return boardList;
	}
	
	/**
	 * 검색 조건과 페이징을 적용한 게시글 목록 조회
	 * @param paging 페이징 정보 (검색 조건 포함)
	 * @return 검색된 페이징 게시글 목록
	 */
	public List<Board> listWithPagingAndSearch(Paging paging) {
		List<Board> boardList = new ArrayList<Board>();
		
		String sql = " SELECT * FROM ( "
				+ "     SELECT ROWNUM AS rnum, b.* FROM ( "
				+ "         SELECT * FROM board WHERE 1=1 ";
		
		// 검색 조건 추가
		if (paging.getSearchType() != null && paging.getSearchKeyword() != null && !paging.getSearchKeyword().trim().isEmpty()) {
			switch (paging.getSearchType()) {
				case "title":
					sql += " AND title LIKE ? ";
					break;
				case "writer":
					sql += " AND writer LIKE ? ";
					break;
				case "content":
					sql += " AND content LIKE ? ";
					break;
				default:
					sql += " AND (title LIKE ? OR content LIKE ?) ";
					break;
			}
		}
		
		sql += "         ORDER BY bno DESC "
				+ "     ) b WHERE ROWNUM <= ? "
				+ " ) WHERE rnum >= ? ";
		
		try {
			psmt = con.prepareStatement(sql);
			
			int paramIndex = 1;
			
			// 검색 파라미터 바인딩
			if (paging.getSearchType() != null && paging.getSearchKeyword() != null && !paging.getSearchKeyword().trim().isEmpty()) {
				if ("title".equals(paging.getSearchType()) || "writer".equals(paging.getSearchType()) || "content".equals(paging.getSearchType())) {
					psmt.setString(paramIndex++, "%" + paging.getSearchKeyword() + "%");
				} else {
					psmt.setString(paramIndex++, "%" + paging.getSearchKeyword() + "%");
					psmt.setString(paramIndex++, "%" + paging.getSearchKeyword() + "%");
				}
			}
			
			// 페이징 파라미터 바인딩
			psmt.setInt(paramIndex++, paging.getEndRow());
			psmt.setInt(paramIndex, paging.getStartRow());
			
			rs = psmt.executeQuery();
			
			while (rs.next()) {
				Board board = new Board();
				board.setBno(rs.getInt("bno"));
				board.setTitle(rs.getString("title"));
				board.setWriter(rs.getString("writer"));
				board.setContent(rs.getString("content"));
				board.setRegDate(rs.getTimestamp("reg_date"));
				board.setUpdDate(rs.getTimestamp("upd_date"));
				
				boardList.add(board);
			}
		} catch (SQLException e) {
			System.err.println("검색 페이징 게시글 목록 조회 시, 예외 발생");
			e.printStackTrace();
		}
		
		return boardList;
	}

}
