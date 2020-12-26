package se.foodload.presentation.models;

import lombok.Data;

@Data
public class ItemCountResponse {
    private int amount;

    public ItemCountResponse(int amount){
        this.amount = amount;
    }

    public ItemCountResponse() {}
}
