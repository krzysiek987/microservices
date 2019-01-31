package com.example.notification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.util.IdGenerator;
import org.springframework.util.JdkIdGenerator;

@Slf4j
@SpringBootApplication
@EnableBinding(Sink.class)
public class NotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationServiceApplication.class, args);
	}

	@Bean
	public IdGenerator idGenerator() {
		return new JdkIdGenerator();
	}

	@StreamListener(Sink.INPUT)
	public void reactOnOfferEvent(OfferEvent offerEvent) {
		log.info("Received {}", offerEvent);
	}
}
