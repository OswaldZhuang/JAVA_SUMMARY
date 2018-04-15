package jms.spring.listenerContainer;

import org.springframework.context.annotation.Configuration;

@Configuration
public class AboutJmsMessageListenerContainer {
    /*
     * spring jms listener进行了封装
     * org.springframework.jms.listener包下:
     * AbstractMessageListenerContainer --继承--> AbstractJmsListeningContainer
     * --继承--> JmsDestinationAccessor --继承--> JmsAccessor
     */
}
