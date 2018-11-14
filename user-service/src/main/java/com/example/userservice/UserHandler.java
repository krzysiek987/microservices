package com.example.userservice;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.ServerResponse.created;
import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.IdGenerator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
class UserHandler {

	private final UserRepository repository;
	private final IdGenerator idGenerator;

	Mono<ServerResponse> get(ServerRequest request) {
		final UUID id = UUID.fromString(request.pathVariable("id"));
		final Mono<User> user = repository.findById(id);
		return user
				.flatMap(p -> ok().contentType(APPLICATION_JSON).body(fromPublisher(user, User.class)))
				.switchIfEmpty(notFound().build());
	}

	Mono<ServerResponse> all(ServerRequest request) {
		return ok().contentType(APPLICATION_JSON)
				.body(fromPublisher(repository.findAll(), User.class));
	}

	Mono<ServerResponse> put(ServerRequest request) {
		final UUID id = UUID.fromString(request.pathVariable("id"));
		final Mono<User> requestUser = request.bodyToMono(User.class);
		final Mono<User> databaseUser = repository.findById(id);

		return Mono.zip(requestUser, databaseUser)
				.map(tuple ->
						tuple.getT2()
								.toBuilder()
								.name(tuple.getT1().getName())
								.familyName(tuple.getT1().getFamilyName())
								.build()
				)
				.flatMap(repository::save)
				.flatMap(saved -> ok().syncBody(saved))
				.switchIfEmpty(notFound().build());
	}

	Mono<ServerResponse> post(ServerRequest request) {
		final Mono<User> requestUser = request.bodyToMono(User.class);
		final UUID id = idGenerator.generateId();
		return created(UriComponentsBuilder.fromPath("users").pathSegment(id.toString()).build().toUri())
				.contentType(APPLICATION_JSON)
				.body(
						fromPublisher(
								requestUser.map(user -> user.toBuilder().id(id).build())
										.flatMap(repository::save),
								User.class
						)
				);
	}

	Mono<ServerResponse> delete(ServerRequest request) {
		final UUID id = UUID.fromString(request.pathVariable("id"));
		return repository
				.findById(id)
				.flatMap(p -> noContent().build(repository.delete(p)))
				.switchIfEmpty(notFound().build());
	}

}
