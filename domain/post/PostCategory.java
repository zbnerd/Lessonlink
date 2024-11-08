package com.lessonlink.domain.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lessonlink.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
public class PostCategory extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "post_category_id")
    private Long id;
    private String categoryName;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "postCategory")
    private List<Post> posts = new ArrayList<>();

    public PostCategory() {
    }

    public PostCategory(String categoryName) {
        this.categoryName = categoryName;
    }
}
