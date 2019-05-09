package jms.message;

public class AboutMessageHeader {
    
    /*
     * 消息头部
     * 包含一下字段:
     * JMSMessageID:一条message的唯一标识,必须以"ID:"开头,JMSMessageID的唯一性是由provider(即JMS server)保证的
     * JMSCorrelationID:correlationId用于连接两条message,最典型的用法就是使用correlationId连接response和request
     * (实际上,link两条message使用JMSMessageID是最方便的,因为其具有唯一性,但是假如app希望在ID中加入另外的信息,那么就可以使用JMSCorrelationID)
     * 
     */

}
