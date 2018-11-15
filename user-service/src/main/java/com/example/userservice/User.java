package com.example.userservice;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	private UUID id;
	private String email;
	private String name;
	private String familyName;
}
