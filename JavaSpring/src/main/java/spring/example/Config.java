package spring.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("spring.example")
@EnableAspectJAutoProxy
public class Config {

    @Bean
    Comp3 comp3(){
        return new Comp3();
    }

    @Bean
    Comp4 comp4(){
        return new Comp4();
    }
}
