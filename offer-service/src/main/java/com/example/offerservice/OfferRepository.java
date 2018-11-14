package com.example.offerservice;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import java.util.UUID;

public interface OfferRepository extends ReactiveMongoRepository<Offer, UUID> {


}
