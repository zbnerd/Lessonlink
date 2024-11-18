package com.lessonlink.dto.builder;

import com.lessonlink.domain.db.item.embedded.Duration;
import com.lessonlink.domain.db.item.embedded.Period;
import com.lessonlink.domain.db.item.embedded.TimeRange;
import com.lessonlink.domain.db.item.enums.BookFormat;
import com.lessonlink.domain.db.item.enums.BookLanguage;
import com.lessonlink.domain.db.item.enums.CourseLevel;
import com.lessonlink.domain.db.item.enums.CourseType;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Getter
@Builder
@Slf4j
public class ItemDto {
    // 공통 필드
    private String name;
    private int price;
    private int stockQuantity;

    // Book 관련 필드
    private String author;
    private String isbn;
    private String publisher;
    private LocalDate publishedDate;
    private int pageCount;
    private BookFormat format;
    private BookLanguage language;
    private String summary;

    // Course 관련 필드
    private String teacherId;
    private String description;
    private Period period;
    private TimeRange timeRange;
    private Duration duration;
    private CourseLevel level;
    private CourseType courseType;
    private String materialUrl;
}