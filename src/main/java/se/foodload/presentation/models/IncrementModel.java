package se.foodload.presentation.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class IncrementModel {
	@NotNull(message = "qrCode missing")
	@NotBlank(message = "qrCode cannot be blank")
	private long id;
	
	public IncrementModel() {
		
	}
}
