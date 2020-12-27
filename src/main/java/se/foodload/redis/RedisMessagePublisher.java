package se.foodload.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import se.foodload.domain.Item;
import se.foodload.dtos.ItemCountDTO;
import se.foodload.enums.RedisMessageEnums;

@Service
public class RedisMessagePublisher {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private ChannelTopic topic;

	public RedisMessagePublisher() {
	}

	public void publishItem(long itemCountId, Item item, String clientId, long familyId, int amount, String storageType) {
		RedisItemUpdate publishMsg = new RedisItemUpdate(itemCountId, RedisMessageEnums.UPDATE_ITEM.toString(), clientId, familyId, item.getName(),
				item.getBrand(), item.getQrCode(), amount, storageType);
		redisTemplate.convertAndSend(topic.getTopic(), publishMsg);
	}

	public void publishChangeFamily(String clientId, long familyId, long oldFamilyId) {
		RedisChangeFamily publishMsg = new RedisChangeFamily(RedisMessageEnums.CHANGE_FAMILY.toString(), clientId, familyId, oldFamilyId);
		redisTemplate.convertAndSend(topic.getTopic(), publishMsg);
	}

	public void publishFamilyInvite(String clientId, long familyId) {
		RedisFamilyInvite publishMsg = new RedisFamilyInvite(RedisMessageEnums.FAMILY_INVITE.toString(), clientId, familyId);
		redisTemplate.convertAndSend(topic.getTopic(), publishMsg);
	}

	public void publishMoveItem(String clientId, long familyId, ItemCountDTO srcItemCountDTO, ItemCountDTO destItemCountDTO){
		RedisMoveItem publishMsg = new RedisMoveItem(clientId, RedisMessageEnums.MOVE_ITEM.toString(), familyId, srcItemCountDTO, destItemCountDTO);
		redisTemplate.convertAndSend(topic.getTopic(), publishMsg);
	}

	public void publishDeleteItem(String clientId, long familyId, long itemCountId){
		RedisDeleteItem publishMsg = new RedisDeleteItem(itemCountId, RedisMessageEnums.DELETE_ITEM.toString(), clientId, familyId);
		redisTemplate.convertAndSend(topic.getTopic(), publishMsg);
	}
}