package se.foodload.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import se.foodload.application.interfaces.IClientService;
import se.foodload.application.interfaces.ITemplateService;
import se.foodload.domain.Client;
import se.foodload.domain.Template;
import se.foodload.domain.TemplateItem;
import se.foodload.dtos.BuyItemDTO;
import se.foodload.presentation.dto.ClientDTO;
import se.foodload.presentation.models.requests.CreateTemplateItemRequestModel;
import se.foodload.presentation.models.requests.CreateTemplateRequestModel;
import se.foodload.presentation.models.requests.UpdateTemplateItemRequestModel;
import se.foodload.presentation.models.requests.UpdateTemplateRequestModel;
import se.foodload.presentation.models.responses.BuyListResponseModel;

import java.util.List;
import java.util.Set;

@RestController
@Validated
@CrossOrigin
public class TemplateController {
    static final String GET_TEMPLATES = "/get-templates";
    static final String CREATE_TEMPLATE = "/create-template";
    static final String ADD_TEMPLATE_ITEM = "/add-template-item";
    static final String UPDATE_TEMPLATE_ITEM = "/update-template-item";
    static final String REMOVE_TEMPLATE_ITEM = "/remove-template-item";
    static final String DELETE_TEMPLATE = "/delete-template";
    static final String BUY_LIST = "/buy-list";

    @Autowired
    ITemplateService templateService;
    @Autowired
    IClientService clientService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(GET_TEMPLATES)
    public Set<Template> getTemplates(@AuthenticationPrincipal ClientDTO clientDTO){
        Client client = clientService.findClient(clientDTO);
        return templateService.getTemplates(client.getFamily());
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(CREATE_TEMPLATE)
    public Template createTemplate(@AuthenticationPrincipal ClientDTO clientDTO, @RequestBody CreateTemplateRequestModel request){
        Client client = clientService.findClient(clientDTO);
        return templateService.createTemplate(client.getFamily(), request);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(ADD_TEMPLATE_ITEM + "/{templateId}")
    public TemplateItem addTemplateItem(@AuthenticationPrincipal ClientDTO clientDTO, @PathVariable Long templateId, @RequestBody CreateTemplateItemRequestModel request){
        Client client = clientService.findClient(clientDTO);
        return templateService.addNewTemplateItem(client.getFamily(), templateId, request);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(UPDATE_TEMPLATE_ITEM)
    public void updateTemplateItem(@AuthenticationPrincipal ClientDTO clientDTO, @RequestBody UpdateTemplateItemRequestModel request){
        Client client = clientService.findClient(clientDTO);
       templateService.updateTemplateItem(client.getFamily(), request);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(REMOVE_TEMPLATE_ITEM)
    public void removeTemplateItem(@AuthenticationPrincipal ClientDTO clientDTO, @RequestParam Long templateId, @RequestParam Long templateItemId){
        Client client = clientService.findClient(clientDTO);
        templateService.removeTemplateItem(client.getFamily(), templateId, templateItemId);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(DELETE_TEMPLATE+"/{templateId}")
    public void deleteTemplate(@AuthenticationPrincipal ClientDTO clientDTO, @PathVariable Long templateId){
        Client client = clientService.findClient(clientDTO);
        templateService.deleteTemplate(client.getFamily(), templateId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(BUY_LIST + "/{templateId}")
    public BuyListResponseModel getBuyList (@AuthenticationPrincipal ClientDTO clientDTO, @PathVariable Long templateId){
        Client client = clientService.findClient(clientDTO);
        return templateService.generateBuyList(client.getFamily(), templateId);
    }

}
