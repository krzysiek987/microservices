package com.example.offerservice;

import static java.util.Objects.isNull;

import java.util.UUID;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.util.IdGenerator;
import org.springframework.util.JdkIdGenerator;

@EnableEurekaClient
@EnableFeignClients
@EnableBinding(Source.class)
@SpringBootApplication
public class OfferServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OfferServiceApplication.class, args);
	}

	@Bean
	public IdGenerator idGenerator() {
		return new JdkIdGenerator();
	}

	@Bean
	public AbstractMongoEventListener uuidGeneratorListener() {
		return new AbstractMongoEventListener<Identifiable<UUID>>() {
			@Override
			public void onBeforeConvert(final BeforeConvertEvent<Identifiable<UUID>> event) {
				Identifiable<UUID> object = event.getSource();
				if (isNull(object.getId())) {
					object.setId(idGenerator().generateId());
				}
			}
		};
	}




}
