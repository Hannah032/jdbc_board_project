package com.groupware.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcademicSchedule {
    private int scheduleId;
    private String title;
    private Date startDate;
    private Date endDate;
    private String description;

    /**
     * 
     * @param 학사일정 수정용
     * @param description
     * @param startDate
     * @param endDate
     */
    public AcademicSchedule(String title, String description, Date startDate, Date endDate) {
    	this.title = title;
    	this.description = description;
    	this.startDate = startDate;
    	this.endDate = endDate;
    }
}

