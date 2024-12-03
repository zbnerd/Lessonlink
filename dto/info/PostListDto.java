package com.lessonlink.dto.info;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostListDto {
    private final Long id;
    private final String memberName;
    private final String postTitle;
    private final LocalDateTime createdAt;
}
