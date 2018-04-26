package concurrence.chapter13_explicitlock;

/*
 * 关于可重入锁
 * 可重入表示当一个线程试图去获取一个已经由它持有的锁,这个请求就会成功
 * ReentrantLock表示可重入,互斥的锁
 */
public class AboutReentrantLock {
    
    /*
     * 公平锁:新来的线程必须排到队列的末尾等待获得锁
     * 非公平锁:新来的线程可以抢先获得锁
     * 
     * 实际上一般推荐使用非公平锁,因为非公平是抢占式的,公平锁的性能一般比较低
     * 
     * ReentrantLock内部主要是由Sync内部类实现的,该类继承自AQS，采用的是AQS的独占模式(EXCLUSIVE)
     * ReentrantLock内部实现了两种锁:公平锁(FairSync),非公平锁(NonfairSync),(继承Sync类)
     * 之所以会抽取Sync类作为公平锁和非公平锁的公共类,是因为Sync实现了可重入的功能
     * 实际上可重入的实现是基于判断当前锁的持有线程是否是当前线程(即isHeldByCurrentThread)
     */
	
	/*
	 * java.util.concurrent.locks.ReentrantLock$Sync extends AbstractQueuedSynchronizer
	 * 
	 * final boolean nonfairTryAcquire(int acquires) 该方法实现非公平的锁获取,实际上非公平锁子类的tryAcquire()方法就是
	 * 调用的此方法,
	 * 上该方法仅仅是简单的判断当前的锁是否被当前线程独占,并设置AQS中state的值
	 * (state的值为原来的值加上 acquires值)
	 * 
	 * protected final boolean tryRelease(int releases) 该方法重写了AQS的tryRelease方法,该方法同上也是仅仅判断锁的
	 * 拥有情况以及设置state(state的值为原来的值减去releases）
	 * 
	 * protected final boolean isHeldExclusively() 查看是否独占该锁 return getExclusiveOwnerThread() == Thread.currentThread()
	 * 
	 * state的状态为0,表示锁是空闲的,每成功tryAcquire(int acquires)一次,state变为state + acquires
	 * 每成功tryRelease(int releases)一次,state变为state - releases 也就是说,release的时候state的值会减小,acquire的时候,state
	 * 的值会增加,但是state>=0,如果state<0,说明int型的state值溢出了,竞争锁的线程超过了2^31 - 1(2147483647)个
	 * 实际上,acquires和releases的值都为1
	 */
	
	/*
	 * NonfairSync extends Sync 实现非公平锁
	 * 
	 * final void lock() 调用该方法时线程会立刻抢占该锁
	 * 如果CAS设置state为1成功(认为以前是0),那么将ownerThread设置为当前线程
	 * 否则执行AQS#acquire(1)（其实这个时候线程还是会进入到队列里面等待）
	 */
	
	/*
	 * FairSync extends Sync
	 * 
	 * final void lock() 该方法直接执行acquire(1),也就是说直接将其入队,不管当前的锁是否是空闲的
	 * 
	 * protected final boolean tryAcquire(int acquires)
	 * 如果state为0(说明锁是空闲的),那么再判断是否有入队的线程以及CAS状态为1是否成功,上述条件都成立,才返回true(这也是公平锁
	 * 性能比较低的原因之一)
	 * 如果state不为0,判断锁的占有着是否是当前线程(可重入),是的话设置状态返回true
	 */
	
	/*
	 * java.util.concurrent.locks.ReentrantLock
	 * private final Sync sync; 同步器
	 * 无参数的构造函数默认构造非公平的锁
	 * public ReentrantLock(boolean fair)  true则构造公平锁
	 * public void lock() 
	 * public void lockInterruptibly() throws InterruptedException
	 * public boolean tryLock() 尝试获取锁,如果获取不成功,那么会立刻返回实际上底层调用的sync#nonfairTryAcquire(1)
	 * public void unlock()
	 * public Condition newCondition() 该方法会构造同步队列,底层是构造AQS#ConditionObject
	 * 
	 */

}
