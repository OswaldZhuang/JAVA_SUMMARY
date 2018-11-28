package concurrence.chapter8_threadpool;

public class AboutForkJoinPool {
	/**
	 * class java.util.concurrent.ForkJoinPool extends AbstractExecutorService
	 * 是在java7中新增加的一种线程池
	 * 继承AbstractExecutorService使得该线程池可以提交非ForkJoinTask的任务(即Runnable, Callable)
	 * ForkJoinPool采用"工作窃取"的方式:线程池内的所有线程都会试图去寻找和执行提交到线程池中的任务(如果没有认为就会阻塞)
	 * (或者其他活跃的任务生成的任务),这种做法针对以下情况是比较高效的:
	 * 1.大多数的任务会生成子任务;
	 * 2.大量较小的任务提交到线程池中.
	 * 当asyncMode在构造函数中设置为true时,ForkJoinPool非常适用于不会合并在一起的事件风格(event-style)的任务
	 * 静态方法ForkJoinPool#commonPool()是常用的构造方法,common pool可以被任何没有显式提交到指定池中的ForkJoinTask使用
     * commonPool能够降低资源的使用(因为在没有使用的时候会缓慢的回收线程,并且在使用中恢复)
	 * 对于定制的ForkJoinPool而言,其构造器提供了"并行等级"的参数,默认情况下,其等于可用处理器数量,
	 * ForkJoinPool试图通过添加,挂起,恢复等去维护足够数量的活跃线程.但这种调整在面对阻塞IO和无法管理的同步时并不能得到保证,
     * 相关的接口ForkJoinPool#ManagedBlocker使得上述同步得到一定的实现
     *
     * 除了执行和生命周期控制方法,本类还提供了状态检查方法(比如getStealCount),用于在fork/join应用的运行,调整,监控中的"帮助".
     * 同样的,toString方法会返回线程池的状态信息.
     *
     * 因为是ExecutorServices中的一种,本类有三种主要的任务执行方法:execute(ForkJoinTask),invoke(ForkJoinTask),submit(ForkJoinTask)
     * 这些方法用于被那些还未加入到fork/join计算的用户使用.这些方法需要传入ForkJoinTask实例.但是这些方法的重载形式也允许
     * 混合执行Runnable,Callable.然而早已在线程池中执行的任务应该使用ForkJoinTask#fork,ForkJoinTask#invoke,ForkJoinTask#fork
     * 除非使用异步的事件风格且不会经常合并的任务,上述方法的选择区别较小.
     *
     * common pool以默认参数构造,但是这些也可以通过设置三个system properties来控制:
     * java.util.concurrent.ForkJoinPool.common.parallelism
     * java.util.concurrent.ForkJoinPool.common.threadFactory
     * java.util.concurrent.ForkJoinPool.common.exceptionHandler
     *
     * 需要注意的是,本类的最大运行线程数限制在32767,如果创建线程池的时候试图去传入更大的值将会抛出IllegalArgumentException
     *
	 * 只有当线程池关闭或者内部资源耗尽的时候ForkJoinPool才会拒绝任务(抛出RejectedExecutionException)
	 */

	/**
	 * WorkQueues:
	 * 内部类ForkJoinPool#WorkQueue,大多数操作都与该"工作窃取"队列有关,该队列是一种特殊的双端队列(只
	 * 支持3种操作:push,pop,poll(aka steal,即窃取操作))(push和pop操作仅仅被拥有者线程调用,或者说在是持有该对象的锁的线程,
	 * 而poll操作可以是被其他线程调用),
	 * 该队列与一般的队列根本上的不同在于GC的需求,在slot出队的时候,尽快将其设置为null,因此尽管在程序产生大量任务的时候
	 * 都能保持尽可能尽可能小的footprint(内存消耗),为了达到这一点,CAS pop以及poll的方式由index(游标)转换成了slot本身
	 * (pop操作是由队列的"拥有者"执行,而poll是由窃取者操作)
	 * 因为我们依靠引用的CAS操作,我们不需要在top(即队列顶部)或者base采用bits标记,这些引用仅仅是在环形数组队列中使用的ints标记,
	 * top==base意味着队列为空,但是当push,pop或者poll没有被完全提交的时候队列可能呈现非空(这就是一个错误,因为本来应该是空的)
	 * (isEmpty()方法查看最后一个元素部分移除的情况),正因如此poll操作,单个考虑的话,并不是wait-free(?)的.一个工作窃取者只能在
	 * 其他正在执行中的操作结束之后才能进行.总的来看我们认为在概率上这些操作是非阻塞的.如果工作窃取失败,那么窃取者会随机寻找下一个.
	 * 所以,为了使得一个窃取者能够进行窃取,那么满足任何进行中的poll操作或者在空队列的push操作完成.(这就是为什么我们使用pollAt和它
	 * 的变体,该方法在一个确定的index上尝试一次,否则考虑别的方法,而不是通过多次重试poll)
	 * 这种方式同样支持在用户模式(user mode)下使用poll而不是pop本地任务处理是FIFO顺序,而不是LIFO.这点在message-passing框架中
	 * 非常有用,该框架下,任务不会合并.然而没有模型同时考虑到affinities,loads,cache localities等等因素,所以很少有模型能够在给定
	 * 机制下提供最好的性能,但是可移植地通过平衡上述因素提供了很好地吞吐量.更进一步,尽管我们确实试图使用上述那些信息,我们通常都没有
	 * 运用他们的基础.
	 * WorkQueue也同时以相同的方式用作任务提交.我们不能在相同的队列中混合这些任务,而是,我们随机地关联队列和提交任务的线程(使用一种
	 * hash的形式).ThreadLocalRandom类提供的值作为hash值来选择存在的queue,并且可能随机地给其他任务提交者重新定位queue.本质上,
	 * 任务提交者的行为类似于工作者除了任务提交者被限制在执行他们提交的本地任务.任务的插入在共享模式下需要锁(主要为了保护resizing),
	 * 但我们仅仅使用一个简单的自旋锁(属性qlock),因为任务提交者在遇到一个忙碌的队列的时候会转向尝试或者创建其他队列,阻塞仅仅发生
	 * 在尝试或者创建队列的时候.另外,qlock在shutdown的时候的值会变成一个不可锁定的值(-1).通过对qlock更加廉价的写操作解锁仍然可以进行
	 * (成功的情形下),不成功时会使用CAS.
	 */
    /**
     * ForkJoinPool#WorkQueue
     *
     * ================================  队列数组
     *  poll  <-  base   top  <- push
     *
     * 队列支持"工作窃取"和外部的任务提交.在绝大多数平台上的性能对WorkQueue和其数组的放置非常敏感.我们当然不想多个WorkQueue实例或者
     * 多个数组共享cache line(缓存行).@Contented注释告诉jvm使得WorkQueue实例分开
     *
     * 其主要属性和方法如下:
     * static final int INITIAL_QUEUE_CAPACITY = 1 << 13; 初始队列大小,队列大小必须是2的指数,至少是4但必须
     * 更大来减少或者消除队列间的缓存行共享.目前该值是很大的,部分原因是jvm常常会将数组放置在共享gc 记录(bookkeeping)的地方,
     * 这样的话每次写入操作都会遇到严重的内存争夺的问题.
     *
     * private static final sun.misc.Unsafe U;
     * private static final int  ABASE; ForkJoinTask[]的基本偏移量
     * private static final int  ASHIFT; 数组转化量
     * private static final long QTOP; top属性的offset（偏移量）
     * private static final long QLOCK; qlock属性的偏移量
     * private static final long QCURRENTSTEAL; currentSteal属性的偏移量
     *
     * static final int MAXIMUM_QUEUE_CAPACITY = 1 << 26; 最大的数组大小,64M,必须是2的指数,并且小于等于1<<(31 - 数组宽度)
     *
     * volatile int scanState; 扫描状态,小于0,inactive,奇数,scanning
     *
     * int stackPred; 线程池栈的前驱
     *
     * int nsteals; 窃取数量
     *
     * int hint; 随机和窃取者索引提示
     *
     * int config; 所在线程池的WorkQueue数组的索引(低16位)和模式(高16位)
     *
     * volatile int qlock; 1 锁住的状态 <0 终结状态
     *
     * volatile int base; poll操作的下一个slot的索引位置
     *
     * int top; push操作的下一个slot索引位置
     *
     * ForkJoinTask<?>[] array; WorkQueue维护的数组
     *
     * final ForkJoinPool pool; 线程池的引用
     *
     * final ForkJoinWorkerThread owner; 拥有者线程,或者在共享模式下为null
     *
     * volatile Thread parker; 当调用挂起线程的方法时,等于owner，实际上就是该工作队列的工作者
     *
     * volatile ForkJoinTask<?> currentJoin; 当前正在被合并的任务
     *
     * volatile ForkJoinTask<?> currentSteal; 主要被helpStealer使用
     *
     * WorkQueue(ForkJoinPool pool, ForkJoinWorkerThread owner) 构造器,此方法会把base和top同时设置为队列长度的一半值
     *
     * final int getPoolIndex() 该方法返回可输出的索引,具体为(config & 0xffff) >>> 1
     *
     * final int queueSize() 返回大概的队列中的任务的数量.即进行简单的base - top
     *
     * final boolean isEmpty() 提供更加精确的对队列是否为空的判断，主要是判断队列中是否还存在任务
     * 判断方法如下:
     * (
     * 	(n = base - (s = top)) >= 0 ||
     *     (n == -1 &&
     *     	(
     *     		(a = array) == null ||
     *     		(m = a.length - 1) < 0 ||
     *     		U.getObject(a, (long)((m & (s - 1)) << ASHIFT) + ABASE) == null
     *     	)
     *     )
     * )
     * (m & (s - 1)) << ASHIFT + ABASE （base和top之间）
     *
     * final void push(ForkJoinTask<?> task) 将任务入队，仅仅被拥有者调用（非共享队列）（共享队列参照externalPush）
     * 其核心在于利用CAS将task设置到数组中,并利用CAS增加top的索引值,如果当前的任务数量大于数组长度-1,那么进行扩容操作
     * ？为什么扩容在最后一步进行,数组不会在之前就越界吗
     *
     * final ForkJoinTask<?>[] growArray() 初始化以及加倍数组容量,要么拥有者调用,要么在持有锁的情况下调用,
     * 通过CAS取出原来数组的task,再通过CAS将原来的数组置null,最后CAS将task放入新的数组.
     *
     * final ForkJoinTask<?> pop() 取出任务,以后进先出的方式,仅仅在非共享队列被拥有者调用.
     * 通过CAS将top-1位置处的task取出,然后CAS将相应的位置置为null,最后设置top属性的值.
     *
     * final ForkJoinTask<?> pollAt(int b) 在特定位置取出task,但是这个位置必须和队列的base相等,否则返回空
     * 操作原理同上
     *
     * final ForkJoinTask<?> poll() 取出下一个任务,先进先出顺序
     * CAS取出base处的任务，操作原理同上
     *
     * final ForkJoinTask<?> nextLocalTask() 获取下一任务,获取方式取决于队列模式（共享或者非共享）
     * 返回(config & FIFO_QUEUE) == 0 ? pop() : poll()
     *
     * final ForkJoinTask<?> peek() 根据队列模式返回任务(仅仅返回引用)
     *
     * final boolean tryUnpush(ForkJoinTask<?> t) 将任务弹出队列,只有当任务t是在top位置的任务的时候才可能返回true
     *
     * final void cancelAll() 移除和取消所有已知的任务
     * 主要调用ForkJoinTask.cancelIgnoringExceptions来取消任务
     *
     * final void pollAndExecAll() 将队列中的所有任务取出执行,直到队列为空
     * 主要调用ForkJoinTask#doExec
     *
     * final void execLocalTasks() 移除并执行所有本地任务
     *
     * final void runTask(ForkJoinTask<?> task) 执行给定的任务和任何还在队列中的任务
     * 首先scanState &= ~SCANNING标记为忙碌状态.然后调用task#doExec方法,再CAS将currentSteal置null,如果++nsteals小于0（即溢出）
     * 那么调用transferStealCount进行另外设置
     *
     * final void transferStealCount(ForkJoinPool p) 增加stealCounter
     * 如果nsteals小于0，那么将其设置为Integer.MAX_VALUE
     *
     * final boolean tryRemoveAndExec(ForkJoinTask<?> task) 将task从队列中移除并且执行，仅仅在awaitJoin使用，如果队列为空，返回true
     * 从top到base遍历整个数组，如果在数组中找到task，并且状态检查正确，那么就出队并执行
     *
     * final CountedCompleter<?> popCC(CountedCompleter<?> task, int mode) 将CountedCompleter从队列中移除（是在队首）并返回，仅仅在helpComplete使用
     * 如果队首的元素是task,那么将相应位置置空并且返回该task
     *
     * final int pollAndExecCC(CountedCompleter<?> task) 窃取并运行CountedCompleter类型的任务（队首），返回值会被helpComplete所用
     * 返回：1 表示成功；2 表示可重试；-1 队列不为空，但是没有匹配的任务；否则返回base值
     *
     * final boolean isApparentlyUnblocked() 判断owner线程是否阻塞
     */

     /**
	 * Management:
     * "工作窃取"的主要吞吐优势来自于下放的控制 -- 工作者主要从自己或者互相获取任务,从比率上来说每秒可以超过十亿.
     * 线程池以最小的中心信息来进行自己的创建,活动(能够扫描和运行任务),停止,阻塞以及终止线程.仅仅有一些我们可以
     * 追踪和维护的全局属性,所以我们将其打包在很小数量的变量中,以不阻塞和不锁的形式维护在原子变量中.几乎所有的本质上的
     * 原子控制状态由两个volatile变量持有(这些变量目前为止经常作为检查状态和一致性被读取)(同样的,config变量也持有不变的配置状态)
     *
     * "ctl"属性包含了64位,其原子地决定了添加,停止,入队,出队,重新唤醒工作者(workers).为了让上述成为可能,我们限制最大的
     * 并行等级位(1<<15)-1 (2^15-1)(这已经远远的超过了普通的操作范围),这将允许标志,计数,阈值功能在ctl的子16位中.
     *
     * "runState"属性持有可锁的状态位(STARTED, STOP, etc),该变量同样保护工作者队列(workQueue)数组的更新.当用做锁的时候,
     * 其仅仅为了几条指令而持有(唯一的例外是一次性的数组初始化和不太常见的扩容),所以该变量在最多一次的简单自旋之后常常都是可以访问的.
     * 但需要额外注意的是,自旋之后,awaitRunStateLock方法(仅仅当初始的CAS失败之后才调用),使用了wait/notify机制,这将产生阻塞(
     * 因为其基于内置的monitor).对于高度填充的锁(即锁中包含太多控制信息)来说,这将不是一个好主意,但是在自旋限制之后大多数的线程池
     * 是在没有锁填充的情况下运行,所以以上机制作为更加保守的选择能够很好的运行.因为我们不会采用一个内部的对象作为monitor(监视器)
     * "stealCounter"(AtomicLong类型)会被使用(该变量必须采用lazy init的方式,详见externalSubmit).
     *
     * "runState"和"ctl"只在一个地方相互作用:决定添加工作者线程(详见tryAddWorker),期间持有锁的时候会CAS操作ctl
     *
     * 记录工作队列(WorkQueues).工作队列被记录在workQueues数组中,在第一次被使用的时候数组被创建(详见externalSubmit)并且需要时
     * 扩容.记录新的工作者和删除终止的工作者记录时对数组的更新操作由"runState"锁保护,但是数组可以并发地读和直接访问
     * 我们同样保证读取数组引用的方式绝对不会陈旧.为了简化基于索引(index)的操作,数组的长度常常是2的次方数,并且所有的读取这
     * 必须忍受空值.工作者队列处于计数索引位置,共享(提交)队列处于偶数索引位置(最多64来限制增长).将他们放在一起可以简化并且提升扫描速度.
     * 所有的工作者线程的创建都是在后台,由任务提交,替换终止的工作者和阻塞的工作者的补偿(compensation)触发.然而,所有其他的支持代码
     * 被设定用于以其他策略工作.为了保证我们不会一直持有工作者的引用以阻塞GC,所有访问workQueues的方式是通过workQueues数组的索引.
     * 本质上来说,workQueue数组以弱引用(weak reference)机制进行服务.因此比如说ctl的存储栈顶的子域存储的是索引值而不是引用.
     *
     * 空闲工作者入队.不像HPC工作窃取框架,我们不能在没有任务地时候让工作者无限期地自旋来扫描任务,而且我们不能开始/继续工作者除非有可获取
     * 的任务.另一方面,当新任务被创建或者提交的时候我们必须迅速地启用工作者.多数的使用情况下,启动工作者是总体性能的主要限制因素.
     *
     * "ctl"属性自动地维护了活跃的和总共的工作者数以及一条队列来放置正在等待的线程,所以这些线程能够收到信号的时候被分配.活跃数量也扮演
     * "静止指示器"的角色,当工作者认为没有更多的认为可执行时,该变量递减.队列是一种Treiber栈.在"最近使用顺序"中唤醒线程,栈是一种理想的
     * 选择.这将提高性能和局部性(locality),其大于易于填充和无法释放工作者的弊端(除非在栈顶).当工作者没有找到任务的时候,我们先将其入栈
     * (空闲工作者栈)(ctl字段的低32位),然后挂起工作者.栈顶状态持有工作者的"scanState"字段的值:其索引和状态,加上一个版本.还有count子字段
     * 来提供Treiber栈ABA问题的影响.
     *
	 * scanState属性被工作者和线程池同时使用以管理和追踪是否工作者是INACTIVE(可能是因为等待信号而阻塞),或者扫描任务.当一个工作者是非活跃的
     * 时候,它的scanState被设置,并且被阻止执行任务.需要注意:.当入队的时候,scanState的低16位必须持有其线程池的索引.所以我们在初始化的时候
     * 设置该索引(详见registerWorker),保持其值或者需要的时候恢复.
     *
     * 内存排序(Memory ordering).我们常常需要比最小排序更强壮的排序因为我们有时候必须向工作者发送信号,或者Dekker-like的完全屏障以
     * 避免丢失信号.在没有昂贵的屏障的情况下安排足够多的排序需要在表述访问约束的方法之间折中.最中心的操作--从队列中获取,更新"ctl",需要
     * 完全屏障的CAS.数组元素的读取采用Unsafe提供的volatile的竞争.其他线程对WorkQueue的"base","top",数组的访问需要volatile的加载.
     * 我们使用声明"base"为volatile的规约,并且第一个读取它.拥有者线程必须确保有序的更新,所以写操作采用有序的约束.其他的只被拥有者写的
     * WorkQueue的属性(比如currentSteal)的规约是类似的.
     *
     * 创建工作者.
     * 为了创建工作者,我们预增了总数(作为一种保留),并且试图通过工厂方法去创建ForkJoinWorkerThread.在创建的时候,新的线程会调用registerWorker
     * 方法,该方法会创建WorkQueue并且在WorkQueue的数组中分配一个索引.然后线程被启动起来.上述步骤中的任何异常或者工厂方法返回null,
     * deregisterWorker方法会调整数量并做相应的记录.如果工厂方法返回null,线程池仍然会运行,只不过运行的工作者数量会小于目标数量.如果出现
     * 了异常,异常会被传播出去,一般是传播到外部的调用者.我们简单地把WokrQueue的数组当作是一个长度为2的次方数的hash表,按需扩展.seedIndex
     * 的增长确保没有冲突(resize,注销或替换工作者的时候除外),这样就保持了低冲突的可能性.我们不能在这里使用ThreadLocalRandom.getProbe()
     * 因为线程还没有启动,但是可以在为已存在的线程创建submission queue的时候使用.
     *
     * 停止(deactivate)和等待.入队的时候会遇到一些内在的竞争.需要注意的是:生成任务的线程会丢失对其他放弃搜索任务但还没有加入到等待队列的线程的可见性.
     * 当一个工作者无法找到可以窃取的任务时,它将会停止和入队.十分平常地,因为GC或者OS的调度任务的缺少是瞬时的.为了减少工作者停止的"误报警",
     * 扫描者在清除的时候计算checksums.(此处用到了稳定性检查,别处采用的是快照技术的变体).只有当总数是稳定的时候工作者才会放弃并且试图停止.
     * 更进一步,为了避免丢失的信号,工作者在成功入队之后会重复扫面过程,直到再次稳定.在这种情形下,在工作者从队列被释放之前,工作者不能运行(获取)任务.
     * 所以工作者会最后自己尝试去释放自己或者任何的后继(详见tryRelease).否则,在空扫描的时候,一个不活跃的工作者在阻塞之前会使用一个适应的本地自旋创建
     * (详见awaitWork).注意到围绕在"挂起"和其他阻塞的Thread.interrupts的非常见规约:因为中断仅仅是用作线程检查终止状态,而在阻塞的时候无论如何都会
     * 检查终止状态,在任何调用挂起(park)之前,我们会清除状态(使用Thread.interrupted),这样的话挂起不会立刻返回因为状态会通过其他无关的中断操作而
     * 设置
     *
     * 信号和活跃.工作者只有在至少有一个可执行的任务的情况下才会被创建和活跃.当工作者被入空队列的时候,如果空闲,那么会被唤醒,如果已存在数量小于并行
     * 等级,那么就会创建新的工作者.这些初级的信号被其他线程加固无论什么时候其他线程从队列中移出任务,并且主要到还有其他任务.在大多数的平台上,发出
     * 唤醒信号的时间都是比较长的,并且给线程发出信号到线程真正处理的时间可以非常的长.所以尽可能的从关键的路径中卸载这些延迟是很值得的.并且,因为非活跃
     * 的工作者常常重新扫描或者自旋而不是阻塞,所以我们设置和清除WorkQueues的parker字段来减小不必要的唤醒的调用.(这需要一个次级的重复检查以避免
     * 丢失的信号)
     *
     * 整理(trim)工作者.为了释放资源,线程池休眠时开始等待的工作者将会超时和终结(详见awaitWork)(前提是线程池休眠时间超过IDLE_TIMEOUT),减少线程
     * 的数量,最后移除所有工作者.并且,超过两个的空闲线程存在的时候,多余的线程会在下一个休眠点被立刻终止.
     *
     * 关闭和终结.shutdownNow实际上会调用tryTerminate来自动设置runState位.调用者线程以及其他工作者在这之后进行终结,通过设置他们的qlock状态来
     * 帮助终结其他工作者,取消他们还未处理的任务,唤醒他们,重复执行直到稳定(以一个工作者数量相关的循环的方式).调用非中断的shutdown方法通过检查是否
     * 关闭应该开始来进行关闭.这依靠于"ctl"的表示"活跃数"的位保持一致,只要休眠,tryTerminate就会从awaitWork被调用.
     * 然而,外部的提交者不会参加这个一致行动.所以,tryTerminate清扫队列(直到稳定)来保证正在进行中的提交的任务的缺失和在触发"STOP"关闭阶段之前的
     * 工作者处理这些任务.(注意到:当shutdown可用而helpQuiescePool被调用的时候会存在内部冲突.同时等待休眠,但是tryTerminate倾向于在
     * helpQuiescePool完成后才被触发)
     *
     * Joining Tasks:
     * 当一个工作者等待合并一个被其他工作者窃取或持有的任务,工作者可能采取任何动作.因为我们多路复用许多任务到池中的工作者,我们不能仅仅使得他们阻塞.
     * 我们同样不能仅仅重新分配合并者的运行时栈,之后再替换它,这是一种"连续"的形式,就算该方法可行但也不是一个很好的主意,因为我们可能同时需要一个
     * 非阻塞的任务和其处理的连续.取而代之,我们联合两种策略:
     * 帮助(Helping):如果窃取还没有发生,那么整理合并者来执行一些会运行的任务
     * 补偿(Compensating):除非早已存在足够多的存活线程,tryCompensate()方法可能会创建或者重新创建一个备用的线程来补偿阻塞的合并者,直到他们变为
     * 非阻塞.
     * 第三种形式(在tryRemoveAndExec实现)会帮助一个假想的补偿者:如果我们可以轻易地说出一个补偿者的可能行为是窃取并执行正在合并的任务,那么合并的线程
     * 可以直接这么做,而不需要一个补偿线程(尽管以更大的运行时栈为代价,但是这个折中是值得的).
     *
     * ManagedBlocker不能用来帮助,所以只能依赖于awaitBlocker方法的补偿.
     *
     * helpStealer中的算法需要一种线性帮助的形式.每个工作者记录(在currentSteal属性中)最近从其他工作者窃取的任务(或者是提交).工作者同样记录(在currentJoin
     * 属性中)最近活跃地合并的任务.helpStealer方法使用这些标志来试图发现一个工作者以帮助其加速活跃的已合并的任务的完成(比如窃取回任务并执行).
     * 因此,合并者执行一个会在其自己本地双端队列(该队列有还未被窃取且等待合并的任务)的任务.这是某种实现的保守变体.其不同点在于:
     * 1.我们仅仅维护窃取之上的工作者的依赖链接,而不是使用依照任务记录.这有时需要workQueues数组的线性扫描来定位窃取者,但大多数情况不用,因为窃取者会留下定位他们
     * 的线索.这只是个暗示因为一个工作者可能会有多个窃取而暗示仅仅记录其中一个(常常是最近的那个).
     * 2.这是"浅"的,忽略了嵌套的和潜在循环相互窃取.
     * 3.这是有意奔放的:只有当活跃的合并时,currentJoin属性被更新,这意味着在长时间存活的任务,GC间隙时我们会丢失链接
     * 4.我们使用checksums限制尝试的次数来发现任务并且后退来挂起工作者,如果可能的话,以其他的替换.
     *
     * CountedCompleters的帮助动作不需要追踪currentJoins:helpComplete方法以相同的root获取并执行任何等待的任务.然而,这仍然需要一些completer chains的遍历,
     * 这样的话就没有非显式合并的情况下使用CountedCompleters高效.
     *
     * 补偿并不是为了完全地保持非阻塞线程的目标并行数.该类的一些过去版本对任何阻塞的合并直接采取补偿.然而,实践中,大多数的阻塞是GC的瞬时副产品和其他的JVM或者OS的活动,
     * 而这些是有价值的.当前,只有在验证所有活跃线程正在通过检查WorkQueue.scanState属性处理任务之后才会尝试补偿.补偿也会在最常见的情况下被绕开(这些情况下补偿不太有益):
     * 当带有空队列的工作者(也就是没有持续任务)在合并中阻塞并且还有足够多的线程保证活跃.
     *
     * 补偿机制可能会有界限.commonPool的界限(详见commonMaxSpares)最好能够使得JVM能够在资源耗尽之前对抗编程错误和滥用.其他情况下,用户可能会提供工厂类来限制线程创建.
     * 该线程池中界限的影响是不精确的.当线程注销的时候总工作者数量会下降,而并不是当线程退出和资源被JVM和OS回收的时候.所以同时存活的线程数会瞬时地大于界限.
     *
     * Common Pool:
	 */

    /**
     * ForkJoinPool的属性与方法如下:
     *
     * 基本模型
     * WorkQueue[] ->
     * ()===========() WorkQueue (ForkJoinTask[])  <-  ForkJoinWorkerThread
     * ()===========() WorkQueue (ForkJoinTask[])  <-  ForkJoinWorkerThread
     * ()===========() WorkQueue (ForkJoinTask[])  <-  ForkJoinWorkerThread
     * ()===========() WorkQueue (ForkJoinTask[])  <-  ForkJoinWorkerThread
     *
     * CAS机制
     * private static final sun.misc.Unsafe U;
     * private static final int  ABASE; ForkJoinTask[]的基本偏移量
     * private static final int  ASHIFT;
     * private static final long CTL; ctl的偏移量
     * private static final long RUNSTATE; runState
     * private static final long STEALCOUNTER; stealCounter
     * private static final long PARKBLOCKER; parkBlocker
     * private static final long QTOP; top
     * private static final long QLOCK; qlock
     * private static final long QSCANSTATE; scanState
     * private static final long QPARKER; parker
     * private static final long QCURRENTSTEAL; currentSteal
     * private static final long QCURRENTJOIN; currentJoin
     *
     * 边界
     * static final int SMASK        = 0xffff; 最大索引值，也就是WorkQueue[]长度
     * static final int MAX_CAP      = 0x7fff; 最大工作者数量-1 max capacity
     * static final int EVENMASK     = 0xfffe;
     * static final int SQMASK       = 0x007e;
     *
     * 队列的scanState和ctl
     * static final int SCANNING     = 1; 用于scanSate，
     * static final int INACTIVE     = 1 << 31; 必须为负数
     * static final int SS_SEQ       = 1 << 16; 用于ctl的版本计数，如果ctl的版本增加，那么加上这个变量
     *
     * 队列模式
     * static final int MODE_MASK    = 0xffff << 16;
     * static final int LIFO_QUEUE   = 0;
     * static final int FIFO_QUEUE   = 1 << 16;
     * static final int SHARED_QUEUE = 1 << 31; 必须为负数
     *
     * public static final ForkJoinWorkerThreadFactory defaultForkJoinWorkerThreadFactory; 用于创建工作者线程
     *
     * private static final RuntimePermission modifyThreadPermission; 启动或终结线程的权限
     *
     * static final ForkJoinPool common; common pool 在ForkJoinPool的构造器中会被构造出来
     *
     * static final int commonParallelism; common pool的并行等级, 可为0，但是实际是1
     *
     * private static int commonMaxSpares; tryCompensate中对空闲线程创建的限制
     *
     * private static int poolNumberSequence; 创建workerNamePrefix的序列号
     *
     * private static final synchronized int nextPoolId() 简单地将poolNumberSequence加1
     *
     * private static final long IDLE_TIMEOUT = 2000L * 1000L * 1000L; 空闲线程超时时间(nanoseconds)
     * 在超时的时候，线程会尝试收缩工作者数量
     *
     * private static final long TIMEOUT_SLOP = 20L * 1000L * 1000L;
     *
     * private static final int DEFAULT_COMMON_MAX_SPARES = 256; commonMaxSpares的默认值
     *
     * private static final int SPINS  = 0; 在阻塞之前的自旋等待次数。spins（awaitRunStateLock和awaitWork）目前使用
     * 随机化的spins，目前设置为0以减少CPU的使用。如果比0大的话，SPINS的值必须是2的次方数，最少是4。
     *
     * private static final int SEED_INCREMENT = 0x9e3779b9;
     *
     * ctl的低32位和高32位的掩码
     * private static final long SP_MASK    = 0xffffffffL;
     * private static final long UC_MASK    = ~SP_MASK;
     *
     * 活跃数 active count
     * private static final int  AC_SHIFT   = 48;
     * private static final long AC_UNIT    = 0x0001L << AC_SHIFT; 一个单位，在活跃数数需要增加1的时候加上该量
     * private static final long AC_MASK    = 0xffffL << AC_SHIFT;
     *
     * 总数 total count
     * private static final int  TC_SHIFT   = 32;
     * private static final long TC_UNIT    = 0x0001L << TC_SHIFT; 一个单位，在总数需要增加1的时候加上该量
     * private static final long TC_MASK    = 0xffffL << TC_SHIFT;
     * private static final long ADD_WORKER = 0x0001L << (TC_SHIFT + 15); // sign ctl的第48位，如果这一位为1，那么
     * TC就为负，也就表示可以添加工作者(或者说TC不足)
     *
     * runState位，SHUTDOWN必须为负数（即runState为负），其余是2的次方数
     * private static final int  RSLOCK     = 1;
     * private static final int  RSIGNAL    = 1 << 1;
     * private static final int  STARTED    = 1 << 2;
     * private static final int  STOP       = 1 << 29;
     * private static final int  TERMINATED = 1 << 30;
     * private static final int  SHUTDOWN   = 1 << 31;
     *
     * 实例属性
     * volatile long ctl;                   控制量：活跃数，工作者总数，
     *  16：AC(number of active，活跃数量-目标并行) 16：TC(number of total，工作者总数-目标并行) 16：SS(版本计数和顶部等待线程的状态) 16:ID(WorkQueue数组索引最大值，即栈顶)
     *  AC为负，那么没有足够的活跃工作者，TC为负，那么没有足够的总共的工作者，SP -> SS ID
     *
     * volatile int runState;               状态
     * 1：SHUTDOWN 1：TERMINATED 1：STOP （26：customized） 1：STARTED 1：RSIGNAL 1：RSLOCK
     *
     * final int config;                    并行，模式
     * 16：MODE 16：parallelism
     *
     * int indexSeed;                       用于产生工作者索引（所在数组位置）
     * volatile WorkQueue[] workQueues;     工作队列
     * final ForkJoinWorkerThreadFactory factory;
     * final UncaughtExceptionHandler ueh;
     * final String workerNamePrefix;       用于创建工作者名字
     * volatile AtomicLong stealCounter;    用于同步控制，即用作锁
     *
     * private int lockRunState() 获取runState的锁，返回当前的runState,
     * 如果当前的runState不为0或者CAS判断runState失败，那么调用awaitRunStateLock等待，否则返回该值
     *
     * private int awaitRunStateLock() 自旋或者阻塞直到runState的锁可用
     * 不断循环，期间：
     * 如果runState的lock位为0（即runState&RSLOCK==0）那么继续判断，如果CAS设置runState为RSLOCK状态成功，在没有中断的情况下
     * 直接返回runState的值
     * 如果r（初始值为0）等于0，那么通过ThreadLocalRandom随机分配一个值
     * 如果spins（初始值为0）大于0，那么对r进行一系列操作（实际效果就是xor？未验证），如果r大于等于0，那么对spins减1
     * 如果runState的started位为0（即runState&STARTED==0）或者stealCounter为空，那么线程让步（yield），以避免初始化的竞争
     * 如果CAS设置runState为RSIGNAL状态成功（即需要信号唤醒或者唤醒其他线程），那么锁住stealCounter，如果runState的RSIGNAL位不为0，
     * 那么调用stealCounter的wait方法以阻塞，否则调用stealCounter的notifyAll方法唤醒其他等待的线程
     *
     * private void unlockRunState(int oldRunState, int newRunState) 释放runState并且为其设置新的值
     * 如果CAS操作oldRunState和newRunState失败，那么将runState设置为newRunState并且调用stealCounter的notifyAll方法
     *
     * private boolean createWorker() 尝试创建和启动一个工作者，假设总数已经作为保留被增加，失败的情况的下调用deregisterWorker
     * 首先使用ForkJoinWorkerThreadFactory创建工作者线程ForkJoinWorkerThread，然后调用其启动方法（start），如果有任何异常，
     * 即调用deregisterWorker移除工作者线程（实际上在new一个orkJoinWorkerThread实例的时候会调用registerWorker以注册到线程池中）
     *
     * private void tryAddWorker(long c) 尝试添加一个工作者，在此之前增加ctl计数，
     * c表示ctl值，表示总数和活跃数，在CAS失败后，c被刷新为目前的ctl并且重试
     * 如果ctl&ADD_WORKER ！= 0（表示可以添加工作者） 并且(int)c == 0（表示c的低32位都为0） 那么不断循环，期间：
     * 首先创建nc = ((AC_MASK & (c + AC_UNIT)) | (TC_MASK & (c + TC_UNIT))) (表示总工作者数和活跃工作者数同时加1)
     * 如果c==ctl，那么首先检查线程池是否停止（要先获取runState锁），如果停止，那么直接跳出，否则CAS将nc设置到ctl中，如果
     * 成功的话那么调用createWorker创建工作者，然后跳出（在此之前会释放runState锁）
     *
     * final WorkQueue registerWorker(ForkJoinWorkerThread wt) ForkJoinWorkerThread的构造器的回调函数以建立和记录工作者线程的队列
     * 首先将wt设置为守护线程，然后设置UncaughtExceptionHandler
     * 接着创建新的WorkQueue实例，然后获取runState的锁
     * 如果workQueues（WorkQueue[]）不为空并且该数组长度大于0，那么：
     * indexSeed += SEED_INCREMENT，再创建一个奇数的索引i=((indexSeed << 1) | 1) & （数组长度（n）-1）
     * 如果workQueues在相应的索引位置上有实例，那么在下移到该索引+n/2的位置，如果这个位置比数组长度大，那么数组双倍扩容
     * 最后设置WorkQueue的相关状态量，并把数组的相应位置指向该实例（还需要释放runState锁）
     *
     * final void deregisterWorker(ForkJoinWorkerThread wt, Throwable ex) 终结工作者和创建，启动工作者失败的回调函数。从数组中移除工作者的
     * 记录，并调整计数，如果线程池关闭，那么尝试终结。
     * 首先如果ForkJoinWorkerThread不为空并且其WorkQueue不为空，那么首先获取runState锁然后把WorkQueue数组的相应位置的置为空，最后释放锁
     * 然后CAS将ctl重置为((AC_MASK & (c - AC_UNIT)) | (TC_MASK & (c - TC_UNIT)) | (SP_MASK & c))))，即减小活跃数和工作者总数
     * 将ForkJoinWorkerThread的WorkQueue进行相关的设置，包括qlock，并且取消掉里面的所有任务（WorkQueue#cancelAll）
     * 不断循环，期间：
     *  如果检查到线程池已经被终结掉，那么直接跳出
     *  如果(int)ctl!=0，那么tryRelease（尝试唤醒），然后跳出
     *  如果ex不为空并且可以添加工作者，那么tryAddWorker，然后跳出
     * 最后如果ex不为空，那么重新抛出该异常（ForkJoinTask#rethrow）
     *
     * final void signalWork(WorkQueue[] ws, WorkQueue q) 如果太少的工作者是活跃的，尝试创建或者激活一个工作者
     * 当ctl<0的，那么
     *  如果没有空闲工作者并且可以添加工作者，那么tryAddWorker添加工作者并跳出
     *  如果ws为空，那么直接跳出
     *  如果线程池已经终结(WorkQueue数组的长度小于等于活跃数)，那么直接跳出
     *  如果线程池正在被终结(数组的活跃数索引处为空)，同样直接跳出
     *  创建新的ctl，具体为(UC_MASK & (ctl + AC_UNIT)) | (SP_MASK & v.stackPred) 表示活跃数增加1，
     *  如果WorkQueue(栈顶处)的scanState等于ctl的低32位并且CAS更新ctl成功，那么将新的scanState(版本+1)赋值，并且如果WorkQueue的parker
     *  不为空，那么调用Unsafe将其唤醒，最后跳出
     *  如果q不为空且其base==top，那么直接跳出
     *
     * private boolean tryRelease(long c, WorkQueue v, long inc) 发出信号并且释放工作者(如果其在空闲工作者栈的顶部)
     * c：传入的ctl值 inc：active count的增量 成功返回true
     * 如果v不为空并且v的scanState和ctl的低16位相等，那么
     *  首先创建新的ctl，具体为AC增加c，ctl的低32位变为v#stackPred，如果CAS更新ctl成功，那么更新v的scanState(增加版本号)，
     *  如果v的parker不为空，那么唤醒parker，返回true
     * 否则返回false
     *
     * final void runWorker(WorkQueue w) 该方法被ForkJoinWorkerThread.run调用，
     * 首先增加w的容量（WorkQueue#growArray）
     * 然后不断循环，期间：
     *  如果调用scan的返回值（ForkJoinTask）不为空，那么调用WorkQueue#runTask执行任务（扫描任务，扫描的过程是随机的）
     *  否则如果awaitWork为false，直接跳出
     *
     * private ForkJoinTask<?> scan(WorkQueue w, int r) 扫描并且尝试窃取顶端的任务。扫描从一个随机位置开始（具体指的是WorkQueue[]的随机位置），
     * 随机地移动到一个明显的竞争位置，否则继续线性地移动直到到达2个连续的空队列，以相同的校验和（每个队列的base值求和），在该点上工作者尝试停止并重新
     * 扫描，如果发现一个任务就重新活跃（自己或者其他工作者）；否则返回空来等待任务。该方法会使用尽量少的内存以减少其他扫描线程的影响
     * w：工作者，r：扫描的随机数
     * 不断循环，期间：
     *  如果在r位置处的WorkQueue不为空，CAS获取数组顶部的任务成功并且CAS将相应位置置空成功，那么返回任务并跳出
     *  如果上述CAS操作不成功（即发生了竞争），那么将r赋值为另一个随机数，然后继续循环
     *  如果r位置处的WorkQueue为空，那么就到其下一个位置，然后继续循环
     *  如果到了WorkQueue数组末尾，WorkQueue都为空，如果工作者（同过w）是不活跃状态，那么直接跳出，否则将其设置为不活跃状态，并CAS设置新的ctl，在
     *  第二轮循环的时候跳出，
     * 跳出循环，那么就返回null值
     *
     * private boolean awaitWork(WorkQueue w, int r) 也许会阻塞工作者以等待窃取的任务，或者返回false因为工作者应该被终结，如果
     * 正在停止的工作者引起线程池休眠，那么检查线程池是否终结，并且只要这不是仅有的工作者，就在一段时间内等起它启动。超时的时候，如果ctl
     * 没有被改变，那么终结掉工作者，进而唤醒其他工作者以重复相同的过程
     * w：工作者，r：随机数（用于自旋），如果工作者需要被终结，那么返回空
     * 如果w为空或者其状态为终结状态，那么直接返回false
     *
     *
     *
     *
     * //合并任务
     *
     * final int helpComplete(WorkQueue w, CountedCompleter<?> task, int maxTasks) 尝试窃取和运行任务，使用"顶层"算法的变体，将任务
     * 限制为祖先任务：该算法倾向于提取和运行从工作者自己的队列中的任务(通过popCC)。否则就扫描其他工作者的队列，随机地移动到竞争或者执行，基于
     * 校验和决定放弃。参数maxTasks支持外部使用；内部的使用则为0。
     *
     *
     * private void helpStealer(WorkQueue w, ForkJoinTask<?> task) 尝试为窃取者分配和执行给定任务，
     *
     * private boolean tryCompensate(WorkQueue w) 尝试减少活跃数量（有时是隐式的）并且可能地释放或者创建一个补偿地工作者以备阻塞的时候使用。
     * 在竞争，无法窃取，不稳定或者终结的时候返回false。
     *
     * final int awaitJoin(WorkQueue w, ForkJoinTask<?> task, long deadline) 帮助或者阻塞直到给定的任务完成或者超时
     * 返回值为任务的状态
     *
     * private WorkQueue findNonEmptyStealQueue() 返回一个非空的窃取队列，
     *
     * final void helpQuiescePool(WorkQueue w)
     *
     * final ForkJoinTask<?> nextTaskFor(WorkQueue w)
     *
     * static int getSurplusQueuedTaskCount()
     *
     * private boolean tryTerminate(boolean now, boolean enable)
     *
     * private void externalSubmit(ForkJoinTask<?> task)
     *
     * final void externalPush(ForkJoinTask<?> task)
     *
     * static WorkQueue commonSubmitterQueue()
     *
     * final boolean tryExternalUnpush(ForkJoinTask<?> task)
     *
     * final int externalHelpComplete(CountedCompleter<?> task, int maxTasks)
     *
     * public <T> T invoke(ForkJoinTask<T> task)
     *
     * public void execute(ForkJoinTask<?> task)
     *
     * public void execute(Runnable task)
     *
     * public <T> ForkJoinTask<T> submit(ForkJoinTask<T> task)
     *
     * public <T> ForkJoinTask<T> submit(Callable<T> task)
     *
     * public <T> ForkJoinTask<T> submit(Runnable task, T result)
     *
     * public ForkJoinTask<?> submit(Runnable task)
     *
     * public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks)
     *
     * public int getParallelism()
     *
     * public int getPoolSize()
     *
     * public boolean getAsyncMode()
     *
     * public int getRunningThreadCount()
     *
     * public int getActiveThreadCount()
     *
     * public boolean isQuiescent()
     *
     * public long getStealCount()
     *
     * public long getQueuedTaskCount()
     *
     * public int getQueuedSubmissionCount()
     *
     * public boolean hasQueuedSubmissions()
     *
     * protected ForkJoinTask<?> pollSubmission()
     *
     * protected int drainTasksTo(Collection<? super ForkJoinTask<?>> c)
     *
     * public void shutdown()
     *
     * public List<Runnable> shutdownNow()
     *
     * public boolean isTerminated()
     *
     * public boolean isTerminating()
     *
     * public boolean isShutdown()
     *
     * public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException
     *
     * public boolean awaitQuiescence(long timeout, TimeUnit unit)
     *
     * static void quiesceCommonPool()
     *
     *
     *
     */

}
