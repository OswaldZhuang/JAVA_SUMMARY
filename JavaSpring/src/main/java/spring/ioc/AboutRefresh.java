package spring.ioc;

/**
 * AbstractApplicationContext#refresh()
 */
public class AboutRefresh {
    /**
     * 主要过程如下:
     * prepareRefresh();
     *
     * ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();
     *
     * prepareBeanFactory(beanFactory);
     *
     * postProcessBeanFactory(beanFactory);
     *
     * //调用所有的BeanFactoryPostProcessor的回调方法
     * invokeBeanFactoryPostProcessors(beanFactory);
     *
     * //注册BeanPostProcessor
     * registerBeanPostProcessors(beanFactory);
     *
     * initMessageSource();
     *
     * initApplicationEventMulticaster();
     *
     * onRefresh();
     *
     * registerListeners();
     *
     * //该过程主要完成bean的实例化
     * finishBeanFactoryInitialization(beanFactory);
     *
     * //该过程主要启动LifeCycle对象(start方法)
     * finishRefresh();
     */

}
