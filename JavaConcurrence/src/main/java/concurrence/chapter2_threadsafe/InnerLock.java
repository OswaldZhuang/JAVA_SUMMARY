package concurrence.chapter2_threadsafe;

/*java提供内置锁的机制来支持原子性:synchronized修饰的
 * 同步代码块.每个java对象都可以用作一个同步的锁，线程进入
 * 代码块之前获得锁，退出时释放锁。锁是互斥的，即最多只有一个
 * 线程能持有该锁。
 */

/*
 * 实际上锁存放在对象的对象头中的MarkWord中
 * 内置锁有偏向锁,轻量级锁和重量级锁
 * 偏向锁:如果线程获得一个锁之后再次请求同一个锁,则无需进行相关的同步操作,从而节省操作时间
 * 轻量级锁:偏向锁失败,则转为轻量级锁,轻量级锁由BasicObjectLock实现,
 * 该对象由BasicLock对象和持有该锁的对象的指针组成
 */
public class InnerLock {
    
    private Integer num;
    /*如果synchronized加在方法体上，则意味着该类所对应的对象为一个锁*/
    public synchronized void method() {
        
    }
    
    public void method2() {
        synchronized(num) {
            
        }
    }
    
}
