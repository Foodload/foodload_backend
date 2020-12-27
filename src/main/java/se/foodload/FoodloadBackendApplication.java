package se.foodload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import se.foodload.config.yaml.YAMLConfig;

@SpringBootApplication
public class FoodloadBackendApplication implements CommandLineRunner {

	@Autowired
	private YAMLConfig myConfig;

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(FoodloadBackendApplication.class);
		app.run();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Environment: " + myConfig.getEnvironment());
		System.out.println("YAML Name: " + myConfig.getName());
		System.out.println("Enabled: " + myConfig.isEnabled());
	}

}
