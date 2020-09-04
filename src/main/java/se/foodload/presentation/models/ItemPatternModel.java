package se.foodload.presentation.models;

import lombok.Data;

@Data
public class ItemPatternModel {

	private String name;
	private int start;

	ItemPatternModel() {
	}

	ItemPatternModel(String name, int start) {
		this.name = name;
		this.start = start;
	}
}
