package 게시판.dto;
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
	
	// 페이징 계산 메서드 (미사용)
	public void calculatePaging() {
		// --- 전체 페이지 수 계산 ---
		// totalCount: 전체 게시물 수 (예: 123개)
		// countList: 한 페이지에 보여줄 게시물 수 (예: 10개)
		// (double)로 형변환하여 나눗셈의 결과가 소수점을 포함하도록 합니다. (123 / 10 = 12.3)
		// Math.ceil() 함수는 소수점 이하를 올림하여 정수로 만듭니다. (12.3 -> 13.0)
		// 즉, 123개의 게시물을 10개씩 보여주려면 총 13개의 페이지가 필요합니다.
		// 마지막 13페이지에는 3개의 게시물만 표시됩니다.
		totalPage = (int) Math.ceil((double) totalCount / countList);

		// --- 페이지 그룹의 시작 및 끝 페이지 계산 ---
		// pageGroup: 한 화면에 보여줄 페이지 번호의 개수 (예: 5개씩 -> [1][2][3][4][5], [6][7][8][9][10])
		// pageNum: 현재 사용자가 보고 있는 페이지 번호

		// [startPage 계산]
		// 예시: pageNum=3, pageGroup=5
		// 1. (3 - 1) / 5 = 0 (정수 나눗셈)
		// 2. 0 * 5 = 0
		// 3. 0 + 1 = 1  => startPage는 1
		// 예시: pageNum=8, pageGroup=5
		// 1. (8 - 1) / 5 = 1 (정수 나눗셈)
		// 2. 1 * 5 = 5
		// 3. 5 + 1 = 6  => startPage는 6
		// 이 공식은 현재 페이지가 속한 페이지 그룹의 첫 번째 페이지 번호를 찾아냅니다.
		int pageGroup = 5; // 페이지 그룹을 5로 정의
		startPage = ((pageNum - 1) / pageGroup) * pageGroup + 1;

		// [endPage 계산]
		// 예시: startPage=6, pageGroup=5, totalPage=13
		// 1. 6 + 5 - 1 = 10
		// 2. Math.min(10, 13) = 10 => endPage는 10
		// 예시: startPage=11, pageGroup=5, totalPage=13
		// 1. 11 + 5 - 1 = 15
		// 2. Math.min(15, 13) = 13 => endPage는 13
		// 기본적으로 시작 페이지에서 그룹 크기만큼 더해 끝 페이지를 구하지만,
		// 계산된 끝 페이지가 실제 전체 페이지 수보다 클 경우, 전체 페이지 수를 끝 페이지로 설정합니다.
		// 이는 존재하지 않는 페이지 번호가 표시되는 것을 방지합니다.
		endPage = Math.min(startPage + pageGroup - 1, totalPage);

		// --- DB 쿼리용 시작/끝 행 번호 계산 ---
		// 이 값들은 데이터베이스에서 특정 페이지에 해당하는 데이터를 잘라오기 위해 사용됩니다.
		// (예: Oracle의 ROWNUM, MySQL의 LIMIT)

		// [startRow 계산]
		// 예시: pageNum=1, countList=10
		// 1. (1 - 1) * 10 + 1 = 1 => 1번 행부터
		// 예시: pageNum=3, countList=10
		// 1. (3 - 1) * 10 + 1 = 21 => 21번 행부터
		// 현재 페이지 이전 페이지들의 모든 게시물 수를 건너뛰고, 현재 페이지의 첫 번째 게시물 행 번호를 계산합니다.
		startRow = (pageNum - 1) * countList + 1;

		// [endRow 계산]
		// 예시: pageNum=1, countList=10
		// 1. 1 * 10 = 10 => 10번 행까지
		// 예시: pageNum=3, countList=10
		// 1. 3 * 10 = 30 => 30번 행까지
		// 현재 페이지의 마지막 게시물 행 번호를 계산합니다.
		endRow = pageNum * countList;

		// --- 이전/다음 페이지 존재 여부 확인 ---
		// 이 boolean 값들은 화면에 '< 이전' 또는 '다음 >' 링크를 표시할지 여부를 결정하는 데 사용됩니다.

		// [hasPrev 계산]
		// 현재 페이지가 1보다 크면(즉, 2페이지 이상이면) 항상 '이전' 페이지가 존재합니다.
		// pageNum > 1 의 결과는 true 또는 false 입니다.
		hasPrev = pageNum > 1;

		// [hasNext 계산]
		// 현재 페이지가 전체 페이지 수보다 작으면 항상 '다음' 페이지가 존재합니다.
		// pageNum < totalPage 의 결과는 true 또는 false 입니다.
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
