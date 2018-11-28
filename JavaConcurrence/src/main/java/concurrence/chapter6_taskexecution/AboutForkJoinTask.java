package concurrence.chapter6_taskexecution;

public class AboutForkJoinTask {
    /**
     * abstract class java.util.concurrent.ForkJoinTask<V> implements Future<V>
     * 运行在ForkJoinPool中的任务.ForkJoinTask是类似于线程的实体但比普通线程更加轻量级,在ForkJoinPool中
     * 大量的任务和子任务可能仅仅被少数实际的线程所管理,以一些使用上的限制作为代价.
     * 当ForkJoinTask被显式地提交到ForkJoinPool的时候,或者如果还没有加入ForkJoin计算,
     * 通过fork,invoke和其他相关方法开始于commonPool的时候,ForkJoinTask开始执行.一旦开始,它常常会进一步开始
     * 其他的子任务.
     * 该类除了fork/join还有其他非常先进的用法.
     * ForkJoinTask是轻量级的Future,ForkJoinTask的效率出自于一系列的约束(这些约束仅仅是部分地强制性地),反映了
     * 其主要用途是作为计算性的任务(纯粹的计算功能或者在完全孤立的对象上操作).主要的协调机制是fork方法,该方法分配异步
     * 执行,join方法直到任务的结果被计算完成才会继续.计算理想状态下应该避免同步(synchronized)方法和阻塞,并且除了
     * 合并其他任务应该最小化其他的阻塞同步,或者是使用同步器,比如Phasers(与fork/join调度协作).可分割的子方法同样
     * 不应该有阻塞的I/O操作并且理想情况下应该访问那些完全与其他正在运行的任务所隔离的对象.该类强制的不允许抛出checked
     * exception(比如IOException).然而,计算仍然会遇到unchecked exception,这些异常抛向试图去合并任务的调用者.
     * 这些异常可能会额外的包含RejectedExecutionException,出自于内部资源的耗尽,比如说分配内部任务队列失败.重新抛出的
     * 异常和普通异常的行为是一致的,但是,当可能的时候,重新抛出的异常会包含两个线程的stack trace(一个是发起计算的线程,另一个
     * 是真正遇到异常的线程).
     * 定义和使用阻塞的ForkJoinTasks是有可能的,但是需要长远的考虑三点:
     * 1.如果任何其他任务会依赖于一个被外部同步或IO阻塞的任务,那么极少数任务会完成.事件风格的异步任务(从不合并,比如CountedCompleter)
     * 常常是上述情况.
     * 2.为了最小化资源的影响,任务应该很小,理想情况下仅仅是阻塞行为.
     * 3.除非是ForkJoinPool.ManagedBlocker API被使用,或者是肯能被阻塞的任务数量是已知少于线程池的并行等级,线程池无法保证
     * 足够的线程能够可用.
     * 等待完成并提取任务的结果的一般方法是join方法,但是有一些变体:
     * Future#get方法支持可中断和限时的等待,并且采用Future的规约来获取结果.
     * invoke方法语意上和fork() join()相同,但是常常试图在当前线程开始执行.
     * 这些方法的quiet形式不会提取结果或者报告异常.当一系列的任务正在被执行并且你需要在所有任务完成之前延迟结果的处理或者异常,那么
     * quiet形式是很有用的.
     * invokeAll方法执行最常见的并行形式:分开一些的任务并且将他们合并在一起.
     * 在最典型的用法中,fork-join 表现类似于从一个并行递归方法调用(fork)并且返回(join).因为是其他递归调用的形式,返回(join)应该
     * 第一个最邻近执行.比如,a.fork(); b.fork(); b.join(); a.join();实质上比先a.join()再b.join()更加有效.
     * 任务的执行状态可能会在几个细节等级被查询:
     * 如果一个任务以任何方式完成那么isDone返回true(包括任务取消);
     * 如果任务在没有取消或者遇到异常的时候完成,那么isCompletedNormally返回true;
     * 如果任务被取消(即getException方法返回CancellationException),那么isCancelled返回true;
     * 如果任务要么被取消要么遇到异常,isCompletedAbnormally返回true.
     * ForkJoinTask类并不常常直接用作继承.而是,继承一个支持特定fork/join处理风格的子类,典型的就是递归任务为大多数不需要返回结果
     * 的计算,CountedCompleter中完成的任务触发其他动作.一般地,具体的ForkJoinTask子类声明的属性包含其参数,在构造器中创建,并且
     * 定义一个一定程度上使用本类的控制方法的compute方法.
     * 只有当完成依赖不是环状的join方法和其变体才是合适的.也就是,并行计算可以被描述为一个有向无环图(DAG).否则执行就会遇到死锁因为任务
     * 会环状地等待对方.然而,该框架支持其他方法和技术(比如说Phaser,helpQuiesce,complete的应用),这些方法用作构造那些并不严格是DAG
     * 的子类(即解决环的问题).为了支持这样的用法,一个ForkJoinTask可能会被原子地被标记上一个short类型的值,(采用setForkJoinTaskTag
     * 或者compareAndSetForkJoinTaskTag),并采用getForkJoinTaskTag来检查.ForkJoinTask的实现不会以任何目的使用protected方法或者标签,
     * 但是这些protected方法或标签可能会被特定子类使用.比如说,并行图的遍历可以使用提供的方法来避免重复访问那些处理过的节点(任务).
     * 大多数的基本支持方法是final的,以此避免对内部轻量级任务调度有关的方法的重写.开发者应该最小化地实现protected方法:exec,setRawResult,
     * getRawResult,同时也要在子类中实现抽象的计算方法(可能会依赖于该类的protected方法).
     * ForkJoinTasks应该执行相对小数量的计算.大的任务应该被拆分为更小的子任务,常常通过递归来拆解.非常粗略的规则就是:一个任务应该执行
     * 100～10000个基本执行步骤.并且要避免无限的循环.如果任务过大,那么并行不能提高吞吐量.如果过小,那么内存和内部任务维护上线可能过载.
     * 该类为Runnable和Callable提供适配方法以允许不同类型任务的提交.当所有任务都是ForkJoinTask类型,那么考虑以asyncMode构造的线程池.
     * ForkJoinTasks是可序列化的.这使得它可以被外部使用,比如远程执行框架.只有在执行之前和之后序列化任务是敏感的,执行期间并不是.
     * 序列化并不依赖于执行期间本身.
     */

    /**
     * ForkJoinTask主要用于维护基本的状态属性,执行和等待结果
     * 状态属性持有运行控制状态位,其被打包进了一个int类型以减小内存消耗和确保原子性.状态初始值位0,完成后为负数.有(NORMAL, CANCELLED, EXCEPTIONAL)
     * (这些值与DONE_MASK进行和操作).被其他线程阻塞等待的执行中的任务会设置SIGNAL位.设置了SIGNAL位并且已经完成了的窃取的任务会通过notifyAll来
     * 通知其他等待者.尽管因为一些目的而不理想,我们使用基本的内置的wait/notify机制来利用JVM中的锁膨胀,否则我们需要模仿上述机制来避免加入更多的
     * 每个任务的记录的开销.我们希望这些monitor更加的"大",比如说不使用偏向锁或者轻量级锁技术,所以使用一些古怪的编码风格以避免他们,主要是通过分配每个
     * 同步块能够执行wait,notifyAll.
     * 控制位仅仅占用高16位.低位用于用户定义的内容.
     */

    /**
     * 属性及方法如下:
     * volatile int status; 直接由线程池和工作者访问.
     * static final int DONE_MASK   = 0xf0000000; 遮盖未完成的位
     * static final int NORMAL      = 0xf0000000; 该值为负数
     * static final int CANCELLED   = 0xc0000000; 该值小于NORMAL
     * static final int EXCEPTIONAL = 0x80000000; 该值小于CANCELLED
     * static final int SIGNAL      = 0x00010000; 该值在[1, 16]之间
     * static final int SMASK       = 0x0000ffff; 低位标签
     */

    /**
     * private int setCompletion(int completion) 将任务设置为完成状态,并且唤醒其他等待线程,
     * 参数为NORMAL, CANCELLED, EXCEPTIONAL
     * 返回值为完成状态
     * 无限循环,期间判断,如果status小于0,那么直接返回status,如果以status|completion cas status成功的话,那么调用给该对象加锁
     * 并调用notifyAll,最后返回completion.(之所以采用或操作是因为可保留用户自定义的位)
     * */

    /**
     * final int doExec() 被窃取任务的一般执行方法,
     * 如果status大于0(即还未完成),那么调用exec(),如果期间出错,调用setExceptionalCompletion记录错误.如果exec方法的返回值为true,
     * 也就是已完成,那么setCompletion(NORMAL)
     * */

    /**
     * final void internalWait(long timeout) 如果任务未完成,那么设置SIGNAL状态,并且执行wait
     * 如果status大于0且以status|SIGNAL cas  status成功,那么给该对象加锁,再判断如果status>=0(未完成),那么wait(timeout),否则notifyAll()
     * */

    /**
     * private int externalAwaitDone() 阻塞非工作者线程,返回完成时的状态
     *
     */
}
