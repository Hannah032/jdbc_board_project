package com.groupware.service;

import com.groupware.dto.Like;

/**
 * 좋아요 기능 관련 비즈니스 로직을 처리하는 서비스 인터페이스
 */
public interface LikeService {

    /**
     * 특정 게시글의 좋아요 개수를 가져옵니다.
     * @param boardNo 게시글 번호
     * @return 좋아요 수
     */
    int getLikeCount(int boardNo);

    /**
     * 사용자가 게시글에 좋아요를 눌렀는지 확인합니다.
     * @param like (boardNo, empNo)
     * @return 좋아요 상태 (true/false)
     */
    boolean checkLike(Like like);

    /**
     * 게시글에 좋아요를 추가/삭제(토글)합니다.
     * @param like (boardNo, empNo)
     * @return 좋아요 추가 시 true, 삭제 시 false
     */
    boolean toggleLike(Like like);
    
    /**
     * 게시글 삭제 시, 연관된 모든 좋아요를 삭제합니다.
     * @param boardNo 게시글 번호
     */
    void deleteLikesByBoard(int boardNo);
}
