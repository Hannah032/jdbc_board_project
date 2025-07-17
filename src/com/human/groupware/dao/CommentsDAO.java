package com.human.groupware.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.human.groupware.dto.Comment;

/**
 * 데이터 접근 객체
 * - 댓글 데이터를 접근
 */
public class CommentsDAO extends JDBConnection {
	/**
	 * 특정 게시글의 댓글 목록
	 * @param boardNo
	 * @return
	 */
	public List<Comment> listByBoard(int boardNo) {
		// 댓글 목록을 담을 컬렉션 객체 생성
		List<Comment> commentList = new ArrayList<Comment>();
		
		// SQL 작성
		String sql = " select * "
				+ " from comments "
				+ " where board_no = ? "
				+ " order by reg_date asc ";
		
		try {
			// 1. SQL 실행 객체 생성 - PreparedStatement (pstmt)
			psmt = con.prepareStatement(sql);
			
			// ? 동적 파라미터 바인딩
			psmt.setInt(1, boardNo);
			
			// 2. SQL 실행 요청 -> 결과 ResultSet (rs)
			rs = psmt.executeQuery();
			
			// 3. 조회된 결과를 리스트(commentList)에 추가
			while(rs.next()) {
				Comment comment = new Comment();
				
			// 결과 데이터 가져오기
			comment.setBno(rs.getInt("bno"));
			comment.setBoardNo(rs.getInt("board_no"));
			comment.setWriter(rs.getString("writer"));
			comment.setContent(rs.getString("content"));
			comment.setRegDate(rs.getTimestamp("reg_date"));
			comment.setUpdDate(rs.getTimestamp("upd_date"));
			
			commentList.add(comment);
			}
		} catch (SQLException e) {
			System.err.println("댓글 목록 조회 시, 예외 발생");
			e.printStackTrace();
		}
		
		return commentList;
	}
	
	/**
	 * 댓글 조회
	 * @param no
	 * @return
	 */
	public Comment select(int no) {
		// 댓글 정보 객체 생성
		Comment comment = new Comment();
		
		// SQL 작성
		String sql = " select *"
				+ " from comments"
				+ " where bno = ? ";
		
		try {
			// 1. SQL 실행 객체 생성 - PreparedStatement (pstmt)
			psmt = con.prepareStatement(sql);
			
			// ? 동적 파라미터 바인딩
			psmt.setInt(1, no);
			
			// SQL 실행 요청
			rs = psmt.executeQuery();
			
			// 조회 결과 1건 가져오기
			if (rs.next()) {
				// 결과 데이터 가져오기
				comment.setBno(rs.getInt("bno"));
				comment.setBoardNo(rs.getInt("board_no"));
				comment.setWriter(rs.getString("writer"));
				comment.setContent(rs.getString("content"));
				comment.setRegDate(rs.getTimestamp("reg_date"));
				comment.setUpdDate(rs.getTimestamp("upd_date"));
			}
		} catch (SQLException e) {
			System.err.println("댓글 조회 시, 예외 발생");
			e.printStackTrace();
		}
		// 댓글 정보 1건 반환
		return comment;
	}
	
	/**
	 * 댓글 등록
	 * @param comment
	 * @return
	 */
	public int insert(Comment comment) {
		int result = 0;
		
		String sql = " insert into comments (bno, board_no, writer, content) "
					+ " values(SEQ_COMMENT.NEXTVAL, ?, ?, ? )";
		try {
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, comment.getBoardNo());
			psmt.setString(2, comment.getWriter());
			psmt.setString(3, comment.getContent());
			result = psmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println("댓글 생성 시, 예외 발생");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 댓글 수정
	 * @param comment
	 * @return
	 */
	public int update(Comment comment) {
		int result = 0;
		
		String sql = " update comments "
					+ "\t\tset content = ? "
					+ "\t\t\t,upd_date = SYSDATE "
					+ " where bno = ? ";		
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, comment.getContent());
			psmt.setInt(2, comment.getBno());
			result = psmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println("댓글 수정 시, 예외 발생");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 댓글 삭제
	 * @param no
	 * @return
	 */
	public int delete(int no) {
		int result = 0;
		
		String sql = " delete from comments "
					+ " where bno = ? ";	
		try {
			psmt = con.prepareStatement(sql); 
			psmt.setInt(1, no);
			result = psmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println("댓글 삭제 시, 예외 발생");
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 특정 게시글의 모든 댓글 삭제
	 * @param boardNo
	 * @return
	 */
	public int deleteByBoard(int boardNo) {
		int result = 0;
		
		String sql = " delete from comments "
					+ " where board_no = ? ";	
		try {
			psmt = con.prepareStatement(sql); 
			psmt.setInt(1, boardNo);
			result = psmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println("게시글 댓글 삭제 시, 예외 발생");
			e.printStackTrace();
		}
		return result;
	}
}