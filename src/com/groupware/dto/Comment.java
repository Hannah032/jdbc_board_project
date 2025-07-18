package com.groupware.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 댓글 정보
// - 댓글 번호, 게시글 번호, 작성자, 내용, 등록일자, 수정일자
// 
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
	private int bno;
	private int boardNo;
	private String writer;
	private String content;
	private Date regDate;
	private Date updDate;
	
	public Comment(int boardNo, String writer, String content) {
		this.boardNo = boardNo;
		this.writer = writer;
		this.content = content;
	}
}