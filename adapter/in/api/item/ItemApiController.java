package com.lessonlink.adapter.in.api.item;

import com.lessonlink.adapter.in.api.Result;
import com.lessonlink.domain.item.*;
import com.lessonlink.domain.item.embedded.Duration;
import com.lessonlink.domain.item.embedded.Period;
import com.lessonlink.domain.item.embedded.TimeRange;
import com.lessonlink.domain.item.enums.BookFormat;
import com.lessonlink.domain.item.enums.BookLanguage;
import com.lessonlink.domain.item.enums.CourseLevel;
import com.lessonlink.domain.item.enums.CourseType;
import com.lessonlink.dto.ItemDto;
import com.lessonlink.service.ItemService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemApiController {

    private final ItemService itemService;

    /**
     * 새로운 책(Book) 아이템을 생성하는 HTTP 요청을 처리하는 메서드입니다.
     * 요청으로부터 전달된 책 정보를 기반으로 `Book` 객체를 생성하고,
     * 해당 책 정보를 서비스 계층을 통해 데이터베이스에 저장합니다.
     * 저장이 완료되면 생성된 책의 ID를 응답으로 반환합니다.
     *
     * @param request 클라이언트로부터 전달된 책 정보를 담고 있는 요청 객체
     *                 - author: 저자
     *                 - isbn: ISBN 번호
     *                 - publisher: 출판사
     *                 - publishedDate: 출판일
     *                 - pageCount: 페이지 수
     *                 - format: 책의 형식 (HARDCOVER, PAPERBACK, EBOOK)
     *                 - language: 책의 언어
     *                 - summary: 책의 요약
     *                 - name: 아이템 이름
     *                 - price: 아이템 가격
     *                 - stockQuantity: 재고 수량
     *
     * @return `CreateItemResponse` 객체, 생성된 책의 ID를 포함하여 반환
     */
    @PostMapping("/api/v1/items/book")
    public ItemResponse createBook(
            @RequestBody @Valid BookRequest request
    ) {
        Book book = new Book();
        book.setBookInfo(buildBookInfo(request));

        Long bookId = itemService.saveItem(book);
        return new ItemResponse(bookId);
    }

    /**
     * 새로운 강의(Course) 아이템을 생성하는 HTTP POST 요청을 처리하는 메서드입니다.
     * 요청으로부터 전달된 강의 정보를 기반으로 `Course` 객체를 생성하고,
     * 해당 강의 정보를 서비스 계층을 통해 데이터베이스에 저장합니다.
     * 저장이 완료되면 생성된 강의의 ID를 응답으로 반환합니다.
     *
     * @param request 클라이언트로부터 전달된 강의 정보를 담고 있는 요청 객체
     *                 - teacher: 강사명
     *                 - description: 강의 설명
     *                 - startDate: 강의 시작일
     *                 - endDate: 강의 종료일
     *                 - startTime: 강의 시작 시간
     *                 - endTime: 강의 종료 시간
     *                 - durationHour: 강의 시간(시 단위)
     *                 - durationMinute: 강의 시간(분 단위)
     *                 - level: 강의 난이도 (BEGINNER, INTERMEDIATE, ADVANCED)
     *                 - courseType: 강의 유형 (ONLINE, OFFLINE, HYBRID)
     *                 - materialUrl: 강의 자료 URL
     *                 - name: 강의 이름
     *                 - price: 강의 가격
     *                 - stockQuantity: 재고 수량(Course의 경우 남은 강의 자릿수)
     *
     * @return `CreateItemResponse` 객체로 생성된 강의의 ID를 포함하여 반환
     */
    @PostMapping("/api/v1/items/course")
    public ItemResponse createCourse(
            @RequestBody @Valid CourseRequest request
    ) {
        Course course = new Course();
        course.setCourseInfo(buildCourseInfo(request));

        Long courseId = itemService.saveItem(course);
        return new ItemResponse(courseId);
    }

    /**
     * 책(Book) 아이템 정보를 업데이트하는 HTTP PUT 요청을 처리하는 메서드입니다.
     * 클라이언트로부터 전달된 요청 데이터를 기반으로 해당 ID에 맞는 책 정보를 수정합니다.
     * 수정된 후에는 수정된 책의 ID를 응답으로 반환합니다.
     *
     * @param id 수정할 책의 고유 식별자 (PathVariable로 전달)
     * @param request 클라이언트로부터 전달된 책 정보를 담고 있는 요청 객체
     *                 - name: 책 이름
     *                 - price: 가격
     *                 - stockQuantity: 재고 수량
     *                 - author: 저자
     *                 - isbn: ISBN 번호
     *                 - publisher: 출판사
     *                 - publishedDate: 출판일
     *                 - pageCount: 페이지 수
     *                 - format: 책의 형식 (EBOOK, PAPERBACK, HARDCOVER 등)
     *                 - language: 책의 언어
     *                 - summary: 책 요약
     *
     * @return `ItemResponse` 객체로, 수정된 책의 ID를 포함하여 반환
     */
    @PutMapping("/api/v1/items/book/{id}")
    public ItemResponse updateBook(
            @PathVariable Long id,
            @RequestBody @Valid BookRequest request
    ) {
        itemService.updateBook(id, buildBookInfo(request));

        Item foundBookId = itemService.findOne(id);
        return new ItemResponse(foundBookId.getId());
    }

    /**
     * 강의(Course) 아이템 정보를 업데이트하는 HTTP PUT 요청을 처리하는 메서드입니다.
     * 클라이언트로부터 전달된 요청 데이터를 기반으로 해당 ID에 맞는 강의 정보를 수정합니다.
     * 수정된 후에는 수정된 강의의 ID를 응답으로 반환합니다.
     *
     * @param id 수정할 강의의 고유 식별자 (PathVariable로 전달)
     * @param request 클라이언트로부터 전달된 강의 정보를 담고 있는 요청 객체
     *                 - name: 강의 이름
     *                 - price: 강의 가격
     *                 - stockQuantity: 강의 재고 수량
     *                 - teacher: 강사명
     *                 - description: 강의 설명
     *                 - startDate: 강의 시작일
     *                 - endDate: 강의 종료일
     *                 - startTime: 강의 시작 시간
     *                 - endTime: 강의 종료 시간
     *                 - durationHour: 강의 시간 (시 단위)
     *                 - durationMinute: 강의 시간 (분 단위)
     *                 - level: 강의 난이도 (BEGINNER, INTERMEDIATE, ADVANCED)
     *                 - courseType: 강의 유형 (ONLINE, OFFLINE, HYBRID)
     *                 - materialUrl: 강의 자료 URL
     *
     * @return `ItemResponse` 객체로, 수정된 강의의 ID를 포함하여 반환
     */
    @PutMapping("/api/v1/items/course/{id}")
    public ItemResponse updateCourse(
            @PathVariable Long id,
            @RequestBody @Valid CourseRequest request
    ) {
        itemService.updateCourse(id, buildCourseInfo(request));

        Item foundCourseId = itemService.findOne(id);
        return new ItemResponse(foundCourseId.getId());
    }

    /**
     * 모든 아이템(Item) 정보를 조회하는 HTTP GET 요청을 처리하는 메서드입니다.
     * 이 메서드는 서비스 계층을 통해 전체 아이템 리스트를 조회한 후,
     * 각 아이템의 이름, 가격, 재고 수량을 `ItemInfo` 객체에 담아 반환합니다.
     * 최종적으로 `Result` 객체로 리스트를 감싸서 응답합니다.
     *
     * @return `Result` 객체로, 조회된 아이템 리스트(`ItemInfo` 객체 리스트)를 포함하여 반환
     */
    @GetMapping("/api/v1/items")
    public Result allItems() {
        List<Item> findItems = itemService.findItems();
        List<ItemInfo> collect = findItems.stream()
                .map(i -> new ItemInfo(
                        i.getName(),
                        i.getPrice(),
                        i.getStockQuantity()
                )).toList();
        return new Result(collect);
    }

    /**
     * 특정 ID에 해당하는 아이템(Item) 정보를 조회하는 HTTP GET 요청을 처리하는 메서드입니다.
     * 전달된 아이템 ID를 기반으로 서비스 계층에서 해당 아이템을 조회한 후,
     * 조회된 아이템 정보를 `Result` 객체로 감싸서 응답합니다.
     *
     * @param id 조회할 아이템의 고유 식별자 (PathVariable로 전달)
     * @return `Result` 객체로, 조회된 아이템 정보를 포함하여 반환
     */
    @GetMapping("/api/v1/items/{id}")
    public Result findItemById(@PathVariable Long id) {
        Item foundItem = itemService.findOne(id);
        return new Result(foundItem);
    }

    /**
     * BookRequest 객체를 통해 `ItemDto` 객체를 생성하는 메서드입니다.
     * @param request 책 아이템 생성을 위한 요청 객체
     * @return ItemDto 객체로 생성된 책 정보를 포함
     */
    private static ItemDto buildBookInfo(BookRequest request) {
        return new ItemDto.BookBuilder()
                .author(request.getAuthor())
                .isbn(request.getIsbn())
                .publisher(request.getPublisher())
                .publishedDate(request.getPublishedDate())
                .pageCount(request.getPageCount())
                .format(request.getFormat())
                .language(request.getLanguage())
                .summary(request.getSummary())
                .name(request.getName())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .build();
    }

    /**
     * CourseRequest 객체를 통해 `ItemDto` 객체를 생성하는 메서드입니다.
     * @param request 강의 아이템 생성을 위한 요청 객체
     * @return ItemDto 객체로 생성된 강의 정보를 포함
     */
    private static ItemDto buildCourseInfo(CourseRequest request) {
        return new ItemDto.CourseBuilder()
                .teacherId(request.getTeacher())
                .description(request.getDescription())
                .period(new Period(request.getStartDate(), request.getEndDate()))
                .timeRange(new TimeRange(request.getStartTime(), request.getEndTime()))
                .duration(new Duration(request.getDurationHour(), request.getDurationMinute()))
                .level(request.getLevel())
                .courseType(request.getCourseType())
                .materialUrl(request.getMaterialUrl())
                .name(request.getName())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .build();
    }

    /**
     * 아이템 생성 시 공통 필드를 포함하는 요청 데이터를 담는 클래스입니다.
     */
    @Data
    static class ItemRequest {

        @NotBlank(message = "Item name is required")
        private String name;
        @NotNull(message = "Price is required")
        @Positive(message = "Price must be greater than 0")
        private int price;

        @NotNull(message = "Stock quantity is required")
        @Positive(message = "Stock quantity must be greater than 0")
        private int stockQuantity;
    }

    /**
     * 강의(Course) 아이템 생성을 위한 요청 데이터를 담는 클래스입니다.
     */
    @Data
    static class CourseRequest extends ItemRequest {

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
    }

    /**
     * 책(Book) 아이템 생성을 위한 요청 데이터를 담는 클래스입니다.
     */
    @Data
    static class BookRequest extends ItemRequest {

        private String author;
        private String isbn;
        private String publisher;
        private LocalDate publishedDate;
        private int pageCount;
        private BookFormat format;
        private BookLanguage language;
        private String summary;
    }

    /**
     * 아이템 생성 및 수정 요청의 응답 데이터를 담는 클래스입니다.
     */
    @Data
    @AllArgsConstructor
    static class ItemResponse {
        private Long id;
    }

    /**
     * 모든 아이템을 조회할 때 반환하는 기본 정보를 담는 클래스입니다.
     */
    @Data
    @AllArgsConstructor
    static class ItemInfo {
        private String name;
        private int price;
        private int stockQuantity;
    }

    /**
     * 강의 아이템 조회 시 반환하는 세부 정보를 담는 클래스입니다.
     */
    @Data
    @AllArgsConstructor
    static class CourseInfo {

        private String name;
        private int price;
        private int stockQuantity;

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
    }

    /**
     * 책 아이템 조회 시 반환하는 세부 정보를 담는 클래스입니다.
     */
    @Data
    @AllArgsConstructor
    static class BookInfo {

        private String name;
        private int price;
        private int stockQuantity;

        private String author;
        private String isbn;
        private String publisher;
        private LocalDate publishedDate;
        private int pageCount;
        private BookFormat format;
        private BookLanguage language;
        private String summary;
    }
}
