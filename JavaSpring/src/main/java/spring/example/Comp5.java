package spring.example;

import org.springframework.stereotype.Component;

@Component
public class Comp5 {

    @Profile
    public void businessLogic(){
        System.out.println("Do something");
    }
}
