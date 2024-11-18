package com.lessonlink.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostDto {
    private String title;
    private Boolean isNotice;
    private String contents;
}
