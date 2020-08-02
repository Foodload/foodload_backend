package se.foodload.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import se.foodload.domain.Item;

@Service
public class RedisMessagePublisher {

    @Autowired
    private RedisTemplate<String, PublishItem> redisTemplate;
    @Autowired
    private ChannelTopic topic;

    

    public RedisMessagePublisher() {
    }
    public void publishMessage(boolean operation, Item item, String  clientId, long familyId, int ammount) {
    	PublishItem publishItem =  new PublishItem(operation, clientId, familyId, item.getName(), item.getBrand(), item.getQrCode(), ammount);
        redisTemplate.convertAndSend(topic.getTopic(),publishItem);
    }
	
}