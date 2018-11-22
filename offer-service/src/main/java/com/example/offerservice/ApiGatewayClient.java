package com.example.offerservice;

import java.util.Map;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("api-gateway")
public interface ApiGatewayClient {

	@GetMapping("/users/{id}")
	Map<String, Object> getUser(@PathVariable("id") UUID id);

	@GetMapping("/products/{id}")
	Map<String, Object> getProduct(@PathVariable("id") UUID id);
}