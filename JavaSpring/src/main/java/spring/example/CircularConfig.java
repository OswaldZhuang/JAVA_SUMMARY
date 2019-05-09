package spring.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CircularConfig {

    @Autowired
    ApplicationContext appCtx;

/*    @Bean
    ApplicationContext appCtx(){
        return new AnnotationConfigApplicationContext(this.getClass());
    }*/

    public static void main(String[] args){
        ApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(CircularConfig.class);
        applicationContext.getApplicationName();
    }
}
