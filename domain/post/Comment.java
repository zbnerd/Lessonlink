package com.lessonlink.domain.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lessonlink.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
public class Comment extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    private String contents;

    @Setter
    @JoinColumn(name = "post_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_comment_id")
    private Comment parentComment;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> subComments = new ArrayList<>();
}
