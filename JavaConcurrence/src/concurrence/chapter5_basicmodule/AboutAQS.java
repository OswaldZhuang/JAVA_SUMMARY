package concurrence.chapter5_basicmodule;

/*
 * java.util.concurrent.locks.AbstractQueuedSychronizer
 * 同步器
 */
public class AboutAQS {
    
    /*
     * 抽象同步器(AQS)是大多数同步组件的实现基础(java.util.concurrent.Semaphore,
     * java.util.concurrent.CountDownLatch,
     * java.util.concurrent.locks.ReentrantLock 
     * java.util.concurrent.locks.ReadWriteLock...)
     * 该抽象同步器用作单一状态的同步
     * AQS维护两种不同的模式(排他(exclusive)模式(也就是独占模式),共享(shared)模式)
     * AQS的实现一般用作内部辅助类(helper class),
     * AQS内部的线程(无论是什么模式)都共享同一个(先进先出)队列,一般来说AQS的子类都只实现一种模式
     * (也有两种模式都实现的,比如说ReadWriteLock)
     * AQS定义了还定义了内部类ConditionObject(该类是java.util.concurrent.locks.Condition
     * 的具体实现),该类用于支持独占模式
     * 以下的方法需要子类去重写或者实现:
     *  #tryAcquire 以独占方式获取锁
     *  #tryRelease 
     *  #tryAcquireShared 以共享方式获取锁
     *  #tryReleaseShared
     *  #isHeldExclusively
     *  在实现上述方法的时候需要通过改变或者查看状态量完成(#getState,#setState,#compareAndSetState)
     *  这些方法的实现也必须是内部线程安全,非阻塞的
     *  
     * AQS的继承的类java.util.concurrent.locks.AbstractOwnableSynchronizer
     * 用于监控拥有独占的同步器的线程
     */
    
    /*
     * AbstractQueuedSychronizer维护的数据结构是一个队列(实际上该队列的数据结构是链表,
     * 并且链表的节点既有前驱也有后继)(CLH lock,是一种公平的自旋锁,其中的节点不断轮询前驱节点的状态
     * 实际上该队列并没有采用这种锁,只是使用了类似的策略,在AQS中,锁不再是自旋锁,而是阻塞锁)
     * 链表的节点被定义为Node,其中存储着一些重要的变量,其中:
     * static final Node SHARED = new Node();定义Node为SHARED模式
     * static final Node EXCLUSIVE = null;定义Node为EXCLUSIVE模式
     * waitStatus:表示Node中的线程是否应该被阻塞,当Node的前驱释放之后,Node就会被给信号
     * 其中有
     *     CANCELLED =  1 表示线程被取消了
     *     SIGNAL    = -1 表示后继线程被阻塞,在等执行释放锁的信号
     *     CONDITION = -2 表示线程在Condition上的等待,即在condition队列中
     *     PROPAGATE = -3 表示传播共享锁,当前场景下的acquireShared能够得以执行
     * volatile Node prev 指向前驱节点,当前依赖于前驱节点的waitStatus
     * volatile Node next 指向后继节点,被取消的节点的next会指向自己
     * Node nextWaiter 指向下一个在Condition(条件队列)上等待的Node或者是特殊值SHARED,nextWaiter也用来存储当前
     * node是处于什么模式下的
     * 
     * 在Condition上等待的线程也是用的Node节点
     *
     */
    
    /* AQS中的属性和方法:
     * 
     * private transient volatile Node head 队列头部,采用懒加载,仅当setHead方法调用时才会
     * 设置它,如果head存在,那么它的waitStatus就一定不是CANCELLED,采用volatile修饰,保证内存的可见性,在
     * 其他线程对其进行更改之后,在其他线程能够立刻可见其状态,下同
     * private transient volatile Node tail 队列尾部,采用懒加载,仅当调用enq方法的时候会对其
     * 进行更新
     * private volatile int state 同步状态,如果为0,则说明该锁没有被线程占有
     * static final long spinForTimeoutThreshold = 1000L 自旋的超时阈值
     * private Node enq(final Node node) 
     * 节点入队:具体操作为,不断循环(for(;;),这样做是为了不断尝试直到CAS操作完成),在此期间采用CAS将Node设置为队尾
     * 返回node的前驱
     * 对于队列初始化的情况会比较特殊,大致说来:头结点会设置成为new Node(),再将参数中传进来的node追加在head后面
     * 
     * private Node addWaiter(Node mode) 
     * 在队尾加入等待节点(线程),传入的参数为等待的模式,Node.EXCLUSIVE或者Node.SHARED,返回新的节点
     * 首先尝试采用CAS操作来将节点(节点中的线程为当前线程)设置到队尾,如果失败的话(或者队列还没有初始化),就采用enq方法
     *   
     * private void unparkSuccessor(Node node)
     * 唤醒Node后继
     * 首先查看Node的等待状态,如果小于0(则说明该线程没有被取消),那么CAS将其设置为0
     * 随后,查看后继是否为空或者状态是否大于0,如果是,那么从后向前遍历队列,直到找到状态小于0的节点
     * 最后,唤醒该节点
     * 
     * private void cancelAcquire(Node node)
     * 取消当前正在获取锁的操作
     * 首先将node中的线程赋值为空,然后找到状态不为CANCLLED的前驱节点pred,再获得pred的next节点predNext
     * 将node的等待状态设置为CANCELLED
     * 如果node节点是tail,那么将pred设置为尾节点,并把predNext设置为空
     * 否则如果pred不为头结点且其状态为SIGNAL,则将predNext设置为node的后继(next),否则唤醒node的后继
     * 实际上以上操作的本质就是将node节点的等待状态设置为CANCELLED,并且跳过node,将node之前和之后的非CANCELLED
     * 的节点连接起来
     * 
     * private static boolean shouldParkAfterFailedAcquire(Node pred, Node node)
     * 判断在获取锁失败之后是否需要阻塞线程
     * 首先获取前驱节点的状态,如果其状态为SIGNAL,则表示当前节点需要被阻塞,那么返回true
     * 如果状态大于0,那么向前遍历跳过状态为CACELLED的节点,把node和那个节点(状态不为CANCELLED)连接起来,返回false
     * 如果状态为0或者PROPAGATE,则CAS将前驱节点状态设置为SIGNAL,返回false
     * 
     * final boolean transferForSignal(Node node) 将node从条件队列转移到同步队列
     * 首先CAS设置node的等待状态从CONDITION到0,如果失败,返回false,否则将node加入到队列中(enq方法),再获取node前驱的状态,
     * 如果其大于0(即CANCELLED)或者CAS设置为SIGNAL失败,那么唤醒node中的线程,最后返回true
     * 
     * 
     * ----------------------共享模式SHARED------------------------------------------------------
     * 
     * public final void acquireShared(int arg)
     * 如果tryAcquireShared(arg) < 0 那么 doAcquireShared(arg)
     * protected int tryAcquireShared(int arg) 该方法需要子类实现
     * 表示在共享模式下获取锁,
     * 返回值小于0,表示获取失败,等于0表示获取锁的操作成功但是随后的共享模式下获取锁的操作无法成功
     * 大于0表示以上二者都能够成功
     * private void doAcquireShared(int arg)
     * 共享非中断模式下的获取锁操作
     * 首先将当前线程的节点node加入到队列的队尾(addWaiter(Node.SHARED)),设置状态量failed为true,interrupted为false
     * 不断循环,在此期间获取node的前驱p,如果p是head,那么调用tryAcquireShared(arg),如果其返回值r大于等于0,则setHeadAndPropagate(node, r)
     * failed设置为false,返回,
     * 如果shouldParkAfterFailedAcquire(p, node) && parkAndCheckInterrupt()成立的话,线程就会被被阻塞(被parkAndCheckInterrupt
     * 阻塞),如果shouldParkAfterFailedAcquire不成立,那么跳过parkAndCheckInterrupt,设置interrupted为true,继续循环
     * 最后,根据failed,调用cancelAcquire(node)
     * 
     * public final boolean releaseShared(int arg)
     * 如果tryReleaseShared(arg)为真,那么doReleaseShared(),返回true
     * protected boolean tryReleaseShared(int arg) 该方法需要子类实现
     * private void doReleaseShared()
     * 共享模式下的释放操作
     * 不断循环,在此期间,获得头结点,查看头结点的等待状态,如果等待状态为SIGNAL,那么CAS将其设置为0(如果失败,那么继续循环),并且唤醒
     * 后继节点,如果等待状态为0,并且CAS设置等待状态为PROPAGATE失败,那么继续循环(为了CAS操作成功),如果等待状态为0,并且CAS设置等待状态为PROPAGATE成功
     * 那么跳出循环
     * 即该方法要么唤醒头结点的后继节点(头结点状态为SIGNAL),要么设置头结点的状态为PROPAGATE
     * 
     * private void setHeadAndPropagate(Node node, int propagate)
     * node表示要设置为head的节点
     * propagate是tryAcquireShared的返回值
     * 首先将node设置为头结点
     * 当 propagate大于0 或者 原来的头结点为空 或者 原来头结点的等待状态小于0 
     * 则获取当前头节点的下一个节点,如果下一个节点为空或者是共享模式则doReleaseShared()
     * 这一步是使得锁释放能够传播的关键,只要调用doReleaseShared,那么后继节点就会被唤醒,而后继节点一般是挂起并处于doAcquireShared中
     * 而doAcquireShared中又会调用setHeadAndPropagate,这样就相当于递归的将释放操作传递下去
     * 
     * public final void acquireSharedInterruptibly(int arg)
     * private void doAcquireSharedInterruptibly(int arg)
     * 
     * public final boolean tryAcquireSharedNanos(int arg, long nanosTimeout)
     * private boolean doAcquireSharedNanos(int arg, long nanosTimeout)
     * 
     * ----------------------独占模式EXCLUSIVE------------------------------------------------------
     * 
     * public final void acquire(int arg)
     * 该方法会调用!tryAcquire(arg) && acquireQueued(addWaiter(Node.EXCLUSIVE), arg),如果上述条件不成立
     * 那么就会将自己中断
     * protected boolean tryAcquire(int arg) 该方法需要子类实现
     * 
     * 
     * public final boolean release(int arg)
     * 如果tryRelease(arg)成功,且头结点不为空,等待状态不为0,则调用unparkSuccessor(h),h为头结点
     * protected boolean tryRelease(int arg) 该方法需要子类实现
     * 
     * 
     * final boolean acquireQueued(final Node node, int arg)
     * 独占非中断模式下的对队列里的线程的获取锁的操作
     * 如果等待过程中被中断,则返回true
     * 程序中设置2个boolean类型的状态量failed = true,interrupted = false
     * 不断循环,在此期间,首先得到node的前驱p,如果p为头结点并且tryAcquire成功的话(这也是实现非公平锁和公平锁的一个非常重要的地方),
     * 把node设为头结点,failed设置为false,返回interrupted(false)
     * 如果shouldParkAfterFailedAcquire(p, node) && parkAndCheckInterrupt()成立的话那么意味着线程会被阻塞在这里(
     * parkAndCheckInterrupt阻塞当前线程),当线程被唤醒后,实际上会设置interrupted,然后继续循环,也就是说,即便线程被唤醒了也不一定
     * 能够得到锁
     * 如果shouldParkAfterFailedAcquire不成立的话,后面的阻塞操作就不会执行,循环就会继续
     * 最后,根据failed,调用cancelAcquire(node)
     * 这是个自旋获取锁的过程,线程的状态要么是不断循环轮询,要么是被阻塞
     * 
     * public final void acquireInterruptibly(int arg)
     * private void doAcquireInterruptibly(int arg)
     * 独占可中断模式下的获取锁操作
     * 如果当前线程是中断的,那么抛出InterruptedException
     * 如果tryAcquire失败,那么:
     * 操作类似于acquireQueued,不同的在于如果shouldParkAfterFailedAcquire(p, node) && parkAndCheckInterrupt()成立的话那么
     * 抛出InterruptedException
     * 
     * public final boolean tryAcquireNanos(int arg, long nanosTimeout)
     * private boolean doAcquireNanos(int arg, long nanosTimeout)
     * 
     * protected boolean isHeldExclusively() 该方法需要子类实现
     * 
     */
    
    /*
     * AQS中的另一个内部类是ConditionObject,它实现了Condition接口
     * java.util.concurrent.locks.Condition实际上就是一种条件队列(类比于Object的wait,signal等方法)
     * 内部的属性有:
     * private transient Node firstWaiter
     * private transient Node lastWaiter
     * 
     * private Node addConditionWaiter()
     * 将等待者加入条件队列,返回值是当前线程的node
     * 
     * private void unlinkCancelledWaiters()
     * 将条件队列中CANCELLED状态的的节点去除
     * 
     * public final void signal()
     * private void doSignal(Node first)
     * 
     * public final void signalAll()
     * private void doSignalAll(Node first)
     * 该方法实际上是循环调用doSignal达到释放所有等待线程的目的
     * 
     * public final void awaitUninterruptibly()
     * 
     * public final void await() throws InterruptedException
     * 
     * public final long awaitNanos(long nanosTimeout) throws InterruptedException
     * 
     * public final boolean awaitUntil(Date deadline) throws InterruptedException
     * 
     * public final boolean await(long time, TimeUnit unit) throws InterruptedException
     * 
     * 
     */

}
