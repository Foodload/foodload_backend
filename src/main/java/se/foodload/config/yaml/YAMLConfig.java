package se.foodload.config.yaml;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Class holding information about which YAML config that is currently running.
 *
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class YAMLConfig {
    private String name;
    private String environment;
    private boolean enabled;

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setEnvironment(String environment){
        this.environment = environment;
    }

    public String getEnvironment(){
        return environment;
    }

    public boolean isEnabled(){
        return enabled;
    }

    public void setEnabled(boolean enabled){
        this.enabled = enabled;
    }
}
