package com.lessonlink.domain.post;

import com.lessonlink.domain.common.BaseTimeEntity;
import com.lessonlink.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@ToString
public class Post extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @JoinColumn(name = "member_id_secret_key")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
    private String title;
    private Boolean isNotice;
    private String contents;
    private LocalDateTime createdAt;

    private Integer views;

    @JoinColumn(name = "post_category_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private PostCategory postCategory;
}
