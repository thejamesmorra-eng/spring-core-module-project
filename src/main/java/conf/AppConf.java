package conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Scanner;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = {"listener, service, conf"})
public class AppConf {

    @Bean
    public Scanner scanner() {
        return new Scanner(System.in);
    }
}
