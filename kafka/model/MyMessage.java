package com.lessonlink.kafka.model;

import lombok.Data;

@Data
public class MyMessage {
    private long id;
    private int age;
    private String name;
    private String content;
}
