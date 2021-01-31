package se.foodload.dtos;

import lombok.Data;
import se.foodload.domain.Item;

@Data
public class BuyItemDTO {
    private Item item;
    private int amount;

    public BuyItemDTO(Item item, int amount){
       this.item = item;
        this.amount = amount;
    }
}
