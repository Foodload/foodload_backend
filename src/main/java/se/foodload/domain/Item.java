package se.foodload.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
@Entity
@Data
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "itemId")
	private long id;

	String name;
	
	String brand;
	
	String qrCode;
	
	@ManyToOne
	@JoinColumn(name = "itemcategoryId", referencedColumnName = "itemcategoryId")
	ItemCategory itemCategory;
	@ManyToOne
	@JoinColumn(name = "storagetypeId", referencedColumnName = "storagetypeId")
	StorageType storageType;
	
	public Item() {}
	public Item(String name, String brand, String qrCode, ItemCategory itemCategory) {
		this.name = name;
		this.brand = brand;
		this.qrCode = qrCode;
		this.itemCategory = itemCategory;
	}
}
