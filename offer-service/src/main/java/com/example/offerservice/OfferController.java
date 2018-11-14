package com.example.offerservice;

import static org.springframework.http.ResponseEntity.notFound;

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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

	@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Offer> insert(@RequestBody Offer request){
        return repository.insert(request);
    }

	@PutMapping("{id}")
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
