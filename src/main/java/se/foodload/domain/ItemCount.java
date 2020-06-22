package se.foodload.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class ItemCount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "itemcount_id")
	private long id;
	@ManyToOne
	@JoinColumn(name = "item_id", referencedColumnName = "item_id")
	Item item_id;
	
	int count;

	@ManyToOne
	@JoinColumn(name = "storage_id", referencedColumnName = "storage_id")
	Storage storage_id;
	
	
	public ItemCount() {
		
	}
}
