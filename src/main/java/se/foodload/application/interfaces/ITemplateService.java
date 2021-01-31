package se.foodload.application.interfaces;

import se.foodload.domain.Family;
import se.foodload.domain.Template;
import se.foodload.domain.TemplateItem;
import se.foodload.dtos.BuyItemDTO;
import se.foodload.presentation.models.requests.CreateTemplateItemRequestModel;
import se.foodload.presentation.models.requests.CreateTemplateRequestModel;
import se.foodload.presentation.models.requests.UpdateTemplateItemRequestModel;
import se.foodload.presentation.models.responses.BuyListResponseModel;

import java.util.List;
import java.util.Set;

public interface ITemplateService {

    Set<Template> getTemplates(Family family);

    Template createTemplate(Family family, CreateTemplateRequestModel request);

    TemplateItem addNewTemplateItem(Family family, Long templateId, CreateTemplateItemRequestModel request);

    void updateTemplateItem(Family family, UpdateTemplateItemRequestModel request);

    void removeTemplateItem(Family family,  Long templateId, Long templateItemId);

    void deleteTemplate(Family family, Long templateId);

    BuyListResponseModel generateBuyList(Family family, Long templateId);

}
