package se.foodload.presentation.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
@Data
public class ItemModel {
	
	@NotNull(message = "Id missing")
	@NotBlank(message = "Id cannot be blank")
	private final String name;
	
	private final String brand;
	private final String qrCode;
	
	
	
	
	
}
