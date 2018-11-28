package concurrence.chapter6_taskexecution;

/*
 * java线程中的执行任务
 */
public class AboutFutureTask {
    
    /*
     * Interface java.lang.Runnable
     * 表示可执行的任务,该类只有一个run()方法,线程执行的时候会调用run()方法
     */
    
    /*
     * Interface java.util.concurrent.RunnableFuture<V> extends Runnable, Future<V>
     * 表示可执行的Future,主要的执行方法为run()方法,获得执行结果则调用get()
     */
    
    /*
     * java.util.concurrent.FutureTask<V> implements RunnableFuture<V> 
     * 该类需要配合线程池使用
     * 该类有如下属性:
     * private volatile int state;表示任务的状态,包括NEW,COMPLETING,NORMAL....
     * private static final int NEW          = 0;表示新建任务
     *	private static final int COMPLETING   = 1;表示任务执行完成但是还没有为结果赋值
     *	private static final int NORMAL       = 2;表示任务正常结束
     *	private static final int EXCEPTIONAL  = 3;表示任务出错
     *	private static final int CANCELLED    = 4;表示任务已经被取消
     *	private static final int INTERRUPTING = 5;表示任务正在被中断
     *	private static final int INTERRUPTED  = 6;表示任务已经被中断
     * private Callable<V> callable;表示需要执行的任务
     * private Object outcome;任务的执行结果
     * private volatile Thread runner;执行任务的线程,用于执行callable
     * private volatile WaitNode waiters;FutureTak的等待队列
     * public FutureTask(Callable<V> callable) 构造函数,传入参数为callable
     * public FutureTask(Runnable runnable, V result) 构造函数,runnable表示需要执行的任务,result
     * 表示在成功执行任务之后的返回值(通过get()获得),该方法底层实际上是工具类Executors#(runnable,result)转换为
     * callable
     * public boolean cancel(boolean mayInterruptIfRunning) 用于取消任务,mayInterruptIfRunning表示是否要
     * 中断线程,最后会调用finishCompletion()
     * public V get() throws InterruptedException, ExecutionException 获取任务执行的结果,首先会检查state,如果
     * 还未完成,那么就会调用awaitDone(false, 0L),最后调用report方法报告结果
     * private V report(int s) throws ExecutionException 根据状态值返回执行结果
     * public void run() 用于执行callable(实际上就是调用call方法),如果执行发生了异常,那么调用setException设置异常
     * 如果顺利执行完毕,那么调用set设置结果
     * protected void set(V v) 设置状态为COMPLETING再设置结果到outcome,在设置状态为NORMAL,最后调用finishCompletion
     * protected void setException(Throwable t) 同上,不过最终状态为EXCEPTIONAL
     * protected boolean runAndReset() 执行callable但是不会设置结果,执行完之后将state设置到初始状态
     * private void finishCompletion() 该方法会移除和通知所有的等待线程,执行过程为:将等待队列中所有的WaitNode的线程唤醒并出队
     * private int awaitDone(boolean timed, long nanos) throws InterruptedException 等待任务执行完毕,timed表示是否会超时,
     * 返回任务状态
     * nanos表示超时时间,执行过程为不断循环,期间:
     * 如果当前线程被中断,那么从队列中移除WaitNode,并抛出InterruptedException
     * 如果任务状态大于COMPLETING,那么将WaitNode中的线程置为空并直接返回状态量,
     * 如果状态为COMPLETING,那么就会线程让步,尽可能让出cpu供其他任务执行
     * 如果WaitNode为空那么就会新创建WaitNode,并且线程赋值为当前线程
     * 如果WaitNode还未入队,那么CAS将其入队
     * 如果设置了超时且超时时间已过,那么直接移出队列并返回状态,如果还未超时,将当前线程在剩余时间内挂起
     * 如果上述条件都不满足(这种情况就是任务尚未执行完毕,且该线程在等待队列中),那么将当前线程挂起,等待其他线程唤醒
     * (实际上唤醒的地方在run方法内)	
     * 
     * FutureTask#WaitNode 该内部类表示等待在FutureTask上的线程(通常是等待任务执行的结果),是单向链表结构
     */
    
    /*
     * Interface java.util.concurrent.Callable<T>
     * 类似于Runnable,Callable也是表示线程执行的任务,只有一个方法
     * V call() throws Exception 表示该任务会返回结果,并且可以抛出异常
     */
	
}
