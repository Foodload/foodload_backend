package se.foodload.presentation.models;

import lombok.Data;

@Data
public class MoveItemResponse {
    private int amount;

    public MoveItemResponse(int amount){
        this.amount = amount;
    }

    public MoveItemResponse() {}
}
