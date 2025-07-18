package com.groupware.service;

import java.util.List;
import com.groupware.dto.Board;
import com.groupware.dto.Search;

/**
 * 게시글 검색 관련 비즈니스 로직을 처리하는 서비스 인터페이스
 */
public interface SearchService {

    /**
     * 검색 조건에 맞는 게시글 목록을 조회합니다. (페이징 미적용)
     * @param search 검색 조건
     * @return 검색된 게시글 목록
     */
    List<Board> search(Search search);

    /**
     * 검색 조건과 페이징을 적용하여 게시글 목록을 조회합니다.
     * @param search 검색 조건
     * @param pageNum 현재 페이지 번호
     * @param pageSize 페이지당 게시글 수
     * @return 페이징 처리된 검색 결과 목록
     */
    List<Board> searchWithPaging(Search search, int pageNum, int pageSize);
    
    /**
     * 검색 조건에 맞는 게시글의 총 개수를 조회합니다.
     * @param search 검색 조건
     * @return 검색된 게시글의 총 개수
     */
    int getTotalCount(Search search);

}
