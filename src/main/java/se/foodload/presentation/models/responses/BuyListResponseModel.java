package se.foodload.presentation.models.responses;

import lombok.Data;
import se.foodload.dtos.BuyItemDTO;

import java.util.List;

@Data
public class BuyListResponseModel {
    private List<BuyItemDTO> buyList;

    public BuyListResponseModel(List<BuyItemDTO> buyList){
        this.buyList = buyList;
    }
}
