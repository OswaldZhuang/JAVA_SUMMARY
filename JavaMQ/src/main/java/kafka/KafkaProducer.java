package kafka;

public class KafkaProducer {

    /**
     * producer的ack机制
     * aks=[0, 1, all, -1] all和-1一致
     * 0：producer发送消息后不会等待broker的任何ack，消息会被直接加到socket buffer中等待发送
     * 1：leader会在本地写数据，同时向follower发送数据，在部分follower返回ack的时候就会返回ack
     * all：类似于上，但是leader会等到所有follower接收到数据的时候（返回ack）才返回ack
     */

    /**
     * 幂等性：
     * 即producer发送的消息，有且只会在broker中存一份，不会发生发送重复消息的情况
     * 比如broker在接收到消息后发送ack之前挂掉了，那么producer就会认为消息发送失败，于是retry重发消息
     *
     * kafka实现了exactly once语义，保证了消息的幂等
     * 设置enable.idempotence=true
     * producer每个发送的消息中都会携带一个sequence number，当broker接收到的消息的seq小于之前接收到的
     * 那么就会丢掉丢弃这条消息（seq会被broker持久化并同步）
     *
     * 事务：
     * 采用事务的api可以保证在多个partition下发送消息的事务性
     * 对于consumer，可以选择事务隔离等级来接收消息
     *
     * processing.guarantee=exactly_once
     *
     */
}
