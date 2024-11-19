package com.lessonlink.api.test;

import com.lessonlink.kafka.model.MyMessage;
import com.lessonlink.kafka.producer.MyProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyKafkaController {

    private final MyProducer myProducer;

    @PostMapping("/message")
    void message(
            @RequestBody MyMessage message
    ) {
        myProducer.sendMessage(message);
    }
}
