package se.foodload.presentation.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;


@Data
public class ClientDTO {
	
	@NotNull(message = "{personDto.firstName.missing}")
	@NotBlank(message = "{personDto.firstName.blank}")
	private String username;
	@NotNull(message = "{personDto.firstName.missing}")
	@NotBlank(message = "{personDto.firstName.blank}")
	private String firebase_id;
	
	private String email;
	
	
	public ClientDTO(String username, String firebase_id, String email) {
		this.username=username;
		this.firebase_id= firebase_id;
		this.email= email;
	}	
	// If there is no email ( for future authentication purpouses)
	public ClientDTO(String username, String firebase_id) {
		this.username=username;
		this.firebase_id= firebase_id;
		
	}	
	public ClientDTO() {
	
	}	
}
