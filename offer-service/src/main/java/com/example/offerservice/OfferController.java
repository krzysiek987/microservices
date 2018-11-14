package com.example.offerservice;

import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("offers")
public class OfferController {

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
		return repository.insert(offer);
	}

	@PutMapping("{id}")
	public Offer update(@PathVariable UUID id, @RequestBody Offer offer) {
		if (!Objects.equals(id, offer.getId())) {
			throw new IllegalArgumentException("Id mismatch");
		}
		return repository.save(offer);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@PathVariable UUID id) {
		repository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
