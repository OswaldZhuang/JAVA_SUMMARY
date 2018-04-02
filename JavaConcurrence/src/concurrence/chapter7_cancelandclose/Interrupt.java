package concurrence.chapter7_cancelandclose;

/*
 * 中断
 */
public class Interrupt {
    
    /*
     * interrupt是一种协作机制,
     * 实际上中断操作并不会真正的中断正在执行的线程,而是发出中断的请求,然后被中断
     * 的线程接受中断的信号,之后根据这个中断信号去作适当的处理
     * 中断是实现任务取消的比较好的方式
     */
    
    
    /*
     * 实际上在java.lang.Thread中存储着一个boolean类型的中断状态(然而该变量并未在Thread类中定义)
     * 在一些阻塞方法如Thread#sleep Object#wait会检查中断状态,一旦发现状态为true
     * 那么就会<清除中断状态>并且抛出IterruptedException
     * 对于会抛出InterruptedException的方法(意味着那个方法是阻塞的),一般的处理方法包括:
     * 1.将这个异常向外抛,即throws InterruptedException
     * 2.恢复中断的状态,即
     *  catch(IterruptedException ie){
     *      Thread.currentThread.interrupt();
     *  }
     * 
     * java.lang.Thread中提供了一些关于中断的方法:
     * 1.public void interrupt()
     *   该方法能够中断线程(线程可以中断自己也可以中断别的线程),实际上该方法的实现是一个本地方法
     * 2.public static boolean interrupted()
     *   该方法是个静态方法,用于检验当前的线程的中断状态,当调用完这个方法之后中断的状态会被重新设定,他的实现仍然是本地方法
     * 3.public boolean isInterrupted()
     *   该方法作用同上,但并不会重置中断状态
     * 实际上2.3方法的底层调用都是private native boolean isInterrupted(boolean ClearInterrupted)
     * 
     * 
     */
    
    /*
     * 在java.util.concurrent.Future<T>定义了boolean cancel(boolean mayInterruptIfRunning)方法
     * 用于取消任务,其中mayInterruptIfRunning表示线程能否接收中断
     */
    
    /*
     * 当某些方法是阻塞的,但是又无法响应中断的时候(比如同步的IO操作不会抛出InterruptedException),需要视具体情况而为,
     * 
     */
    
    public void useInterrupt() {
        
    }

}
