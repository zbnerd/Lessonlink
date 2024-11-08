package com.lessonlink.domain.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lessonlink.domain.item.embedded.Duration;
import com.lessonlink.domain.item.embedded.Period;
import com.lessonlink.domain.item.embedded.TimeRange;
import com.lessonlink.domain.item.enums.CourseLevel;
import com.lessonlink.domain.item.enums.CourseType;
import com.lessonlink.domain.reservation.Reservation;
import com.lessonlink.dto.ItemDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@ToString
public class Course extends Item {

    private String teacher_member_id;  // 강의자, 추후에 서브쿼리로 선생님 정보 조회하게하기.

    private String description;  // 강의 설명

    @Embedded
    private Period period;

    @Embedded
    private TimeRange timeRange;

    @Embedded
    private Duration duration;

    @Enumerated(EnumType.STRING)
    private CourseLevel level;  // 강의 레벨 (BEGINNER, INTERMEDIATE, ADVANCED)

    @Enumerated(EnumType.STRING)
    private CourseType courseType;  // 강의 유형 (ONLINE, OFFLINE, HYBRID)
    private String materialUrl;  // 강의 자료 URL

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "course")
    private List<Reservation> reservations = new ArrayList<>();

    public void setCourseInfo(ItemDto itemDto) {

        super.setItemInfo(itemDto);

        this.teacher_member_id = itemDto.getTeacher_id();
        this.description = itemDto.getDescription();
        this.period = itemDto.getPeriod();
        this.timeRange = itemDto.getTimeRange();
        this.duration = itemDto.getDuration();
        this.level = itemDto.getLevel();
        this.courseType = itemDto.getCourseType();
        this.materialUrl = itemDto.getMaterialUrl();
    }


}
