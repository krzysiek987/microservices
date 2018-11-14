package com.example.productservice;

import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@RequestMapping("products")
public class ProductController {

    private final ProductRepository repository;

    @GetMapping
    public Iterable<Product> getList(){
        return repository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Product> get(@PathVariable UUID id){
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

	@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product insert(@RequestBody Product product){
        return repository.insert(product);
    }

	@PutMapping("{id}")
    public Product update(@PathVariable UUID id, @RequestBody Product product){
        if(!Objects.equals(id, product.getId())) {
            throw new IllegalArgumentException("Id mismatch");
        }
        return repository.save(product);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
