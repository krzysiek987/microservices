package com.example.productservice;

import static org.springframework.http.ResponseEntity.notFound;

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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("products")
public class ProductController {

    private final ProductRepository repository;
	private final IdGenerator idGenerator;

	@GetMapping
	public Flux<Product> getList() {
		return repository.findAll();
	}

	@GetMapping("{id}")
	public Mono<ResponseEntity<Product>> get(@PathVariable UUID id) {
		return repository.findById(id)
				.map(ResponseEntity::ok)
				.defaultIfEmpty(notFound().build());
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<Product> insert(@RequestBody Product request) {
		request.setId(idGenerator.generateId());
		return repository.insert(request);
	}

	@PutMapping("{id}")
	public Mono<Product> update(@PathVariable UUID id, @RequestBody Product reuqest) {
		if (!Objects.equals(id, reuqest.getId())) {
			throw new IllegalArgumentException("Id mismatch");
		}
		return repository.save(reuqest);
	}

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Mono<Void> delete(@PathVariable UUID id) {
		return repository.deleteById(id);
	}
}
