package com.lessonlink.domain.member;

import com.lessonlink.domain.common.Address;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id
    private Long id;

    private String name;

    @Embedded
    private Address address;

}
