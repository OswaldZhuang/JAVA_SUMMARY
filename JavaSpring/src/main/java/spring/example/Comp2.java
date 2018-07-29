package spring.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Comp2 {
    Comp3 comp3;
    Comp4 comp4;

    @Autowired
    public Comp2(Comp3 comp3, Comp4 comp4) {
        this.comp3 = comp3;
        this.comp4 = comp4;
    }
}
