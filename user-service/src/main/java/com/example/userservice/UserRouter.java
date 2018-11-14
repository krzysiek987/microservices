package com.example.userservice;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class UserRouter {

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
}
