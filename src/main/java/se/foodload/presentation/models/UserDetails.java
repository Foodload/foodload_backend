package se.foodload.presentation.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UserDetails {
	@NotNull(message = "Id missing")
	@NotBlank(message = "Id cannot be blank")
	private final String google_id;
	
	@NotNull(message = "Email missing")
	@NotBlank(message = "Email cannot be blank")
	private final String email;
	
	@NotNull(message = "Username missing")
	@NotBlank(message = "Username cannot be blank")
	private final String username;
	
}
