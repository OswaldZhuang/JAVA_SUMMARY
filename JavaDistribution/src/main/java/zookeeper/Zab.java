package zookeeper;

public class Zab {
    /**
     * Zab协议
     * Zookeeper Atomic Broadcast
     * 支持崩溃恢复的原子广播协议
     *
     * zookeeper采用leader-follower 模式，即leader接收所有请求，然后将
     * 数据同步到follower
     */

    /**
     * leader提交过程（消息广播）
     * leader接收到数据后首先向所有follower发送写入数据以及proposal请求，并等待follower的ack
     * follower收到请求后返回ack，表示可以提交，
     * leader如果收到超过半数的ack，那么自己提交数据并且向follower发送提交命令
     * follower收到提交命令提交数据
     *
     * 为保证提交的事务的顺序性，leader和follower之间通过消息队列
     */

    /**
     * zxid分为两个部分：
     * 高32位epoch，低32位消息计数器（即已经提交的消息数）
     */

    /**
     * 崩溃恢复
     * 如果leader崩溃了或者leader与超过一半的follower失去了联系，那么进入到恢复模式
     * 过程如下：
     * 1.选举新的leader
     * zxid最大的成员会被选举成为leader
     * 2.恢复数据
     */

    /**
     * leader选举
     * 成员先给自己投票然后向其他成员发送投票
     * 成员会检查自己的票和其他成员的票，如果其他成员的票更优，那么将自己的票更新为那张票
     * 然后发送给其他成员
     * 比较规则是（机器id，zxid，epoch）
     */
}
