package se.foodload.dtos;

import lombok.Data;
import se.foodload.domain.ItemCount;

@Data
public class ItemCountDTO {
    private long id;
    private String storageType;
    private ItemDTO item;
    private int amount;

    public ItemCountDTO(long id, String storageType, ItemDTO item, int amount){
        this.id = id;
        this.storageType = storageType;
        this.item = item;
        this.amount = amount;
    }

    public ItemCountDTO(ItemCount itemCount){
        this.id = itemCount.getId();
        this.storageType = itemCount.getStorageType().getName();
        this.item = new ItemDTO(itemCount.getItem());
        this.amount = itemCount.getCount();
    }
}
