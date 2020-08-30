package se.foodload.presentation.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ItemModel {

	@NotNull(message = "qrCode missing")
	@NotBlank(message = "qrCode cannot be blank")
	private String qrCode;
	private String name;
	private String storageType;

	private String newStorageType;
	private int amount;
	// private final String brand;
	// private final String qrCode;

	public ItemModel() {

	}
}
