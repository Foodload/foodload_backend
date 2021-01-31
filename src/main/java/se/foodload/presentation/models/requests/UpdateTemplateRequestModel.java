package se.foodload.presentation.models.requests;

import lombok.Data;

import java.util.List;

@Data
public class UpdateTemplateRequestModel {
    private Long templateId;
    private String name;
    private List<CreateTemplateItemRequestModel> newTemplateItems;
    private List<UpdateTemplateItemRequestModel> updatedTemplateItems;

    public UpdateTemplateRequestModel(){}
}
