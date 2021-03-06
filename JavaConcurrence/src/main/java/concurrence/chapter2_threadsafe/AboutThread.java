package concurrence.chapter2_threadsafe;

/*
 * 线程
 */
public class AboutThread {
    
    
    /*
     * public static native void sleep(long millis) throws InterruptedException
     * Thread的sleep方法使得线程"睡眠"一段时间,让出CPU给其他线程执行,在这期间,线程仍然拥有对象的monitor(对象的锁)
     * 该方法是静态方法,因此该方法仅仅对当前线程有效,即在对象中,调用方式为:
     *  Thread.currentThread().sleep(1000L);(当然,要try catch InterruptedException())
     */
    
    /*
     * public final void suspend()
     * 该方法被标注为@Deprecated,是不推介使用的,原因是该方法具有死锁倾向,
     * 比如说:如果某个线程t1持正在访问某些资源,而此时调用了suspend方法将其挂起,如果这个线程永远不恢复,那么其他线程就无法访问这个资源
     * 当其他需要该资源的线程t2通过t1的resume()方法试图去释放上述线程并之后访问资源时,就会发生死锁
     * (t1持有资源的monitor(锁),并未释放,t2调用t1的resume方法希望释放t1,这时候t2持有t1的monitor请求资源的monitor,而t1有
     * 资源的monitor,向下执行时有需要自己的monitor,此时就死锁了)
     */
    
    /*
     * public final void resume()
     * 该方法被标注为@Deprecated,是不推介使用的
     * 原因同上
     */
    
    /*
     * public final void stop()
     * 该方法被标注为@Deprecated,是不推介使用的
     * 该方法会强制线程停止任务,实际上该方法底层调用了stop0(new ThreadDeath())
     * Thread调用了stop()方法后会会抛出ThreadDeath这样的异常(实际上它继承了Error)
     * 而程序中不能简单就catch住ThreadDeath,这是因为try-catch-finally会在线程真正
     * 被终止前先执行,如果catch住了这个异常,那么就必须将这个对象重新抛出
     * 该方法是天生不安全的,因为在调用这个方法的时候会将该线程上持有的所有monitor都释放掉,这样,被某些monitor
     * 所保护的资源就有可能被其他竞争得到锁的线程所修改,造成同步的问题
     */
    
    /*
     * public final void join() throws InterruptedException
     * 表示等待线程直到线程结束
     * 也就是说假如在线程t1中调用了另一个线程t2的join()方法,那么线程t1会等待t2线程执行
     * 完毕才会继续执行(实际上调用了join(0))
     * public final synchronized void join(long millis) throws InterruptedException
     * 该方法是join方法的真正实现,实现原理如下(以join(0)为例):
     * 核心代码为
     * while (isAlive()) {
     *          wait(0);
     *      }
     * 在t1线程的代码块中调用了t2线程的join方法,首先t1线程会获取t2对象的锁,然后while循环不断检查t2线程是否是存活的
     * 如果是,那么调用t2对象的wait()方法,由于t1线程持有t2对象的锁又调用了wait方法,那么t1线程就被挂起,当t2线程完成后,
     * t2对象调用notifyAll()方法,t1线程被唤醒
     */
    
    /*
     * public static native void yield()
     * 线程让步,意味着线程可能会让出cpu供其他线程执行,然而,这只是一种暗示,调度器可以让线程让出也可以不让出cpu
     */
    
    /*
     * public final void setDaemon(boolean on) 将线程设置为守护(后台)线程,实际上守护线程与普通(用户)线程
     * 并没有什么本质上的区别,当所有正在运行的线程都是守护线程的时候,JVM就会退出.而JVM退出的时候,仍在执行的守护线程会被直接终止掉,
     * 因此守护线程最好执行"内部"任务(比如定时清理缓存)
     * 垃圾回收器就是守护线程.
     */
    

}
