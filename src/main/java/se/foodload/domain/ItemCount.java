package se.foodload.domain;

import java.util.Optional;

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
public class ItemCount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "itemcountId")
	private long id;
	@ManyToOne
	@JoinColumn(name = "itemId", referencedColumnName = "itemId")
	Item itemId;
	
	int count;

	@ManyToOne
	@JoinColumn(name = "storageId", referencedColumnName = "storageId")
	Storage storageId;
	
	
	public ItemCount() {
		
	}


	public ItemCount(Storage storage, Item item) {
		this.itemId = item;
		this.count = this.count++;
		this.storageId = storage;
	}


	
	public void addItemCount() {
		this.count = this.count+1;
	}


	public void removeItemCount() {
		this.count=this.count-1;
		
	}
}
