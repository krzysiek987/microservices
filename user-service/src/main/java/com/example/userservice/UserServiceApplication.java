package com.example.userservice;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.IdGenerator;
import org.springframework.util.JdkIdGenerator;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@SpringBootApplication
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Bean
	public RouterFunction<ServerResponse> route(UserHandler userHandler) {
		return RouterFunctions
				.route(GET("/users/{id}").and(accept(APPLICATION_JSON)), userHandler::get)
				.andRoute(GET("/users").and(accept(APPLICATION_JSON)), userHandler::all)
				.andRoute(POST("/users").and(accept(APPLICATION_JSON)).and(contentType(APPLICATION_JSON)),
						userHandler::post)
				.andRoute(PUT("/users/{id}").and(accept(APPLICATION_JSON)).and(contentType(APPLICATION_JSON)),
						userHandler::put)
				.andRoute(DELETE("/users/{id}"), userHandler::delete);
	}

	@Bean
	public IdGenerator idGenerator() {
		return new JdkIdGenerator();
	}
}
