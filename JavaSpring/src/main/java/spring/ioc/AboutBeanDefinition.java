package spring.ioc;

public class AboutBeanDefinition {

    /*
     * 在AutowireCapableBeanFactory中定义了自动装配的模式
     */
    int AUTOWIRE_NO = 0;

    int AUTOWIRE_BY_NAME = 1;

    int AUTOWIRE_BY_TYPE = 2;

    int AUTOWIRE_CONSTRUCTOR = 3;

   /*
    *  @Configuration修饰的java配置类的BeanDefinition
    *  的装配模式为AUTOWIRE_NO
    *  @Bean修饰的BeanDefinition的装配模式为AUTOWIRE_CONSTRUCTOR
    *  @Component修饰的BeanDefinition的装配模式为AUTOWIRE_NO
    */
}
