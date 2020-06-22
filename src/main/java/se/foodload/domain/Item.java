package se.foodload.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "item_id")
	private long id;

	String name;
	
	String brand;
	
	String qrCode;
	
	@ManyToOne
	@JoinColumn(name = "itemcategory_id", referencedColumnName = "itemcategory_id")
	ItemCategory itemCategory;
	
	StorageType storageType;
	
	public Item() {}

}
