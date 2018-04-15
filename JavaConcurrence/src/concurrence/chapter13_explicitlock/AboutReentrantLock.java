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
     * ReentrantLock内部主要是由Sync内部类实现的,该类继承自AQS
     * ReentrantLock内部实现了两种锁:公平锁(FairSync),非公平锁(NonfairSync),(继承Sync类)
     * 之所以会抽取Sync类作为公平锁和非公平锁的公共类,是因为Sync实现了可重入的功能
     * 
     */

}
