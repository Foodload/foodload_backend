package se.foodload;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import redis.embedded.RedisServer;
import redis.embedded.RedisServerBuilder;

@SpringBootApplication
public class FoodloadBackendApplication {
	
	//private RedisServer redisServer;
	
	public static void main(String[] args) {
		SpringApplication.run(FoodloadBackendApplication.class, args);
	}

	 
}
