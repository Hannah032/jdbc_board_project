package com.groupware.service;

import com.groupware.dto.Paging;

/**
 * 페이징 관련 비즈니스 로직 구현 클래스
 */
public class PagingServiceImpl implements PagingService {
	
	@Override
	public Paging createPaging(int totalCount, int countList, int pageNum) {
		// 페이지 번호가 1보다 작으면 1로 설정
		if (pageNum < 1) {
			pageNum = 1;
		}
		
		Paging paging = new Paging();
		paging.setTotalCount(totalCount);
		paging.setCountList(countList);
		paging.setPageNum(pageNum);
		paging.calculatePaging();
		return paging;
	}
	
	@Override
	public Paging createPagingWithSearch(int totalCount, int countList, int pageNum, 
										String searchType, String searchKeyword) {
		// 페이지 번호가 1보다 작으면 1로 설정
		if (pageNum < 1) {
			pageNum = 1;
		}
		
		Paging paging = new Paging();
		paging.setTotalCount(totalCount);
		paging.setCountList(countList);
		paging.setPageNum(pageNum);
		paging.setSearchType(searchType);
		paging.setSearchKeyword(searchKeyword);
		paging.calculatePaging();
		return paging;
	}
	
	@Override
	public String createPageNavigation(Paging paging, String url) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("<div class='pagination'>");
		
		// 이전 페이지 버튼
		if (paging.isHasPrev()) {
			sb.append("<a href='").append(url.replace("{page}", String.valueOf(paging.getPageNum() - 1)))
			  .append("' class='page-link'>&laquo; 이전</a>");
		}
		
		// 페이지 번호들
		for (int i = paging.getStartPage(); i <= paging.getEndPage(); i++) {
			if (i == paging.getPageNum()) {
				sb.append("<span class='page-link active'>").append(i).append("</span>");
			} else {
				sb.append("<a href='").append(url.replace("{page}", String.valueOf(i)))
				  .append("' class='page-link'>").append(i).append("</a>");
			}
		}
		
		// 다음 페이지 버튼
		if (paging.isHasNext()) {
			sb.append("<a href='").append(url.replace("{page}", String.valueOf(paging.getPageNum() + 1)))
			  .append("' class='page-link'>다음 &raquo;</a>");
		}
		
		sb.append("</div>");
		
		return sb.toString();
	}
	
	@Override
	public String createPageNavigationWithSearch(Paging paging, String url) {
		StringBuilder sb = new StringBuilder();
		
		// 검색 파라미터 추가
		String searchParams = "";
		if (paging.getSearchType() != null && paging.getSearchKeyword() != null) {
			searchParams = "&searchType=" + paging.getSearchType() + 
						  "&searchKeyword=" + paging.getSearchKeyword();
		}
		
		sb.append("<div class='pagination'>");
		
		// 이전 페이지 버튼
		if (paging.isHasPrev()) {
			sb.append("<a href='").append(url.replace("{page}", String.valueOf(paging.getPageNum() - 1)))
			  .append(searchParams).append("' class='page-link'>&laquo; 이전</a>");
		}
		
		// 페이지 번호들
		for (int i = paging.getStartPage(); i <= paging.getEndPage(); i++) {
			if (i == paging.getPageNum()) {
				sb.append("<span class='page-link active'>").append(i).append("</span>");
			} else {
				sb.append("<a href='").append(url.replace("{page}", String.valueOf(i)))
				  .append(searchParams).append("' class='page-link'>").append(i).append("</a>");
			}
		}
		
		// 다음 페이지 버튼
		if (paging.isHasNext()) {
			sb.append("<a href='").append(url.replace("{page}", String.valueOf(paging.getPageNum() + 1)))
			  .append(searchParams).append("' class='page-link'>다음 &raquo;</a>");
		}
		
		sb.append("</div>");
		
		return sb.toString();
	}
	
	@Override
	public void printPagingInfo(Paging paging) {
		System.out.println("=== 페이징 정보 ===");
		System.out.println("전체 게시글 수: " + paging.getTotalCount());
		System.out.println("한 페이지당 게시글 수: " + paging.getCountList());
		System.out.println("현재 페이지: " + paging.getPageNum());
		System.out.println("전체 페이지 수: " + paging.getTotalPage());
		System.out.println("시작 페이지: " + paging.getStartPage());
		System.out.println("끝 페이지: " + paging.getEndPage());
		System.out.println("시작 행: " + paging.getStartRow());
		System.out.println("끝 행: " + paging.getEndRow());
		System.out.println("이전 페이지 존재: " + paging.isHasPrev());
		System.out.println("다음 페이지 존재: " + paging.isHasNext());
		if (paging.getSearchType() != null && paging.getSearchKeyword() != null) {
			System.out.println("검색 타입: " + paging.getSearchType());
			System.out.println("검색 키워드: " + paging.getSearchKeyword());
		}
		System.out.println("==================");
	}
} 