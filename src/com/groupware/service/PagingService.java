package com.groupware.service;

import com.groupware.dto.Paging;

/**
 * 페이징 관련 비즈니스 로직 인터페이스
 * - 페이징 계산, 페이지 네비게이션 생성 등을 담당
 */
public interface PagingService {
	
	/**
	 * 페이징 객체 생성 및 계산
	 * @param totalCount 전체 게시글 수
	 * @param countList 한 페이지당 게시글 수
	 * @param pageNum 현재 페이지 번호
	 * @return 계산된 Paging 객체
	 */
	Paging createPaging(int totalCount, int countList, int pageNum);
	
	/**
	 * 검색 조건이 포함된 페이징 객체 생성
	 * @param totalCount 전체 게시글 수
	 * @param countList 한 페이지당 게시글 수
	 * @param pageNum 현재 페이지 번호
	 * @param searchType 검색 타입
	 * @param searchKeyword 검색 키워드
	 * @return 계산된 Paging 객체
	 */
	Paging createPagingWithSearch(int totalCount, int countList, int pageNum, 
								  String searchType, String searchKeyword);
	
	/**
	 * 페이지 네비게이션 HTML 생성
	 * @param paging 페이징 객체
	 * @param url 페이지 이동할 URL (페이지 번호는 {page}로 표시)
	 * @return HTML 문자열
	 */
	String createPageNavigation(Paging paging, String url);
	
	/**
	 * 검색 조건이 포함된 페이지 네비게이션 HTML 생성
	 * @param paging 페이징 객체
	 * @param url 페이지 이동할 URL
	 * @return HTML 문자열
	 */
	String createPageNavigationWithSearch(Paging paging, String url);
	
	/**
	 * 페이징 정보를 콘솔에 출력
	 * @param paging 페이징 객체
	 */
	void printPagingInfo(Paging paging);
} 