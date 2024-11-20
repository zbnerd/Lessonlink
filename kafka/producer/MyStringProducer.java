package com.lessonlink.kafka.producer;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static com.lessonlink.kafka.model.Topic.MY_SECOND_TOPIC;


@Component
@Slf4j
@RequiredArgsConstructor
public class MyStringProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    public void sendMessage(String key, String myMessage) {
        kafkaTemplate.send(MY_SECOND_TOPIC, key, myMessage);
    }

}
