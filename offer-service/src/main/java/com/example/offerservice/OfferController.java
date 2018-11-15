package com.example.offerservice;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.IdGenerator;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("offers")
public class OfferController {

	private final HttpClient httpClient = HttpClient.newHttpClient();
	private final OfferRepository repository;
	private final IdGenerator idGenerator;

	@GetMapping
	public Iterable<Offer> getList() {
		return repository.findAll();
	}

	@GetMapping("{id}")
	public ResponseEntity<Offer> get(@PathVariable UUID id) {
		return repository.findById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Offer insert(@RequestBody Offer offer) {
		offer.setId(idGenerator.generateId());

		validateProductId(offer.getProductId());
		validateUserId(offer.getBidderId());

		return repository.insert(offer);
	}

	@PutMapping("{id}")
	public Offer update(@PathVariable UUID id, @RequestBody Offer requestOffer) {
		if (!Objects.equals(id, requestOffer.getId())) {
			throw new IllegalArgumentException("Id mismatch");
		}
		Offer databaseOffer = repository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Incorrect offer id " + id));
		databaseOffer.setValue(requestOffer.getValue());
		return repository.save(requestOffer);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@PathVariable UUID id) {
		repository.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	private void validateProductId(final UUID id) {
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:9000/products/" + id))
				.build();
		try {
			HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
			log.info("Response status for product {} is {}", id, response.statusCode());
			if (!Objects.equals(response.statusCode(), 200)) {
				//return 404 or validation error
			}
		} catch (Exception e) {
			log.error("Error during product validation", e);
			//return 404 or validation error
		}
	}

	private void validateUserId(final UUID id) {
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("http://localhost:9000/users/" + id))
				.build();
		try {
			HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
			log.info("Response status for user {} is {}", id, response.statusCode());
			if (!Objects.equals(response.statusCode(), 200)) {
				//return 404 or validation error
			}
		} catch (Exception e) {
			log.error("Error during user validation", e);
			//return 404 or validation error
		}
	}
}
