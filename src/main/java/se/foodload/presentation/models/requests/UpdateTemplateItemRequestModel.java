package se.foodload.presentation.models.requests;

import lombok.Data;

@Data
public class UpdateTemplateItemRequestModel {
    private Long templateId;
    private Long templateItemId;
    private Integer count;

    public UpdateTemplateItemRequestModel(){}
}
