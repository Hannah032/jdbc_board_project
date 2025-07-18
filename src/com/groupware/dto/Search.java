package com.groupware.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 검색 조건을 담는 DTO
 * - 검색 타입(제목, 작성자 등)과 검색어를 관리합니다.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Search {
    private String type;    // 검색 타입 (e.g., "title", "writer", "content", "all")
    private String keyword; // 검색어
}
