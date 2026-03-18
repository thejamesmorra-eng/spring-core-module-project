package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Scanner;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = {"console, service, config"})
public class AppConfig {

    @Bean
    public Scanner scanner() {
        return new Scanner(System.in);
    }
}
