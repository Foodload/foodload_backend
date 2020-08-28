package se.foodload.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import se.foodload.domain.Item;
import se.foodload.enums.RedisMessageEnums;

@Service
public class RedisMessagePublisher {

	private static final String UPDATE_ITEM = RedisMessageEnums.UPDATE_ITEM.getMessageType();
	private static final String CHANGE_FAMILY = RedisMessageEnums.CHANGE_FAMILY.getMessageType();
	private static final String FAMILY_INVITE = RedisMessageEnums.FAMILY_INVITE.getMessageType();
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private ChannelTopic topic;

	public RedisMessagePublisher() {
	}

	public void publishItem(long itemcountId, Item item, String clientId, long familyId, int ammount) {
		RedisItemUpdate publishMsg = new RedisItemUpdate(itemcountId, UPDATE_ITEM, clientId, familyId, item.getName(),
				item.getBrand(), item.getQrCode(), ammount);
		redisTemplate.convertAndSend(topic.getTopic(), publishMsg);

	}

	public void publishChangeFamily(String clientId, long familyId, long oldFamilyId) {
		RedisChangeFamily publishMsg = new RedisChangeFamily(CHANGE_FAMILY, clientId, familyId, oldFamilyId);
		redisTemplate.convertAndSend(topic.getTopic(), publishMsg);

	}

	public void publishFamilyInvite(String clientId, long familyId) {
		RedisFamilyInvite publishMsg = new RedisFamilyInvite(FAMILY_INVITE, clientId, familyId);
		redisTemplate.convertAndSend(topic.getTopic(), publishMsg);

	}

}