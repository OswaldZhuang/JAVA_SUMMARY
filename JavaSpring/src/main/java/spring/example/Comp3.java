package spring.example;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class Comp3 implements ApplicationContextAware, BeanNameAware {
    private ApplicationContext appCtx;

    private String bean_name;

    public Comp3() {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.appCtx = applicationContext;
    }

    @Override
    public void setBeanName(String name) {
        this.bean_name = name;
    }
}
