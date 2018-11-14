package com.example.productservice;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

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

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product insert(@RequestBody Product product){
        return repository.insert(product);
    }

    @PostMapping("{id}")
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
