package se.foodload.presentation.models;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DeleteItemModel {
    @NotNull(message = "itemCountId missing")
    private long itemCountId;

    @NotNull(message = "amount for verification missing")
    private int amount;

    public DeleteItemModel(){}
}
