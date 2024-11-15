package com.lessonlink.domain.post;


import com.lessonlink.domain.common.BaseTimeEntity;
import com.lessonlink.domain.order.OrderItem;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Entity
@Getter
@ToString
public class Tag extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "tag_id")
    private Long id;
    private String tag;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL)
    private List<PostTag> postTags;

    //== 연관관계 메서드 ==//
    public void addPostTag(PostTag postTag) {
        postTags.add(postTag);
        postTag.setTag(this);
    }

}
