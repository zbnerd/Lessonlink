package com.lessonlink.kafka.producer;


import com.lessonlink.kafka.model.MyMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static com.lessonlink.kafka.model.Topic.MY_JSON_TOPIC;


@Component
@Slf4j
@RequiredArgsConstructor
public class MyProducer {

    private final KafkaTemplate<String, MyMessage> kafkaTemplate;
    public void sendMessage(MyMessage myMessage) {
        kafkaTemplate.send(MY_JSON_TOPIC, String.valueOf(myMessage.getAge()), myMessage);
    }

}
