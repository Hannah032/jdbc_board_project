package com.groupware.service;

import com.groupware.dao.LikeDAO;
import com.groupware.dto.Like;

/**
 * LikeService 인터페이스의 구현 클래스
 */
public class LikeServiceImpl implements LikeService {

    private LikeDAO likeDAO = new LikeDAO();

    @Override
    public int getLikeCount(int boardNo) {
        return likeDAO.countLikes(boardNo);
    }

    @Override
    public boolean checkLike(Like like) {
        return likeDAO.isLiked(like);
    }

    @Override
    public boolean toggleLike(Like like) {
        // 이미 좋아요를 눌렀는지 확인
        if (likeDAO.isLiked(like)) {
            // 눌렀으면 좋아요 취소
            likeDAO.removeLike(like);
            return false; // 좋아요 취소됨
        } else {
            // 안 눌렀으면 좋아요 추가
            likeDAO.addLike(like);
            return true; // 좋아요 추가됨
        }
    }
    
    @Override
    public void deleteLikesByBoard(int boardNo) {
        likeDAO.deleteLikesByBoard(boardNo);
    }
}
