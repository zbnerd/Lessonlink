package com.lessonlink.kafka.producer;


import com.lessonlink.kafka.model.MyMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Supplier;

@Component
@Slf4j
public class MyProducer implements Supplier<Flux<Message<MyMessage>>> {

    public MyProducer() {
        log.info("MyProducer init!");
    }

    private final Sinks.Many<Message<MyMessage>> sinks = Sinks.many().unicast().onBackpressureBuffer();

    public void sendMessage(MyMessage myMessage) {
        Message<MyMessage> message = MessageBuilder
                .withPayload(myMessage)
                .setHeader(KafkaHeaders.KEY, String.valueOf(myMessage.getAge()))
                .build();

        sinks.emitNext(message, Sinks.EmitFailureHandler.FAIL_FAST);
    }

    @Override
    public Flux<Message<MyMessage>> get() {
        return sinks.asFlux();
    }
}
