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
	private String google_id;
	@NotNull(message = "{personDto.firstName.missing}")
	@NotBlank(message = "{personDto.firstName.blank}")
	private String email;
	
	
	public ClientDTO(String username, String google_id, String email) {
		this.username=username;
		this.google_id= google_id;
		this.email= email;
	}	
}
