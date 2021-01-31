package se.foodload.presentation.models.requests;

import lombok.Data;

import java.util.List;

@Data
public class CreateTemplateRequestModel {
    private String name;
    //private List<CreateTemplateItemRequestModel> templateItems;

    public CreateTemplateRequestModel(){}
}
