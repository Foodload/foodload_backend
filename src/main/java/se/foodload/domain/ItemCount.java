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
@JsonIgnoreProperties(value = { "Storage" }, allowSetters = true)
public class ItemCount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "itemcountId")
	private long id;

	@ManyToOne
	@JoinColumn(name = "itemId", referencedColumnName = "itemId")
	@JsonIgnoreProperties(value = { "id" }, allowSetters = true)
	Item item;

	int count;

	@ManyToOne
	@JoinColumn(name = "storageId", referencedColumnName = "storageId")
	Storage storage;

	public ItemCount() {

	}

	public ItemCount(Storage storage, Item item) {
		this.item = item;
		this.count = this.count++;
		this.storage = storage;
	}

	public ItemCount(Storage storage, Item item, int count) {
		this.item = item;
		this.count = count;
		this.storage = storage;
	}

	public void incrementItemCount() {
		this.count++;
	}

	public void decrementItemCount() {
		this.count--;
	}

	public void addItemCount(int amount) {
		this.count = this.count + amount;
	}

	public void removeItemCount(int amount) {
		this.count = this.count - amount;

	}
}
