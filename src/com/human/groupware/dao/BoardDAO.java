package com.human.groupware.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.human.groupware.dto.Board;
import com.human.groupware.dto.Paging;
/**
 * 데이터 접근 객체
 * - 게시글 데이터를 데이터베이스에서 직접 조작합니다.
 */
public class BoardDAO extends JDBConnection {

    /**
     * 전체 게시글 목록을 조회합니다.
     * @return 게시글 목록
     */
    public List<Board> list() {
        List<Board> boardList = new ArrayList<>();
        String sql = "SELECT * FROM board ORDER BY reg_date DESC";
        
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
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
            System.err.println("게시글 목록 조회 중 예외 발생");
            e.printStackTrace();
        } finally {
            close();
        }
        return boardList;
    }

    /**
     * 특정 게시글 정보를 조회합니다.
     * @param no 조회할 게시글 번호
     * @return 조회된 게시글 정보
     */
    public Board select(int no) {
        Board board = null;
        String sql = "SELECT * FROM board WHERE bno = ?";

        try {
            psmt = con.prepareStatement(sql);
            psmt.setInt(1, no);
            rs = psmt.executeQuery();
            if (rs.next()) {
                board = new Board();
                board.setBno(rs.getInt("bno"));
                board.setTitle(rs.getString("title"));
                board.setWriter(rs.getString("writer"));
                board.setContent(rs.getString("content"));
                board.setRegDate(rs.getTimestamp("reg_date"));
                board.setUpdDate(rs.getTimestamp("upd_date"));
            }
        } catch (SQLException e) {
            System.err.println("게시글 조회 중 예외 발생");
            e.printStackTrace();
        } finally {
            close();
        }
        return board;
    }

    /**
     * 새로운 게시글을 등록합니다.
     * @param board 등록할 게시글 정보
     * @return 적용된 행의 수
     */
    public int insert(Board board) {
        int result = 0;
        String sql = "INSERT INTO board (bno, title, writer, content) VALUES (SEQ_BOARD.NEXTVAL, ?, ?, ?)";

        try {
            psmt = con.prepareStatement(sql);
            psmt.setString(1, board.getTitle());
            psmt.setString(2, board.getWriter());
            psmt.setString(3, board.getContent());
            result = psmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("게시글 등록 중 예외 발생");
            e.printStackTrace();
        } finally {
            close();
        }
        return result;
    }

    /**
     * 게시글 정보를 수정합니다.
     * @param board 수정할 게시글 정보
     * @return 적용된 행의 수
     */
    public int update(Board board) {
        int result = 0;
        String sql = "UPDATE board SET title = ?, writer = ?, content = ?, upd_date = SYSDATE WHERE bno = ?";

        try {
            psmt = con.prepareStatement(sql);
            psmt.setString(1, board.getTitle());
            psmt.setString(2, board.getWriter());
            psmt.setString(3, board.getContent());
            psmt.setInt(4, board.getBno());
            result = psmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("게시글 수정 중 예외 발생");
            e.printStackTrace();
        } finally {
            close();
        }
        return result;
    }

    /**
     * 게시글을 삭제합니다.
     * @param bno 삭제할 게시글 번호
     * @return 적용된 행의 수
     */
    public int delete(int bno) {
        int result = 0;
        String sql = "DELETE FROM board WHERE bno = ?";

        try  {
            psmt = con.prepareStatement(sql);
            psmt.setInt(1, bno);
            result = psmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("게시글 삭제 중 예외 발생");
            e.printStackTrace();
        } finally {
            close();
        }
        return result;
    }

    /**
     * 페이징을 적용하여 게시글 목록을 조회합니다.
     * @param paging 페이징 정보 객체
     * @return 페이징 처리된 게시글 목록
     */
    public List<Board> listWithPaging(Paging paging) {
        List<Board> boardList = new ArrayList<>();
        String sql = "SELECT * FROM (SELECT ROWNUM AS rn, b.* FROM (SELECT * FROM board ORDER BY reg_date DESC) b) WHERE rn BETWEEN ? AND ?";

        try  {
            psmt = con.prepareStatement(sql);
            psmt.setInt(1, paging.getStartRow());
            psmt.setInt(2, paging.getEndRow());

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
            System.err.println("페이징 게시글 목록 조회 중 예외 발생");
            e.printStackTrace();
        } finally {
            close();
        }
        return boardList;
    }

    /**
     * 검색 조건과 페이징을 적용하여 게시글 목록을 조회합니다.
     * @param paging 페이징 및 검색 정보 객체
     * @return 검색 및 페이징 처리된 게시글 목록
     */
    public List<Board> listWithPagingAndSearch(Paging paging) {
        List<Board> boardList = new ArrayList<>();
        StringBuilder sqlBuilder = new StringBuilder();

        // 1. SQL 쿼리 동적 생성: 검색 조건에 따라 WHERE절 구성
        sqlBuilder.append("SELECT * FROM (SELECT ROWNUM AS rn, b.* FROM (SELECT * FROM board WHERE ");

        // 2. 검색 타입에 따라 WHERE 조건문 추가
        switch (paging.getSearchType()) {
            case "title":
                // 제목에 검색어가 포함된 게시글
                sqlBuilder.append("title LIKE ?");
                break;
            case "writer":
                // 작성자에 검색어가 포함된 게시글
                sqlBuilder.append("writer LIKE ?");
                break;
            case "content":
                // 내용에 검색어가 포함된 게시글
                sqlBuilder.append("content LIKE ?");
                break;
            case "all":
                // 제목 또는 내용에 검색어가 포함된 게시글
                sqlBuilder.append("(title LIKE ? OR content LIKE ?)");
                break;
        }

        // 3. 최신순 정렬 및 ROWNUM 부여, 페이징 범위 조건 추가
        sqlBuilder.append(" ORDER BY reg_date DESC) b) WHERE rn BETWEEN ? AND ?");

        // 4. DB 연결 및 쿼리 실행
        try  {
            
            int paramIndex = 1;
            // 5. 검색어 파라미터 바인딩
            if ("all".equals(paging.getSearchType())) {
                // 'all'은 제목과 내용 두 번 바인딩
                psmt.setString(paramIndex++, "%" + paging.getSearchKeyword() + "%");
                psmt.setString(paramIndex++, "%" + paging.getSearchKeyword() + "%");
            } else {
                // 나머지는 한 번만 바인딩
                psmt.setString(paramIndex++, "%" + paging.getSearchKeyword() + "%");
            }
            // 6. 페이징 범위 바인딩 (시작 행, 끝 행)
            psmt.setInt(paramIndex++, paging.getStartRow());
            psmt.setInt(paramIndex, paging.getEndRow());

            // 7. 쿼리 실행 및 결과 처리
            try (ResultSet rs = psmt.executeQuery()) {
                while (rs.next()) {
                    // 8. 한 행씩 Board 객체로 매핑
                    Board board = new Board();
                    board.setBno(rs.getInt("bno"));
                    board.setTitle(rs.getString("title"));
                    board.setWriter(rs.getString("writer"));
                    board.setContent(rs.getString("content"));
                    board.setRegDate(rs.getTimestamp("reg_date"));
                    board.setUpdDate(rs.getTimestamp("upd_date"));
                    boardList.add(board);
                }
            }
        } catch (SQLException e) {
            System.err.println("검색 페이징 게시글 목록 조회 중 예외 발생");
            e.printStackTrace();
        }
        // 9. 결과 반환
        return boardList;
    }

    /**
     * (검색 조건에 맞는) 전체 게시글 수를 조회합니다.
     * @param paging 페이징 및 검색 정보 객체
     * @return 검색된 게시글 수
     */
    public int getTotalCount(Paging paging) {
        int totalCount = 0;
        
        // 검색 키워드가 비어있지 않은 경우, WHERE 절을 추가
        String searchType = paging.getSearchType();
        String searchKeyword = paging.getSearchKeyword();
        
        StringBuilder sqlBuilder = new StringBuilder("SELECT COUNT(*) FROM board");

        if (searchKeyword != null && !searchKeyword.isEmpty()) {
            sqlBuilder.append(" WHERE ");
            switch (searchType) {
                case "title":
                    sqlBuilder.append("title LIKE ?");
                    break;
                case "writer":
                    sqlBuilder.append("writer LIKE ?");
                    break;
                case "content":
                    sqlBuilder.append("content LIKE ?");
                    break;
                case "all":
                    sqlBuilder.append("(title LIKE ? OR content LIKE ?)");
                    break;
            }
        }

        try  {
            psmt = con.prepareStatement(sqlBuilder.toString());
            
            if (searchKeyword != null && !searchKeyword.isEmpty()) {
                if ("all".equals(searchType)) {
                    psmt.setString(1, "%" + searchKeyword + "%");
                    psmt.setString(2, "%" + searchKeyword + "%");
                } else {
                    psmt.setString(1, "%" + searchKeyword + "%");
                }
            }

            rs = psmt.executeQuery();
            if (rs.next()) {
                totalCount = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("게시글 수 조회 중 예외 발생");
            e.printStackTrace();
        } finally {
            close();
        }
        return totalCount;
    }
}
