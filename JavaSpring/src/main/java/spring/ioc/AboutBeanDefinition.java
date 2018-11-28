package spring.ioc;

public class AboutBeanDefinition {

    /*
     * 在AutowireCapableBeanFactory中定义了自动装配的模式
     */
    int AUTOWIRE_NO = 0;
    //@Configuration
    //@Component

    int AUTOWIRE_BY_NAME = 1; //通过名字匹配
    //<bean name="" class="" autowire="byName"/>

    int AUTOWIRE_BY_TYPE = 2; //通过类型匹配
    //<bean name="" class="" autowire="byType"/>

    int AUTOWIRE_CONSTRUCTOR = 3;
    //@Bean

}
