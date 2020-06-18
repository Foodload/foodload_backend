package se.foodload.presentation.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;


@Data
public class ClientDTO {
	
	
	private String username;
	private String firebaseId;
	private String email;
	
	
	public ClientDTO(String username, String firebaseId, String email) {
		this.username=username;
		this.firebaseId= firebaseId;
		this.email= email;
	}	
	// If there is no email ( for future authentication purpouses)
	public ClientDTO(String username, String firebaseId) {
		this.username=username;
		this.firebaseId= firebaseId;
		
	}	
	public ClientDTO() {
	
	}	
}
