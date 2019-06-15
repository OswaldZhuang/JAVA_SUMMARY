package concurrence.chapter5_basicmodule;


public class AboutBlockingQueue {
	/*
	 * Interface java.util.concurrent.BlokingQueue extends java.util.Queue
	 * 阻塞队列
	 * 当队列为空或是满的时候都会使得操作者等待(从空队列取,向满队列放)
	 * 阻塞队列以四种方式解决当前操作无法被满足(可能将来某个时间点会满足)的情况:
	 * 1.抛出异常
	 * 2.返回特殊值
	 * 3.阻塞当前线程
	 * 4.在超时时间内阻塞
	 * 
	 * BlockinQueue的操作可分为如下几类:
	 * 插入:
	 * boolean add(E e); 插入元素到队列中,返回true表示插入成功,失败不会返回false而是抛出异常,
	 * 如果队列空间不足,那么会抛出IllegalStateException
	 * boolean offer(E e);同上,但是失败会返回false,在队列大小有限的情况下优先选择此方法
	 * void put(E e) throws InterruptedException;插入元素并阻塞直到队列空间可用
	 * boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException;插入元素,在有限时间内阻塞直到队列空间可用
	 * 删除:
	 * E take() throws InterruptedException;移除队列的头元素,如果元素不可操作那么就会阻塞
	 * E poll(long timeout, TimeUnit unit) throws InterruptedException;同上,但是有时间限制
	 * boolean remove(Object o);移除元素(不一定是头元素)
	 * 
	 * int drainTo(Collection<? super E> c);将队列元素移到一个集合中,原队列中的元素会被删除
	 * 
	 * BlockingQueue不接收null
	 * BlockingQueue主要设计用作生产者-消费者模式
	 * BlockingQueue的实现是线程安全的
	 * BlockingQueue没有关闭方法,一般的策略是生产者向队列中加入特殊的"end-of-stream"或
	 * "有毒"对象,当消费者获取这些对象时会做出相关的反应
	 */

	/**
	 * ArrayBlockingQueue
     * 基于数组的有界的阻塞队列
     * 通过ReentrantLock和Condition（非空，非满）控制元素的添加和删除
	 */

    /**
     * LinkedBlockingQueue
     */

    /**
     * SynchronousQueue
     */

    /**
     * DelayQueue
     */

    /**
     * PriorityBlockingQueue
     */

    /**
     * Interface java.util.concurrent.TransferQueue
     *
     * LinkedTransferQueue
     */
}
