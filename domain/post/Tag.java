package com.lessonlink.domain.post;


import com.lessonlink.domain.common.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.ToString;

@Entity
@Getter
@ToString
public class Tag extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "tag_id")
    private Long id;
    private String tag;


}
