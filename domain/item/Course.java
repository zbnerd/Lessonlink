package com.lessonlink.domain.item;

import com.lessonlink.dto.ItemDto;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
public class Course extends Item {

    private String teacher;  // 강의자

    private String description;  // 강의 설명

    private LocalDate startDate;  // 시작일
    private LocalDate endDate;  // 종료일
    private LocalTime startTime;  // 시작 시간
    private LocalTime endTime;  // 종료 시간

    private int durationHour;  // 강의 시간 (예: 총 10시간)

    private int durationMinute;

    @Enumerated(EnumType.STRING)
    private CourseLevel level;  // 강의 레벨 (BEGINNER, INTERMEDIATE, ADVANCED)

    @Enumerated(EnumType.STRING)
    private CourseType courseType;  // 강의 유형 (ONLINE, OFFLINE, HYBRID)
    private String materialUrl;  // 강의 자료 URL

    public void setCourseInfo(ItemDto itemDto) {

        super.setItemInfo(itemDto);

        this.teacher = itemDto.getTeacher();
        this.description = itemDto.getDescription();
        this.startDate = itemDto.getStartDate();
        this.endDate = itemDto.getEndDate();
        this.startTime = itemDto.getStartTime();
        this.endTime = itemDto.getEndTime();
        this.durationHour = itemDto.getDurationHour();
        this.durationMinute = itemDto.getDurationMinute();
        this.level = itemDto.getLevel();
        this.courseType = itemDto.getCourseType();
        this.materialUrl = itemDto.getMaterialUrl();

    }

}
