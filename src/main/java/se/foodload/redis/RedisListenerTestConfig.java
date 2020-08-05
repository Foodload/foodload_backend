package se.foodload.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class RedisListenerTestConfig {
	@Autowired
	JedisConnectionFactory jedisConnectionFactory;
	@Autowired
	ChannelTopic topic;
	 /*
	  * MESSAGE SUBSRCIBER FOR TEST
	  */
	
	  @Bean
	    MessageListenerAdapter messageListener() {
	        return new MessageListenerAdapter( new RedisMessageListner() );
	    }

	    @Bean
	    RedisMessageListenerContainer redisContainer() {
	        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
	        container.setConnectionFactory(jedisConnectionFactory);
	        container.addMessageListener(messageListener(), topic);
	        return container;
	    }
}
