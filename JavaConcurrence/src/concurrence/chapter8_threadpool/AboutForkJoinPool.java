package concurrence.chapter8_threadpool;

public class AboutForkJoinPool {
	/*
	 * class java.util.concurrent.ForkJoinPool extends AbstractExecutorService
	 * 是在java7中新增加的一种线程池
	 * 继承AbstractExecutorService使得该线程池可以提交非ForkJoinTask的任务(即Runnable, Callable)
	 * ForkJoinPool采用"工作窃取"的方式:线程池内的所有线程都会试图去寻找和执行提交到线程池中的任务
	 * (或者其他活跃的任务生成的任务),这种做法针对以下情况是比较高效的:
	 * 1.大多数的任务会生成子任务;
	 * 2.大量较小的任务提交到线程池中.
	 * 当asyncMode在构造函数中设置为true时,ForkJoinPool非常适用于还未合并在一起的事件风格的任务
	 * 静态方法ForkJoinPool#commonPool()是常用的构造方法,commonPool能够降低资源的使用(因为在
	 * 没有使用的时候会缓慢的回收线程,并且在使用中恢复)
	 * 对于定制的ForkJoinPool而言,其构造器提供了"并行等级"的参数,默认情况下,其等于可用处理器数量,
	 * ForkJoinPool试图通过添加,挂起,恢复等去维护足够数量的活跃线程.但这种调整并不能得到保证,特别是
	 * 遇到阻塞IO或者其他同步操作的时候,继承内部接口ForkJoinPool#ManagedBlocker使得上述同步得到
	 * 一定的安置
	 * 只有当线程池关闭或者内部资源耗尽的时候ForkJoinPool才会拒绝任务(抛出RejectedExecutionException)
	 * 
	 * WorkQueues:
	 * 内部类ForkJoinPool#WorkQueue,大多数操作都与该"工作窃取"队列有关,该队列是一种特殊的双端队列(只
	 * 支持3种操作:push,pop,poll(aka steal))(push和pop操作仅仅被拥有者线程调用,或者说在锁保护下调用,而poll
	 * 操作是被其他线程调用),
	 * 队列支持"工作窃取"和外部的任务提交
	 * 其主要属性和方法如下:
	 * static final int INITIAL_QUEUE_CAPACITY = 1 << 13 初始队列大小,队列大小必须是2的指数,至少是4但必须
	 * 更大来减少或者消除队列间的缓存行共享
	 */

}
