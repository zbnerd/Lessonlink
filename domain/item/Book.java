package com.lessonlink.domain.item;

import com.lessonlink.dto.ItemDto;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
public class Book extends Item {

    private String author;
    private String isbn;
    private String publisher;     // 출판사
    private LocalDate publishedDate; // 출판일
    private int pageCount;        // 페이지 수

    @Enumerated(EnumType.STRING)
    private BookFormat format;        // 책 형식 (HARDCOVER, PAPERBACK, EBOOK)

    @Enumerated(EnumType.STRING)
    private BookLanguage language;      // 책의 언어
    private String summary;       // 책의 요약/설명

    public void setBookInfo(ItemDto itemDto) {

        super.setItemInfo(itemDto);

        this.author = itemDto.getAuthor();
        this.isbn = itemDto.getIsbn();
        this.publisher = itemDto.getPublisher();
        this.publishedDate = itemDto.getPublishedDate();
        this.pageCount = itemDto.getPageCount();
        this.format = itemDto.getFormat();
        this.language = itemDto.getLanguage();
        this.summary = itemDto.getSummary();

    }

}