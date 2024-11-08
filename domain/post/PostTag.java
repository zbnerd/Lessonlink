package com.lessonlink.domain.post;

import com.lessonlink.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@ToString
public class PostTag extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "post_tag_id")
    private Long id;

    @Setter
    @JoinColumn(name = "post_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @Setter
    @JoinColumn(name = "tag_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Tag tag;

    //== 태그 생성 ==//
    public static PostTag createPostTag(Tag tag) {
        PostTag postTag = new PostTag();
        postTag.setTag(tag);
        return postTag;
    }

}
