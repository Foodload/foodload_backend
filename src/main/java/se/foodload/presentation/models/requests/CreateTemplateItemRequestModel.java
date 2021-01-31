package se.foodload.presentation.models.requests;

import lombok.Data;

@Data
public class CreateTemplateItemRequestModel {
    private Long itemId;
    private Integer count;

    public CreateTemplateItemRequestModel(){}
}
