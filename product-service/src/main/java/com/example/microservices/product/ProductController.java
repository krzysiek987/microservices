package com.example.microservices.product;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@AllArgsConstructor
@RequestMapping("products")
public class ProductController {

	private ProductRepository productRepository;

	@GetMapping
	public List<Product> getList() {
		return productRepository.findAll();
	}

	@GetMapping("{id}")
	public ResponseEntity<Product> get(@PathVariable UUID id) {
		return productRepository.findById(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Product insert(@RequestBody Product product) {
		return productRepository.insert(product);
	}

	@PutMapping("{id}")
	public Product update(@PathVariable UUID id, @RequestBody Product product) {
		if (!Objects.equals(id, product.getId())) {
			throw new IllegalArgumentException("Id mismatch");
		}
		return productRepository.save(product);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@PathVariable UUID id) {
		productRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
