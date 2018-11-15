package com.example.offerservice;

import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OfferRepository extends MongoRepository<Offer, UUID> {

}
