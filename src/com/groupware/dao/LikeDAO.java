package com.groupware.dao;

import java.sql.SQLException;

import com.groupware.dto.Like;

/**
 * `likes` 테이블에 접근하여 좋아요 관련 데이터 처리를 하는 DAO 클래스
 */
public class LikeDAO extends JDBConnection {

    /**
     * 특정 게시글의 좋아요 개수를 조회합니다.
     * @param boardNo 게시글 번호
     * @return 좋아요 개수
     */
    public int countLikes(int boardNo) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM likes WHERE board_no = ?";
        try {
            psmt = con.prepareStatement(sql);
            psmt.setInt(1, boardNo);
            rs = psmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("좋아요 개수 조회 중 예외 발생");
            e.printStackTrace();
        }
        return count;
    }

    /**
     * 특정 사용자가 특정 게시글에 좋아요를 눌렀는지 확인합니다.
     * @param like (boardNo, empNo)
     * @return 좋아요를 눌렀으면 true, 아니면 false
     */
    public boolean isLiked(Like like) {
        boolean liked = false;
        String sql = "SELECT COUNT(*) FROM likes WHERE board_no = ? AND emp_no = ?";
        try {
            psmt = con.prepareStatement(sql);
            psmt.setInt(1, like.getBoardNo());
            psmt.setInt(2, like.getEmpNo());
            rs = psmt.executeQuery();
            if (rs.next()) {
                liked = rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("좋아요 여부 확인 중 예외 발생");
            e.printStackTrace();
        }
        return liked;
    }

    /**
     * 좋아요를 추가합니다.
     * @param like (boardNo, empNo)
     * @return 성공 시 1, 실패 시 0
     */
    public int addLike(Like like) {
        int result = 0;
        String sql = "INSERT INTO likes (board_no, emp_no) VALUES (?, ?)";
        try {
            psmt = con.prepareStatement(sql);
            psmt.setInt(1, like.getBoardNo());
            psmt.setInt(2, like.getEmpNo());
            result = psmt.executeUpdate();
        } catch (SQLException e) {
            // 기본 키 중복 오류(이미 좋아요를 누른 경우)는 무시
            if (e.getErrorCode() != 1) { 
                System.err.println("좋아요 추가 중 예외 발생");
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 좋아요를 취소합니다.
     * @param like (boardNo, empNo)
     * @return 성공 시 1, 실패 시 0
     */
    public int removeLike(Like like) {
        int result = 0;
        String sql = "DELETE FROM likes WHERE board_no = ? AND emp_no = ?";
        try {
            psmt = con.prepareStatement(sql);
            psmt.setInt(1, like.getBoardNo());
            psmt.setInt(2, like.getEmpNo());
            result = psmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("좋아요 취소 중 예외 발생");
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * 게시글 삭제 시, 해당 게시글의 모든 좋아요 기록을 삭제합니다.
     * @param boardNo 게시글 번호
     * @return 삭제된 행의 수
     */
    public int deleteLikesByBoard(int boardNo) {
        int result = 0;
        String sql = "DELETE FROM likes WHERE board_no = ?";
        try {
            psmt = con.prepareStatement(sql);
            psmt.setInt(1, boardNo);
            result = psmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("게시글의 좋아요 전체 삭제 중 예외 발생");
            e.printStackTrace();
        }
        return result;
    }
}
