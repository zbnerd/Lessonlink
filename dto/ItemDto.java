package com.lessonlink.dto;

import com.lessonlink.domain.item.enums.BookFormat;
import com.lessonlink.domain.item.enums.BookLanguage;
import com.lessonlink.domain.item.enums.CourseLevel;
import com.lessonlink.domain.item.enums.CourseType;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Slf4j
public class ItemDto {

    // 공통 필드 (Item에서 상속된 필드)
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
    private String teacher;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private int durationHour;
    private int durationMinute;
    private CourseLevel level;
    private CourseType courseType;
    private String materialUrl;

    // `Builder` 클래스에서 전달된 값을 통해 `ItemDto` 객체를 생성하는 생성자
    private ItemDto(ItemBuilder builder) {

        this.name = builder.name;
        this.price = builder.price;
        this.stockQuantity = builder.stockQuantity;

        // Book 관련 필드
        if (builder instanceof BookBuilder) {
            BookBuilder bookBuilder = (BookBuilder) builder;
            this.author = bookBuilder.author;
            this.isbn = bookBuilder.isbn;
            this.publisher = bookBuilder.publisher;
            this.publishedDate = bookBuilder.publishedDate;
            this.pageCount = bookBuilder.pageCount;
            this.format = bookBuilder.format;
            this.language = bookBuilder.language;
            this.summary = bookBuilder.summary;
        }

        // Course 관련 필드
        if (builder instanceof CourseBuilder) {
            CourseBuilder courseBuilder = (CourseBuilder) builder;
            this.teacher = courseBuilder.teacher;
            this.description = courseBuilder.description;
            this.startDate = courseBuilder.startDate;
            this.endDate = courseBuilder.endDate;
            this.startTime = courseBuilder.startTime;
            this.endTime = courseBuilder.endTime;
            this.durationHour = courseBuilder.durationHour;
            this.durationMinute = courseBuilder.durationMinute;
            this.level = courseBuilder.level;
            this.courseType = courseBuilder.courseType;
            this.materialUrl = courseBuilder.materialUrl;
        }
    }

    // === 공통 필드를 위한 빌더 ===
    public static class ItemBuilder {

        private String name;
        private int price;
        private int stockQuantity;

        public ItemBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ItemBuilder price(int price) {
            this.price = price;
            return this;
        }

        public ItemBuilder stockQuantity(int stockQuantity) {
            this.stockQuantity = stockQuantity;
            return this;
        }

        public ItemDto build() {
            return new ItemDto(this);
        }
    }

    // === Book 관련 필드를 위한 빌더 ===
    public static class BookBuilder extends ItemBuilder {
        private String author;
        private String isbn;
        private String publisher;
        private LocalDate publishedDate;
        private int pageCount;
        private BookFormat format;
        private BookLanguage language;
        private String summary;

        public BookBuilder author(String author) {
            this.author = author;
            return this;
        }

        public BookBuilder isbn(String isbn) {
            this.isbn = isbn;
            return this;
        }

        public BookBuilder publisher(String publisher) {
            this.publisher = publisher;
            return this;
        }

        public BookBuilder publishedDate(LocalDate publishedDate) {
            this.publishedDate = publishedDate;
            return this;
        }

        public BookBuilder pageCount(int pageCount) {
            this.pageCount = pageCount;
            return this;
        }

        public BookBuilder format(BookFormat format) {
            this.format = format;
            return this;
        }

        public BookBuilder language(BookLanguage language) {
            this.language = language;
            return this;
        }

        public BookBuilder summary(String summary) {
            this.summary = summary;
            return this;
        }

        @Override
        public ItemDto build() {
            return new ItemDto(this);
        }
    }

    // === Course 관련 필드를 위한 빌더 ===
    public static class CourseBuilder extends ItemBuilder {
        private String teacher;
        private String description;
        private LocalDate startDate;
        private LocalDate endDate;
        private LocalTime startTime;
        private LocalTime endTime;
        private int durationHour;
        private int durationMinute;
        private CourseLevel level;
        private CourseType courseType;
        private String materialUrl;

        public CourseBuilder teacher(String teacher) {
            this.teacher = teacher;
            return this;
        }

        public CourseBuilder description(String description) {
            this.description = description;
            return this;
        }

        public CourseBuilder startDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public CourseBuilder endDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public CourseBuilder startTime(LocalTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public CourseBuilder endTime(LocalTime endTime) {
            this.endTime = endTime;
            return this;
        }

        public CourseBuilder durationHour(int durationHour) {
            this.durationHour = durationHour;
            return this;
        }

        public CourseBuilder durationMinute(int durationMinute) {
            this.durationMinute = durationMinute;
            return this;
        }

        public CourseBuilder level(CourseLevel level) {
            this.level = level;
            return this;
        }

        public CourseBuilder courseType(CourseType courseType) {
            this.courseType = courseType;
            return this;
        }

        public CourseBuilder materialUrl(String materialUrl) {
            this.materialUrl = materialUrl;
            return this;
        }

        @Override
        public ItemDto build() {
            return new ItemDto(this);
        }
    }
}