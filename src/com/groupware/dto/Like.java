package com.groupware.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 좋아요 정보를 담는 DTO
 * - 어떤 게시글(boardNo)에 어떤 직원(empNo)이 '좋아요'를 눌렀는지 기록합니다.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Like {
    private int boardNo; // 게시글 번호
    private int empNo;   // 좋아요를 누른 직원의 번호
}
