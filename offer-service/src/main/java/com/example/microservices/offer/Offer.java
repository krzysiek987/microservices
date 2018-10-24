package com.example.microservices.offer;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document
public class Offer {

    @Id
    private UUID id;
    private String name;

}
