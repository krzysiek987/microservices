package com.example.offerservice;

import java.util.UUID;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Offer {

	@Id
	private UUID id;
	private String name;

}
