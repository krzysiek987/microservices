package com.example.offerservice;

import java.math.BigDecimal;
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
	private BigDecimal value;
	private UUID productId;
	private UUID bidderId;

}
