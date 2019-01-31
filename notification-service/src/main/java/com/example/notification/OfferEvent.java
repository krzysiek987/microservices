package com.example.notification;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Value;

@Value
public class OfferEvent {

	private UUID id;
	private String name;
	private BigDecimal value;
	private UUID productId;
	private UUID bidderId;

}
