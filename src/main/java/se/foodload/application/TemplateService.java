package se.foodload.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.foodload.application.exception.NotFoundException;
import se.foodload.application.interfaces.ITemplateService;
import se.foodload.domain.*;
import se.foodload.dtos.BuyItemDTO;
import se.foodload.enums.ErrorEnums;
import se.foodload.presentation.models.requests.CreateTemplateItemRequestModel;
import se.foodload.presentation.models.requests.CreateTemplateRequestModel;
import se.foodload.presentation.models.requests.UpdateTemplateItemRequestModel;
import se.foodload.presentation.models.requests.UpdateTemplateRequestModel;
import se.foodload.presentation.models.responses.BuyListResponseModel;
import se.foodload.repository.ItemCountRepository;
import se.foodload.repository.ItemRepository;
import se.foodload.repository.TemplateItemRepository;
import se.foodload.repository.TemplateRepository;

import java.util.*;

@Service
@Transactional
public class TemplateService implements ITemplateService {

    @Autowired
    private TemplateRepository templateRepository;
    @Autowired
    private TemplateItemRepository templateItemRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemCountRepository itemCountRepository;


    @Override
    public Set<Template> getTemplates(Family family) {
        return family.getTemplates();
    }

    @Override
    public Template createTemplate(Family family, CreateTemplateRequestModel request) {
        String name = request.getName();
        Template template = new Template(name);
        //TODO: if addTempalteItemModelsToTemplate returns false. Notify user.
        //addNewTemplateItemsToTemplate(request.getTemplateItems(), template);
        family.addTemplate(template);
        return template;
    }

     @Override
     public TemplateItem addNewTemplateItem(Family family, Long templateId, CreateTemplateItemRequestModel request){
         Optional<Template> templateOpt = templateRepository.findByIdAndFamily(templateId, family);
         if(templateOpt.isEmpty()){
             throw new NotFoundException(ErrorEnums.TEMPLATE_NOT_FOUND.toString());
         }

         Template template = templateOpt.get();

         Optional<Item> itemOpt = itemRepository.findById(request.getItemId());
         if(itemOpt.isEmpty()){
             throw new NotFoundException(ErrorEnums.ITEM_QR_NOT_FOUND.toString());
         }
         Item item = itemOpt.get();

         TemplateItem templateItem = new TemplateItem(item, request.getCount());
         template.addTemplateItem(templateItem);
         return templateItem;
    }

    @Override
    public void updateTemplateItem(Family family, UpdateTemplateItemRequestModel request){

        Optional<Template> templateOpt = templateRepository.findByIdAndFamily(request.getTemplateId(), family);
        if(templateOpt.isEmpty()){
            throw new NotFoundException(ErrorEnums.TEMPLATE_NOT_FOUND.toString());
        }

        Template template = templateOpt.get();

        Optional<TemplateItem> templateItemOpt = templateItemRepository.findByIdAndTemplate(request.getTemplateItemId(), template);

        if(templateItemOpt.isEmpty()) {
            throw new NotFoundException(ErrorEnums.TEMPLATE_ITEM_NOT_FOUND.toString());
        }

        TemplateItem templateItem = templateItemOpt.get();
        templateItem.setCount(request.getCount());
        templateItemRepository.save(templateItem);

        /*
        for(UpdateTemplateItemRequestModel tempItemModel : request.getUpdatedTemplateItems()){
            Optional<TemplateItem> templateItemOpt = templateItemRepository.findByIdAndTemplate(tempItemModel.getTemplateItemId(), template);
            if(templateItemOpt.isEmpty()){
                //TODO: Throw exception or skip and telling user?
                continue;
            }

            TemplateItem templateItem = templateItemOpt.get();
            templateItem.setCount(tempItemModel.getCount());
        }

        templateRepository.save(template);
        return template;
         */
    }

    @Override
    public void removeTemplateItem(Family family, Long templateId, Long templateItemId){

        Optional<Template> templateOpt = templateRepository.findByIdAndFamily(templateId, family);
        if(templateOpt.isEmpty()){
            throw new NotFoundException(ErrorEnums.TEMPLATE_NOT_FOUND.toString());
        }

        Optional<TemplateItem> templateItemOpt = templateItemRepository.findByIdAndTemplate(templateItemId, templateOpt.get());
        if(templateItemOpt.isEmpty()){
            throw new NotFoundException(ErrorEnums.TEMPLATE_ITEM_NOT_FOUND.toString());
        }

        templateOpt.get().removeTemplateItem(templateItemOpt.get());
    }

    @Override
    public void deleteTemplate(Family family, Long templateId){
        Optional<Template> templateOpt = templateRepository.findByIdAndFamily(templateId, family);
        if(templateOpt.isEmpty()){
            throw new NotFoundException(ErrorEnums.TEMPLATE_NOT_FOUND.toString());
        }

        templateRepository.delete(templateOpt.get());
    }

    //TODO: This can also be done in the client...
    @Override
    public BuyListResponseModel generateBuyList(Family family, Long templateId){
        Optional<Template> templateOpt = templateRepository.findByIdAndFamily(templateId, family);
        if(templateOpt.isEmpty()){
            throw new NotFoundException(ErrorEnums.TEMPLATE_NOT_FOUND.toString());
        }

        Template template = templateOpt.get();
        List<BuyItemDTO> buyList = new ArrayList<>();
        Set<TemplateItem> templateItems = template.getTemplateItems();

        if(templateItems == null || templateItems.isEmpty()){
            return new BuyListResponseModel(buyList);
        }

        HashMap<String, Integer> totalCount = new HashMap<>();
        Optional<List<ItemCount>> itemCountsOpt = itemCountRepository.findByFamilyId(family);
        if(!itemCountsOpt.isEmpty()) {
            List<ItemCount> itemCounts = itemCountsOpt.get();
            for(ItemCount itemCount : itemCounts){
                String qrCode = itemCount.getItem().getQrCode();
                int count = itemCount.getCount();
                if(totalCount.containsKey(qrCode)){
                    totalCount.put(qrCode, totalCount.get(qrCode) + count);
                } else {
                    totalCount.put(qrCode, count);
                }
            }
        }

        for(TemplateItem templateItem : templateItems){
            String qrCode = templateItem.getItem().getQrCode();
            int desiredCount = templateItem.getCount();
            int diffCount = totalCount.getOrDefault(qrCode, 0) - desiredCount;
            if(diffCount < 0){
                buyList.add(new BuyItemDTO(templateItem.getItem(), (diffCount * -1)));
            }
        }

        return new BuyListResponseModel(buyList);
    }

    /*
    @Override
    public Template getTemplate(Family family, long templateId) {
        Optional<Template> templateOpt = templateRepository.findByIdAndFamily(templateId, family);
        if(templateOpt.isEmpty()){
            throw new NotFoundException(ErrorEnums.TEMPLATE_NOT_FOUND.toString());
        }
        return templateOpt.get();
    }
     */

    private boolean addNewTemplateItemsToTemplate(List<CreateTemplateItemRequestModel> newTemplateItems, Template template){
        boolean isSuccessful = true;
        for(CreateTemplateItemRequestModel newTempItem : newTemplateItems){
            Optional<Item> itemOpt = itemRepository.findById(newTempItem.getItemId());
            if(itemOpt.isEmpty()){
                //TODO: Throw exception or skip and telling user?
                isSuccessful = false;
                continue;
            }
            Item item = itemOpt.get();

            TemplateItem templateItem = new TemplateItem(item, newTempItem.getCount());
            template.addTemplateItem(templateItem);
        }
        return isSuccessful;
    }
}
