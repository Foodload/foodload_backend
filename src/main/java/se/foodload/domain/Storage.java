package se.foodload.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class Storage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "storage_id")
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "storagetype_id", referencedColumnName = "storagetype_id")
	StorageType storageType;
	
	@ManyToOne
	@JoinColumn(name = "family_id", referencedColumnName = "family_id")
	Family family_id;
	

	public Storage(){
		
	}
}
