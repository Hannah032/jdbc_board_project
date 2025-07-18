package com.groupware.dto;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 페이징 정보 클래스
// - 전체 게시글 수, 한 페이지당 게시글 수, 현재 페이지 번호, 전체 페이지 수 등을 관리
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Paging {
	// 페이징 관련 변수들
	private int totalCount;      // 전체 게시글 수
	private int countList;       // 한 페이지당 게시글 수
	private int pageNum;         // 현재 페이지 번호
	private int totalPage;       // 전체 페이지 수
	private int startPage;       // 시작 페이지 번호
	private int endPage;         // 끝 페이지 번호
	private int startRow;        // 시작 행 번호 (DB 쿼리용)
	private int endRow;          // 끝 행 번호 (DB 쿼리용)
	private boolean hasNext;     // 다음 페이지 존재 여부
	private boolean hasPrev;     // 이전 페이지 존재 여부
	
	// 검색 관련 변수들
	private String searchType;   // 검색 타입 (제목, 내용, 작성자 등)
	private String searchKeyword; // 검색 키워드
	
	// 페이징 계산 메서드
	public void calculatePaging() {
		// 전체 페이지 수 계산
		totalPage = (int) Math.ceil((double) totalCount / countList);
		
		// 시작 페이지와 끝 페이지 계산 (페이지 그룹당 10개씩)
		int pageGroup = 10;
		startPage = ((pageNum - 1) / pageGroup) * pageGroup + 1;
		endPage = Math.min(startPage + pageGroup - 1, totalPage);
		
		// 시작 행과 끝 행 계산 (DB 쿼리용)
		startRow = (pageNum - 1) * countList + 1;
		endRow = pageNum * countList;
		
		// 이전/다음 페이지 존재 여부
		hasPrev = pageNum > 1;
		hasNext = pageNum < totalPage;
	}
	
	// 생성자 (페이징 계산 포함)
	public Paging(int totalCount, int countList, int pageNum) {
		this.totalCount = totalCount;
		this.countList = countList;
		this.pageNum = pageNum;
		calculatePaging();
	}
	
	// 검색 조건이 포함된 생성자
	public Paging(int totalCount, int countList, int pageNum, String searchType, String searchKeyword) {
		this.totalCount = totalCount;
		this.countList = countList;
		this.pageNum = pageNum;
		this.searchType = searchType;
		this.searchKeyword = searchKeyword;
		calculatePaging();
	}
}
