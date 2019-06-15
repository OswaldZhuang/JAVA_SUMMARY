

public class Raft {
    /**
     * Raft协议的目的是为了实现一致性
     */

    /**
     * 协议过程如下：
     * 系统中一共有三种角色：leader，candidate，follower
     *
     * leader election：
     * 当follower在一段时间内无法接收到来自leader的心跳时，follower即认为leader已经挂掉
     * 那么该follower转化为candidate，然后candidate向其他follower发送投票给自己的请求，
     * 如果该candidate收到超过一半的投票，那么他变为leader（向成员广播他变为leader）
     *
     * log replication：
     * 当leader接收到来自client的值时，其首先将该值复制到各个follower中，然后等待各个follower
     * 的ack，收超过一半的ack后，leader向各个follower发送通知，表示该值已经提交
     */
}
