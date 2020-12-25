package se.foodload.redis;

import lombok.Data;

@Data
public class RedisDeleteItem {
    private String messageType;
    private String userId;
    private long familyId;
    private long itemCountId;

    public RedisDeleteItem(long itemCountId, String messageType, String clientId, long familyId) {
        this.messageType = messageType;
        this.itemCountId = itemCountId;
        this.userId = clientId;
        this.familyId = familyId;
    }

    public RedisDeleteItem() {

    }
}
