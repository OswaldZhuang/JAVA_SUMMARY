package concurrence.chapter13_explicitlock;

/*
 * java 显式锁
 */
public class AboutLock {
    /*
     * Interface java.util.concurrent.locks.Lock是所有类型的
     * 锁的基础接口
     * 使用显示锁的主要原因在于提高程序的灵活性
     * 实际上,区别于synchronized关键字,显示锁是在java代码层面上实现锁的获取与释放
     * (当然阻塞线程也需要底层的一些操作,不过这些操作已经由java类库封装好了)
     */
    
    /*
     * Lock接口定义了一些显示锁所必要的方法:
     * void lock() 
     *  阻塞的方式尝试去获取锁,如果锁获取不到,那么当前线程就会被挂起(阻塞),该方法可以检测到死锁
     * void lockInterruptibly() throws InterruptedException
     *  以阻塞可中断的方式去获取锁,如果锁获取不到,那么线程就会挂起,如果线程收到中断信号,那么线程就会
     *  抛出InterruptedException,该方法可以检测到死锁
     * boolean tryLock()
     *  非阻塞的方式去获取锁,如果无法获取到锁,就直接返回false
     * void unlock()
     *  释放掉占有的锁,
     * Condition newCondition()
     *  该方法返回Condition对象(该对象和Lock是相关联的)
     *  在条件队列等待(Condition#await())之前,线程必须持有锁(这个和Object类中的wait语义是一致的,其他方法类似)
     */
}
