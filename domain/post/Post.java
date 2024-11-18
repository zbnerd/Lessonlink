package com.lessonlink.domain.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lessonlink.domain.common.BaseTimeEntity;
import com.lessonlink.domain.member.Member;
import com.lessonlink.dto.builder.PostDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Setter
    private LocalDateTime createdAt;

    private int views;
    private int likes;

    @JoinColumn(name = "post_category_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private PostCategory postCategory;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "post")
    private List<PostTag> postTags = new ArrayList<>();

    //== 연관관계 메서드 ==//
    public void setMember(Member member) {
        this.member = member;
        member.getPosts().add(this);
    }

    public void setPostCategory(PostCategory postCategory) {
        this.postCategory = postCategory;
        postCategory.getPosts().add(this);
    }

    public void addPostTag(PostTag postTag) {
        postTags.add(postTag);
        postTag.setPost(this);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setPost(this);
    }

    public void setPostInfo(PostDto postDto) {
        this.title = postDto.getTitle();
        this.isNotice = postDto.getIsNotice();
        this.contents = postDto.getContents();
    }

    //== 생성 메서드 ==//
    public static Post createPost(Member member, PostCategory postCategory, PostDto postDto, PostTag... postTags) {
        Post post = new Post();
        post.setMember(member);
        post.setPostCategory(postCategory);
        for (PostTag postTag : postTags) {
            post.addPostTag(postTag);
        }

        post.setPostInfo(postDto);

        post.setCreatedAt(LocalDateTime.now());


        return post;
    }

    //== 수정 메서드 ==//
    public void updatePost(PostDto postDto) {
        this.title = postDto.getTitle();
        this.isNotice = postDto.getIsNotice();
        this.contents = postDto.getContents();
    }
}
