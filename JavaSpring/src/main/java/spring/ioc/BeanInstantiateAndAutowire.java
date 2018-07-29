package spring.ioc;

/*
 * Spring中bean的实例化和自动装配
 */
public class BeanInstantiateAndAutowire {
    /*
     * 方法入口:AbstractApplicationContext#Refresh ->
     * AbstractApplicationContext#finishBeanFactoryInitialization(beanFactory) ->
     * DefaultListableBeanFactory#preInstantiateSingletons()
     */

    /*
     * 在preInstantiateSingletons中,Spring会遍历BeanDefinition,然后依次调用getBean方法来
     * 获得实例
     * 在这之后,再检查Bean实例是否是SmartInitializingSingleton,如果是则调用其接口的回调方法
     */

    /*
     * getBean在AbstractBeanFactory中,它会调用doGetBean方法
     * 在doGetBean中,首先会检查singletonObjects中是否已经有该bean,
     * 如果没有的话:
     * 首先检查是否有parentBeanfactory,如果有则调用parentBeanfactory#getBean
     * 没有parentBeanfactory#getBean,那么
     * 1.检查BeanDefinition中的dependsOn(即@DependentOn),并实例化各个dependent
     * 2.实例化BeanDefinition,其核心方法是createBean
     */

    /*
     * AbstractAutowireCapableBeanFactory#createBean实现了自动装配的的bean实例化
     * 如果有InstantiationAwareBeanPostProcessor,那么首先调用其回调方法处理beanDefinition
     * 然后调用doCreateBean得到bean实例
     */

    /*
     * AbstractAutowireCapableBeanFactory#doCreateBean
     * 调用createBeanInstance创建BeanWrapper(实际就是bean的实例)
     * 如果有MergedBeanDefinitionPostProcessor,那么调用回调方法处理beanDefinition
     */

    /*
     * AbstractAutowireCapableBeanFactory#createBeanInstance
     * 如果beanDefinition中有Supplier,那么采用Supplier创建bean实例(obtainFromSupplier)
     * 如果beanDefinition中有factoryMethod,那么采用factoryMethod创建bean实例(instantiateUsingFactoryMethod)
     * (@Configuration中配置的@Bean的实例是采用该方法实例化的)
     * 否则要么调用autowireConstructor要么调用instantiateBean来实例化bean
     * (对于@Component的bean,如果显式的声明了有参数的构造器,那么调用autowireConstructor实例化bean
     * 如果没有显式声明构造器,那么调用instantiateBean实例化bean)
     */

    /*
     * AbstractAutowireCapableBeanFactory#autowireConstructor
     * 实际上调用ConstructorResolver#autowireConstructor实例化bean,该方法对应了spring的构造器注入
     * 该方法的核心在于找到bean的各个构造器以及构造器所对应的参数,并且会实例化这些参数,
     * 实例化参数的方法为createArgumentArray,最后将构造器以及所需要的参数实例一并交由InstantiationStrategy#instantiate
     * 进行实例化
     */

    /*
     * AbstractAutowireCapableBeanFactory#instantiateBean
     * 实际上是调用的是InstantiationStrategy#instantiate方法来实例化bean,
     * 本质上是利用反射通过Constructor#newInstance来完成实例化,构造器是无参数的
     * @Component修饰的bean采用该方法实例化
     */

    /*
     * AbstractAutowireCapableBeanFactory#instantiateUsingFactoryMethod
     * 该方法实际调用ConstructorResolver(this).instantiateUsingFactoryMethod
     */

    /*
     * ConstructorResolver#instantiateUsingFactoryMethod
     * 该方法和autowireConstructor类似,只是参与实例化的方法变成了beanDefinition中定义的
     * FactoryMethod
     */
}
