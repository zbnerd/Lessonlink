package com.lessonlink.domain.db.post;


import com.lessonlink.domain.db.common.BaseTimeEntity;
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
