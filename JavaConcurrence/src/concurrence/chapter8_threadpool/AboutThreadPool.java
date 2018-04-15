package concurrence.chapter8_threadpool;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.Future;

/*
 * 线程池
 * 
 */
public class AboutThreadPool {
    
    /*
     * 线程池类的最基础最原始的接口为Interface java.util.concurrent.Executor
     * 该接口下只有一个方法void execute(Runnable command);即用于提交任务
     * 实际上这样做使得任务的提交和运行得到解耦,让专门的线程池负责线程的运行,这样,开发者仅仅需要创建一些
     * Runnable的任务,然后提交给Executor执行即可
     * 
     * Interface java.util.concurrent.ExecutorService extends Executor
     * ExecutorService增强了Executor,包括
     *  1.终止任务执行 
     *    void shutdown()(正在执行的任务不会被停掉,而新来的任务不会被执行) 
     *    List<Runnable> shutdownNow(强制关闭,正在执行的任务会立刻停止,返回正在等待执行的任务)
     *    boolean awaitTermination(long timeout, TimeUnit unit)(等待要么所有任务完成,要么超时才停止ExectorService)
     *    实际上线程池中任务的终止是采用中断的方式
     *  2.提交任务
     *    <T> Future<T> submit(Callable<T> task)(提交一个callable任务)
     *    <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks)(提交一系列的任务,会等待所有任务完成,
     *    返回所有任务的Future)
     *    <T> T invokeAny(Collection<? extends Callable<T>> tasks)(只要其中一个任务成功返回就会结果)
     *    
     * Abstract Class java.util.AbstractExecutorService implements ExecutorService
     * 该类提供ExecutorService执行任务方法的默认实现(submit,invokeAll,invokeAny),返回结果为java.util.concurrent.RunnableFuture
     * invokeAny方法底层是由java.util.concurrent.ExecutorCompletionService执行的
     * public <T> Future<T> submit(Callable<T> task) 该方法传入的callable会被包装成为FutureTask,再调用execute继续执行
     * execute在子类中实现
     *  
     * Interface java.util.concurrent.ScheduledExecutorService extends ExecutorService
     * 执行定时任务的执行器
     *  public ScheduledFuture<?> schedule(Runnable command,long delay, TimeUnit unit)
     * 
     * 
     * Class java.util.concurrent.ThreadPoolExecutor extends AbstractExecutorService
     * 该类是线程池中比较核心的实现类
     * corePoolSize:当新的任务提交到线程池时,如果目前正在执行的任务数量小于corePoolSize,那么线程池就会创建新的线程为之
     * 执行任务(即使其他的工作线程是空闲的)
     * maximumPoolSize:如果工作线程的数量大于corePoolSize但是小于maximumPoolSize,新提交的任务会进入到队列中
     * 等待,如果队列满了,那么就会创建新的线程,如果工作线程数等于maximumPoolSize,那么新提交的任务就会被reject掉(并不一定会reject,
     * 有饱和策略Saturation Policy)
     * 新的线程是由java.util.concurrent.ThreadFactory创建的
     * 当线程池有超过corePoolSize数量的线程,超过数量的并且超过keepAliveTime时间的空闲线程会被终止掉
     * 排队等待的线程是存储在java.util.concurrent.BlockingQueue<E>中,有三种入队方式
     * 1.直接移交 java.util.concurrent.SynchronousQueue<E> 当有新的任务入队,队列不会存储这个任务而是直接把他
     * 交给空闲线程(或者创建新的)去处理,这种情况下maximumPoolSize需要无限大,Executors.newCachedThreadPool即采用此策略
     * 2.无界队列,java.util.concurrent.LinkedBlockingQueue<E>,等待的队列是无界的,也就是说如果线程数量为corePoolSize
     * 无论多少新的任务进来都会入队,除非有线程空闲,否则任务不会被执行,Executors.newFixedThreadPool和newSingleThreadExecutor采用此策略
     * 3.有界队列,java.util.concurrent.ArrayBlockingQueue<E>
     * 饱和策略:当工作线程数量为maximumPoolSize并且有界队列已满的时候(或者线程池关闭),线程池的execute方法会调用
     * RejectedExecutionHandler#rejectedExecution(Runnable, ThreadPoolExecutor)来拒绝任务
     * 通过etRejectedExecutionHandler(RejectedExecutionHandler handler)来设置饱和策略
     * ThreadPoolExecutor有几种饱和策略(实际都是implements RejectedExecutionHandler):
     *    CallerRunsPolicy 实现一种调节机制,该机制不会抛弃任务,而是直接执行该任务(具体来说就是直接调用run方法)
     *    (这样一来调用线程池的调用者就会被run方法阻塞以降低新任务的流量)
     *    AbortPolicy 将直接抛出异常
     *    DiscardPolicy 什么都不做,'悄悄'抛弃任务
     *    DiscardOldestPolicy 抛弃最旧的任务(即下一个即将被执行的任务),然后执行最新提交的任务
     * 钩子方法(hook)用于在执行每个任务之前(后)执行,有两个:beforeExecute(Thread, Runnable),afterExecute(Runnable, Throwable)
     * 
     * 
     * java.util.Executors
     * 该类是产生线程池的工厂类
     */
    
    /*
     * Interface java.util.concurrent.CompletionService<V>
     * 
     * Class java.util.concurrent.ExecutorCompletionService<V> implements CompletionService<V>
     */
    public static void main(String[] args) {
    }
}
