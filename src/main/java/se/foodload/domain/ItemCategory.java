package se.foodload.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class ItemCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "itemcategoryId")
	private long id;

	String name;

	public ItemCategory() {
	}

	public ItemCategory(String name) {
		this.name = name;
	}
}
