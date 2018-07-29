package spring.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Comp1 {
    /*
     * 该bean的实例化
     * spring首先采用无参数的构造器利用反射将其实例化
     * 随后,利用AutowiredAnnotationBeanPostProcessor将
     * @Autowired修饰的依赖
     */
    @Autowired
    Comp2 comp2;

}
