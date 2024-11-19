package com.lessonlink.kafka.consumer;

import com.lessonlink.kafka.model.MyMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@Slf4j
public class MyConsumer implements Consumer<Message<MyMessage>> {

    public MyConsumer() {
        log.info("MyConsumer init!");
    }

    @Override
    public void accept(Message<MyMessage> message) {
        log.info("Message arrived! - {}", message.getPayload());
    }
}
