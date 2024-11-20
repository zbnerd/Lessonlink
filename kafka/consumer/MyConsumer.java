package com.lessonlink.kafka.consumer;

import com.lessonlink.kafka.model.MyMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.lessonlink.kafka.model.Topic.MY_JSON_TOPIC;


@Component
@Slf4j
public class MyConsumer {

    @KafkaListener(
            topics = {MY_JSON_TOPIC},
            groupId = "test-consumer-group"
    )
    public void accept(ConsumerRecord<String, MyMessage> message) {
        log.info("Message arrived! - {}", message.value());
    }
}
