package com.groupware.service;

import java.util.List;

import com.groupware.dao.BoardDAO;
import com.groupware.dto.Board;
import com.groupware.dto.Paging;
import com.groupware.dto.Search;

/**
 * SearchService 인터페이스의 구현 클래스
 */
public class SearchServiceImpl implements SearchService {

    private BoardDAO boardDAO = new BoardDAO();
    private PagingService pagingService = new PagingServiceImpl();

    @Override
    public List<Board> search(Search search) {
        // DAO를 통해 검색 조건에 맞는 게시글 목록을 직접 가져옵니다.
        // 이 예제에서는 페이징과 통합된 메소드를 사용하지만, 
        // 별도의 검색 전용 DAO 메소드를 만들 수도 있습니다.
        // 여기서는 간단하게 페이징 없는 검색은 구현하지 않고 페이징된 검색만 사용하겠습니다.
        return null; 
    }

    @Override
    public List<Board> searchWithPaging(Search search, int pageNum, int pageSize) {
        // 1. 검색 조건에 맞는 전체 게시글 수를 가져옵니다.
        int totalCount = getTotalCount(search);

        // 2. 페이징 객체를 생성합니다.
        Paging paging = pagingService.createPagingWithSearch(totalCount, pageSize, pageNum, search.getType(), search.getKeyword());

        // 3. DAO에 페이징 정보를 전달하여 해당 페이지의 데이터를 요청합니다.
        List<Board> boardList = boardDAO.listWithPagingAndSearch(paging);

        return boardList;
    }

    @Override
    public int getTotalCount(Search search) {
        return boardDAO.getTotalCount(search.getType(), search.getKeyword());
    }
}
