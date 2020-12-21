package se.foodload.redis;

import lombok.Data;
import se.foodload.dtos.ItemCountDTO;

@Data
public class RedisMoveItem {
    private String messageType;
    private String userId;
    private long familyId;
    private ItemCountDTO srcItemCount;
    private ItemCountDTO destItemCount;

    public RedisMoveItem(String userId, String messageType, long familyId, ItemCountDTO srcItemCount, ItemCountDTO destItemCount) {
        this.userId = userId;
        this.messageType = messageType;
        this.familyId = familyId;
        this.srcItemCount = srcItemCount;
        this.destItemCount = destItemCount;
    }

    public RedisMoveItem(){}
}
