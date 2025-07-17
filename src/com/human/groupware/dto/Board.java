package com.human.groupware.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 게시글 정보
// -게시글 번호, 제목, 작성자, 내용, 등록일자, 수정일자
// 
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Board {
	private int bno;
	private String title;
	private String writer;
	private String content;
	private Date regDate;
	private Date updDate;
	
	public Board(String title, String writer, String content) {
		this.title = title;
		this.writer = writer;
		this.content = content;
	}
}