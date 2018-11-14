package com.example.offerservice;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequiredArgsConstructor
@RequestMapping("offers")
public class OfferController {

    private final OfferRepository repository;

    @GetMapping
    public Flux<Offer> getList(){
        return repository.findAll();
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<Offer>> get(@PathVariable UUID id){
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(notFound().build());
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Offer> insert(@RequestBody Offer request){
        return repository.insert(request);
    }

    @PostMapping("{id}")
    public Mono<Offer> update(@PathVariable UUID id, @RequestBody Offer reuqest){
        if(!Objects.equals(id, reuqest.getId())) {
            throw new IllegalArgumentException("Id mismatch");
        }
        return repository.save(reuqest);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable UUID id){
        return repository.deleteById(id);
    }
}
