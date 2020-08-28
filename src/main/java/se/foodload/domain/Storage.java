package se.foodload.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity
@Data
public class Storage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "storageId")
	private long id;

	@ManyToOne
	@JoinColumn(name = "storagetypeId", referencedColumnName = "storagetypeId")
	@JsonIgnoreProperties(value = { "id" }, allowSetters = true)
	StorageType storageType;

	@ManyToOne
	@JoinColumn(name = "familyId", referencedColumnName = "familyId")
	Family familyId;

	public Storage() {

	}

	public Storage(StorageType storageType, Family familyId) {
		this.storageType = storageType;
		this.familyId = familyId;
	}

}
