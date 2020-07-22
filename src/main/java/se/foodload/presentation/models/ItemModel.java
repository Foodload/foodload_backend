package se.foodload.presentation.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ItemModel {

	@NotNull(message = "qrCode missing")
	@NotBlank(message = "qrCode cannot be blank")
	private final String qrCode;

	private final String storageType;

	private final String newStorageType;
	private final int ammount;
	// private final String brand;
	// private final String qrCode;

}
