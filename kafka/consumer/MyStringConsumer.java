package com.lessonlink.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.lessonlink.kafka.model.Topic.MY_SECOND_TOPIC;


@Component
@Slf4j
public class MyStringConsumer {

    @KafkaListener(
            topics = {MY_SECOND_TOPIC},
            groupId = "test-consumer-group",
            containerFactory = "stringKafkaListenerContainerFactory"
    )
    public void accept(ConsumerRecord<String, String> message) {
        log.info("[StringConsumer] Message arrived! - {}", message.value());
        log.info("[StringConsumer] offset - {}, partition - {}", message.offset(), message.partition());
    }
}
