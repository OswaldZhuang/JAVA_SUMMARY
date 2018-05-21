package concurrence.chapter8_threadpool;

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
     *    void shutdown()
     *    List<Runnable> shutdownNow()
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
     * 通过setRejectedExecutionHandler(RejectedExecutionHandler handler)来设置饱和策略
     * ThreadPoolExecutor有几种饱和策略(实际都是implements RejectedExecutionHandler):
     *    CallerRunsPolicy 实现一种调节机制,该机制不会抛弃任务,而是直接执行该任务(具体来说就是直接调用run方法)
     *    (这样一来调用线程池的调用者就会被run方法阻塞以降低新任务的流量)
     *    AbortPolicy 将直接抛出异常
     *    DiscardPolicy 什么都不做,'悄悄'抛弃任务
     *    DiscardOldestPolicy 抛弃最旧的任务(即下一个即将被执行的任务),然后执行最新提交的任务
     * 钩子方法(hook)用于在执行每个任务之前(后)执行,有两个:beforeExecute(Thread, Runnable),afterExecute(Runnable, Throwable)
     * 当线程池在程序中没有被引用并且没有存活的线程的时候,线程池会自动调用shutdown()方法
     * ThreadPoolExecutor的主要属性和方法如下:
     * private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));表示线程池的控制状态,该量为runState | workerCount
     * 运行状态在高位,工作线程数在低位
     * 线程池定义了如下运行状态量(COUNT_BITS为29):
     * RUNNING = -1 << COUNT_BITS
     * SHUTDOWN = 0 << COUNT_BITS:不接收新的任务,但是会处理队列中的任务
     * STOP =  1 << COUNT_BITS:不接收新的任务,不处理队列中的任务,并且中断正在运行的任务
     * TIDYING = 2 << COUNT_BITS:终止所有任务,当过渡到TIDYING时会调用terminated()方法
     * TERMINATED = 3 << COUNT_BITS:terminated()方法调用完毕
     * private final BlockingQueue<Runnable> workQueue;等待队列
     * private final ReentrantLock mainLock = new ReentrantLock();锁主要用作保护工作线程集和一些相关状态变量,在中断的时候有序进行
     * 同时shutdown和shutdownNow也会用到锁
     * private final HashSet<Worker> workers = new HashSet<Worker>();工作线程集,受mainLock保护
     * private final Condition termination = mainLock.newCondition();条件队列用于支持awaitTermination
     * private int largestPoolSize;最大池大小,受mainLock保护
     * private long completedTaskCount;完成任务数,受mainLock保护
     * private volatile ThreadFactory threadFactory;线程工厂,专门用于产生工作线程
     * private volatile RejectedExecutionHandler handler;饱和策略
     * private volatile long keepAliveTime;空闲线程的超时时间(前提是线程数多于核心线程数或者是allowCoreThreadTimeOut)
     * private volatile boolean allowCoreThreadTimeOut;是否允许核心线程超时
     * private volatile int corePoolSize;
     * private volatile int maximumPoolSize;
     *  
     * class  ThreadPoolExecutor#Worker extends AbstractQueuedSynchronizer implements Runnable
     * 线程池中的工作者,之所以会继承AQS,是因为Worker会执行多个提交进来的任务,于是就存在多个任务"争夺"
     * 一个工作者的情况,因此这个工作者是需要"同步"的.Worker的实现是非重入的,这是因为设计者并不希望
     * 任务调用pool控制方法(比如setCorePoolSize)的时候重新获取锁
     * Worker中持有Thread,用于执行任务
     * 
     * public ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
     *                         BlockingQueue<Runnable> workQueue,
     *                         ThreadFactory threadFactory,
     *                         RejectedExecutionHandler handler)
     *  线程池的核心构造函数
     *  
     *  public void execute(Runnable command)提交任务,该方法是整个线程池的核心方法,其执行过程如下:
     *  首先,如果工作线程的数量小于corePoolSize,那么调用addWorker方法新建线程并执行任务
     *  如果大于corePoolSize且线程池处于运行状态,并且加入队列成功,那么再次检查状态量,如果线程池停止并且
     *  该任务从队列中移除成功,那么调用拒绝策略拒绝该任务,如果当前线程池中没有工作线程,那么调用addWorker创建空任务的线程
     *  如果无法加入队列,那么调用addWorker新建线程去执行任务,如果无法创建,那么调用拒绝策略拒绝任务
     * 
     * private boolean addWorker(Runnable firstTask, boolean core)查看是否能够创建新的Thread用于任务的执行
     * 如果可以的话就创建新的Worker并且以firstTask作为Worker的首个任务,core为true则以corePoolSize为判断标准
     * 否则为maximumPoolSize
     * 执行过程如下:
     * 分别查看线程池状态以及当前Worker的数量,如果符合要求,新创建Worker,之后再次查看线程池状态,一切无误后worker
     * 加入到workerSet,并启动线程执行任务(Worker#run)
     * 
     * final void runWorker(Worker w) 这是Worker#run的实际实现方法,传入的参数是this,是Worker执行任务的核心方法
     * 该方法会重复的向队列中拿取任务(getTask)并执行(实际上就是调用run方法),如果firstTask为空并且队列当中没有任务可取,那么
     * 就会跳出循环执行processWorkerExit以完成工作线程的退出操作,在任务执行之前需要对Worker加锁,任务执行完成之后
     * 释放锁
     * 
     * private Runnable getTask() 从等待队列中获取任务,具体过程为:
     * 不断循环,在此期间:如果线程池状态为stopped或者(shutdown并且队列为空),那么workerCount减1并返回null,
     * 如果设置核心线程会超时,并且当前线程数量大于核心线程数,那么就会以keepAliveTime为超时时间在队列中获取任务
     * 如果工作线程数量已经大于最大线程数量(或者当前线程已经超时获取任务)并且工作线程数量大于1(或者队列为空),
     * 那么workerCount减1,返回null
     * 
     *  private void processWorkerExit(Worker w, boolean completedAbruptly) 该方法主要清除或替换工作线程,completedAbruptly
     *  表示线程是否意外终止,该方法只是由Worker调用(runWorker中调用),其执行过程如下:
     *  如果completedAbruptly为true,那么workerCount减1,
     *  completedTaskCount加1,从workers中移除该worker(该过程受mainLock保护)
     *  之后调用tryTerminate
     *  再判断如果当前的工作线程数量比要求的数量(corePoolSize或者1)大,直接返回,否则创建新的无任务的Worker
     *  
     *   public void shutdown()线程池关闭,停止之前提交的正在执行的任务,但不会接受新任务(其实现精髓就在于
     *   addWorker方法对线程状态的判断从而拒绝任务),该方法不会等待活跃的执行任务的线程
     *   终结(可采用awaitTermination实现该目标),正在执行任务的线程会继续执行其任务
     *   该方法受mainLock保护,执行过程如下:
     *   首先检查是否有关闭线程的权限,然后把线程池状态设置为SHUTDOWN
     * 	  随后中断空闲线程(interruptIdleWorkers)
     *   最后调用tryTerminate将其状态变化为TERMINATED
     *   shutdown是一种比较平滑的关闭方式
     *   
     *   public List<Runnable> shutdownNow()停掉所有正在执行的任务,返回等待被执行的任务
     *   其过程与shutdown方法类似,不同的在于其终止线程采用的是interruptWorkers,这样所有工作线程都会被中断
     *   shutdownNow是一种比较"陡峭"的关闭方式
     * 
     * private void interruptIdleWorkers(boolean onlyOne) 终止空闲的线程,实际上就是终止正在等待任务的Worker,而判断Worker是否空闲的方法即为
     * 该Worker是否能够被获取(tryLock),onlyOne表示只中断一个线程,该方法执行过程为:遍历WorkerSet并中断那些空闲的线程
     * 
     * final void tryTerminate()该方法主要用于将线程池的状态变为TERMINATED,主要过程为:
     * 不断循环,在此期间:
     * 如果当前线程池的状态是RUNNING或者至少是TIDYING或者SHUTDOWN并且工作队列不为空,则直接返回
     * 如果WorkerCount不为0,那么中断其中一个空闲线程并返回
     * 将线程池状态设置为TIDYING,然后调用terminated()钩子方法,最后状态设置为TERMINATED,并唤醒所有在termination上
     * 等待的线程,该过程受mainLock保护
     */ 
	
	
     /* 
     * java.util.Executors
     * 该类是产生线程池的工厂类
     */
    
    /*
     * Interface java.util.concurrent.CompletionService<V>
     * 
     * Class java.util.concurrent.ExecutorCompletionService<V> implements CompletionService<V>
     */
}
