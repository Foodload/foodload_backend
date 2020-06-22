package se.foodload.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class StorageType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "storagetype_id")
	private long id;

	String name;
	
	public StorageType(String name) {
		this.name = name;
	}
	public StorageType() {
	}
}
