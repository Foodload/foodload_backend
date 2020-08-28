package se.foodload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FoodloadBackendApplication {

	// private RedisServer redisServer;

	public static void main(String[] args) {
		SpringApplication.run(FoodloadBackendApplication.class, args);
	}

}
