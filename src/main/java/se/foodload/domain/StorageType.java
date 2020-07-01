package se.foodload.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
@Entity
@Data
public class StorageType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "storagetypeId")
	private long id;

	String name;
	
	public StorageType(String name) {
		this.name = name;
	}
	public StorageType() {
	}
}
