package spring.ioc;

public class ParseConfiguration {
    /*
     * 解析@Configuration配置
     */

    /*
     * 解析过程是通过ConfigurationClassPostProcessor来完成的
     * 其首先会对ConfigurationClass的beanDefinition进行排序(根据@Order),排序完成之后
     * 会遍历这些beanDefinition并依次解析
     * 而真正的解析是委托给ConfigurationClassParser#parse执行的
     * 在解析完成之后,会通过ConfigurationClassBeanDefinitionReader#loadBeanDefinitions
     * 来将解析好的配置类中的bean信息转化为BeanDefinition并注册到BeanFactory中
     */

    /*
     * ConfigurationClassParser#parse
     * 该方法首先把传入的metaData和beanName封装为ConfigurationClass交给processConfigurationClass处理
     * ConfigurationClassParser#processConfigurationClass
     * 进一步把ConfigurationClass封装为SourceClass,和ConfigurationClass一并交给doProcessConfigurationClass
     * 处理
     * 实际上最后处理完成之后所有处理结果都是封装在ConfigurationClass中
     */

    /*
     * ConfigurationClassParser#doProcessConfigurationClass
     * 1.解析成员类
     * 2.解析@PropertySource
     * 3.解析@ComponentScan
     * 4.解析@Import
     * 5.解析@ImportResource
     * 6.解析@Bean
     * 7.解析接口的默认方法
     * 8.解析父类
     */


    /*
     * 解析@Import
     * processImports方法
     * 首先会收集ConfigurationClass上的所有@Import的注解(包括如果注解的注解是@Import,也会收集起来,该过程是递归的过程)
     * 随后对@Import中的类进行解析,循环遍历,
     * 1.如果是ImportSelector类型,那么首先实例化该类
     * 然后如果该ImportSelector是DeferredImportSelector,则将其加入到本对象的deferredImportSelectors中
     * 否则(即仅仅是一般的ImportSelector),则调用其selectImports方法选择import class,传入参数为当前Configuration Class
     * 的MetaData,随后递归地调用processImports完成所有配置类的解析
     * 2.如果是ImportBeanDefinitionRegistrar类型,那么首先实例化该类
     * 然后调用ConfigurationClass#addImportBeanDefinitionRegistrar将该类加入到
     * ConfigurationClass的importBeanDefinitionRegistrars中
     */

    /*
     * 解析@Bean
     * 首先找到所有@Bean修饰的方法,然后将他们加入到ConfigurationClass中
     */
}
