package se.foodload.application.exception.dto;

import lombok.Data;

@Data
public class ConflictDTO {
	private int newCount;

	public ConflictDTO(int count) {
		this.newCount = count;
	}
}
