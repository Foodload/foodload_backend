package se.foodload.dtos;

import lombok.Data;
import se.foodload.domain.Item;

@Data
public class ItemDTO {
    private String name;
    private String brand;
    private String qrCode;

    public ItemDTO(String name, String brand, String qrCode){
        this.name = name;
        this.brand = brand;
        this.qrCode = qrCode;
    }

    public ItemDTO (Item item){
        this.name = item.getName();
        this.brand = item.getBrand();
        this.qrCode = item.getQrCode();
    }
}
