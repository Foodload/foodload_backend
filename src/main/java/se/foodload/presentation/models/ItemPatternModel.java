package se.foodload.presentation.models;

import lombok.Data;

@Data
public class ItemPatternModel {

	private String name;
	private int index;
	private int start;

	ItemPatternModel() {
	}

	ItemPatternModel(String name, int index, int start) {
		this.name = name;
		this.index = index;
		this.start = start;
	}
}
