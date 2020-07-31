package se.foodload.domain;

import java.util.Optional;

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
@JsonIgnoreProperties(value = { "storageId" }, allowSetters = true)
public class ItemCount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "itemcountId")
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "itemId", referencedColumnName = "itemId")
	@JsonIgnoreProperties(value = { "id" }, allowSetters = true)
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
	public ItemCount(Storage storage, Item item, int count) {
		this.itemId = item;
		this.count = count;
		this.storageId = storage;
	}


	
	public void addItemCount(int ammount) {
		this.count = this.count+ammount;
	}


	public void removeItemCount(int ammount) {
		this.count=this.count-ammount;
		
	}
}
