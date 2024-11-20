package com.lessonlink.api.test;

import com.lessonlink.kafka.model.MyMessage;
import com.lessonlink.kafka.producer.MyProducer;
import com.lessonlink.kafka.producer.MyStringProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyKafkaController {

    private final MyProducer myProducer;
    private final MyStringProducer myStringProducer;

    @PostMapping("/message")
    void message(
            @RequestBody MyMessage message
    ) {
        myProducer.sendMessage(message);
    }

    @PostMapping("/string-message/{key}")
    void message(
            @PathVariable String key,
            @RequestBody String message
    ) {
        myStringProducer.sendMessage(key, message);
    }
}
