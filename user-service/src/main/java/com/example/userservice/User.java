package com.example.userservice;

import java.util.UUID;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class User {

	@Id
	private UUID id;
	private String name;
	private String familyName;
	private String email;
}
