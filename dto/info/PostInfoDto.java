package com.lessonlink.dto.info;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PostInfoDto {
    private String memberName;
    private String postTitle;
    private String postContents;
    private Boolean isNotice;
    private LocalDateTime createdAt;
    private Integer views;
    private Integer likes;
    private String categoryName;
}
