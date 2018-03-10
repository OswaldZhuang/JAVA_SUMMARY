package jms.client;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

/*
 * 如何使用JMS规范以在客户端实现消息的接收
 * 这里以ActiveMQ相关实现为例
 */
public class UseOfJMSClient {
    
    public void jmsClientUsage() throws JMSException {
        /*
         * 首先通过<url> <user_name> <pass_word> 创建ConnectionFactory
         */
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("<user_name>","<pass_word>","<the broker url>");
        /*
         * 创建connection,具体对应的类为ActiveMQConnection
         */
        Connection connection = connectionFactory.createConnection();
        /*
         * 创建session,对应的类为ActiveMQSession
         */
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        
        /*
         * 创建Message的Consumer,对应的类为ActiveMQMessageConsumer
         */
        MessageConsumer messageConsumer = session.createConsumer(null);
        
        /*
         * 设置onMesssage方法,以完成"监听"功能
         */
        messageConsumer.setMessageListener((Message message)->{
            //业务逻辑
        });
    }
    
}
