package se.foodload.config;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import redis.embedded.RedisServer;
import se.foodload.auth.filters.FirebaseFilter;

import se.foodload.redis.PublishItem;
import se.foodload.redis.RedisMessageListner;

import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;




@Configuration
@EnableWebSecurity

public class config extends WebSecurityConfigurerAdapter {
	
	private static final String LOGIN_URL = "/login";
	static final String ALL_URL = "/**";
	
	@Autowired
	private FirebaseFilter firebaseFilter;
	
//UNCOMMENT FÖR HERUKO.
   /* String serviceAccountJson = massageWhitespace(System.getenv("SERVICE_ACCOUNT_JSON"));

	@Bean
	@Primary
	public void firebaseInitialization() throws IOException {
		
		InputStream serviceAccount = new ByteArrayInputStream(serviceAccountJson.getBytes());
		
		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.build();
		FirebaseApp.initializeApp(options);
		}*/
	
	 //LOCAL TESTING..
	@Value("${service.account.path}")
	private String keyPath;

	@Bean
	@Primary
	public void firebaseInitialization() throws IOException {
		Resource resource = new ClassPathResource(keyPath);
		FileInputStream serviceAccount = new FileInputStream(resource.getFile());

		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.build();
		if (FirebaseApp.getApps().isEmpty()) {
			FirebaseApp.initializeApp(options);
		}
	}
	
	/**
	 * Layer below WebSecurity. Sets up security against the API and adds filters.
	 * 
	 * @param httpSecurity The <code>HttpSecurity</code> to configure.
	 */
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable().formLogin().disable().httpBasic().disable().cors().and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests().antMatchers(ALL_URL).permitAll()
		.anyRequest().authenticated();
		httpSecurity.addFilterBefore(firebaseFilter, UsernamePasswordAuthenticationFilter.class);
	}	
	
	private static String massageWhitespace(String s) {
        String newString = "";
        for (Character c : s.toCharArray()) {
            if ("00a0".equals(Integer.toHexString(c | 0x10000).substring(1))) {
                newString += " ";
            } else {
                newString += c;
            }
        }
        return newString;

    }
	/*
	 * 
	 * REDIS CONFIGS BELOW
	 * 
	 */
	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
	    return new JedisConnectionFactory();
	}

	@Bean
	ChannelTopic topic() {
	    return new ChannelTopic("PublishItem");
	}
	

	 @Bean
	    public RedisTemplate<String, PublishItem> redisTemplate(JedisConnectionFactory connectionFactory) {
	        RedisTemplate<String, PublishItem> template = new RedisTemplate<String, PublishItem>();
	        template.setConnectionFactory(connectionFactory);
	        template.setValueSerializer(new Jackson2JsonRedisSerializer<PublishItem>(PublishItem.class));
	        return template;
	    }
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
	        container.setConnectionFactory(jedisConnectionFactory());
	        container.addMessageListener(messageListener(), topic());
	        return container;
	    }
	  
	
}
