package se.foodload.presentation.models;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class MoveItemModel {
    @NotNull(message = "itemCountId missing")
    private long itemCountId;

    @NotNull(message = "storageType missing")
    @NotBlank(message = "storageType cannot be blank")
    private String storageType; //srcStorageType or destStorageType depending on moveTo or moveFrom

    private int moveAmount;

    private int oldAmount;

    public MoveItemModel() {

    }
}
