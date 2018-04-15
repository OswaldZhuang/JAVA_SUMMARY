package concurrence.chapter5_basicmodule;

/*
 * java线程中的执行任务
 */
public class ExecutionTask {
    
    /*
     * Interface java.lang.Runnable
     * 表示可执行的任务,该类只有一个run()方法,线程执行的时候会调用run()方法
     */
    
    /*
     * Interface java.util.concurrent.RunnableFuture<V> extends runnable, Future<V>
     * 表示可执行的Future,主要的执行方法为run()方法,获得执行结果则调用get()
     */
    
    /*
     * java.util.concurrent.FutureTask<V> implements RunnableFuture<V> 
     * 该类需要配合线程池使用
     * 该类有如下属性:
     * private volatile int state;表示任务的状态,包括NEW,COMPLETING,NORMAL....
     * private Callable<V> callable;表示需要执行的任务
     * private Object outcome;任务的执行结果
     * private volatile Thread runner;执行任务的线程,用于执行callable
     * private volatile WaitNode waiters;用于等待线程的Treiber stack 
     * FutureTask维护了内部的数据结构WaitNode,该结果只是个简单的单向链表,内部存储的是Thread
     * public FutureTask(Callable<V> callable) 构造函数,传入参数为callable
     * public FutureTask(Runnable runnable, V result) 构造函数,runnable表示需要执行的任务,result
     * 表示在成功执行任务之后的返回值(通过get()获得),该方法底层实际上是工具类Executors#(runnable,result)转换为
     * callable
     * public boolean cancel(boolean mayInterruptIfRunning) 用于取消任务,mayInterruptIfRunning表示是否要
     * 中断线程,最后会调用finishCompletion()
     * public V get() throws InterruptedException, ExecutionException 获取任务执行的结果,首先会检查state,如果
     * 还未完成,那么就会调用awaitDone(false, 0L)
     * public void run() 用于执行callable(实际上就是调用call方法)
     * protected boolean runAndReset() 执行callable但是不会设置结果,执行完之后将state设置到初始状态
     * private void finishCompletion() 该方法会移除和通知所有的等待线程,执行过程为:将等待队列中的WaitNode的线程唤醒并出队
     * private int awaitDone(boolean timed, long nanos) throws InterruptedException 等待任务执行完毕
     * 不断循环,轮询执行的时间和任务的状态,如果任务的状态是COMPLETING,即正在执行,那么调用Thread.yield,表示当前线程(可能)让出CPU来
     * 给任务执行
     * 
     */
    
    /*
     * Interface java.util.concurrent.Callable<T>
     * 类似于Runnable,Callable也是表示线程执行的任务,只有一个方法
     * V call() throws Exception 表示该任务会返回结果,并且可以抛出异常
     */

}
