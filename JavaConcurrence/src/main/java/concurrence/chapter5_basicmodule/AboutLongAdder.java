package concurrence.chapter5_basicmodule;

public class AboutLongAdder {
    /**
     * java.util.concurrent.atomic.LongAdder
     *
     * 一个或多个值一起维护初始值为0的long类型的和。
     * 当更新方法（add）在线程之间竞争的时候，变量可能会自动增加来减小竞争。
     * 方法sum（或者longValue）返回目前的总值。
     * 当多线程更新一个用于搜集统计数据（比如计数）的总值的时候该类常常比AtomicLong更好
     * 高并发下该类更具有优势。
     *
     * LongAdder可以和ConcurrentHashMap一起使用，用以维护可扩展的频率图
     *
     * public void add(long x)
     * 如果cells不为空或者cas相加失败，那么尝试相加cell中的值，如果还是失败（即有竞争），那么调用longAccumulate
     *
     * public long sum()
     * 遍历cells，然后相加
     */

    /**
     * java.util.concurrent.atomic.Striped64
     * 该类维护自动更新变量的懒加载的table，加上额外的"base"属性。table大小是2的次方数。
     * 索引采用基于线程的hash值的计算结果。几乎所有的属性和方法都是protected。
     * table的入口是Cell，是AtomicLong填充的变体，用于减小缓存竞争。填充对于大多数原子类
     * 来说都是适得其反的因为他们常常不规整地分布在内存中，因此不会彼此干涉太严重。但是分布在
     * 数组中的相邻于彼此的原子类倾向于被替换，所以常常会在没有填充的情况下共享缓存行。
     * 因为Cell相对来说较大，所以需要的时候才创建。当没有竞争的时候，所有的更新是在base上。
     * 当第一个竞争时（cas base时失败），table被初始化为长度2。在更远的竞争时，table的长度加倍
     * 直到大于等于cpu的核数。table里的值在需要之前为空。
     * 单自旋锁（cellsBusy）被用作初始化，调整table和在槽位产生新的Cell。没有必要使用阻塞锁，当锁不可用时，
     * 线程尝试其他槽位（或者base），当重试的时候，竞争增加，locality减小。
     * 线程探针（thread probe）属性被ThreadLocalRandom维护，为基于线程的hash。在0槽位竞争之前，这些值
     * 为0。初始化的时候的值不会和其他值冲突。当任何更新时，竞争或者table冲突会由失败的cas表明。产生冲突
     * 时，如果table大小比容量小，除非其他线程持有锁，否则table大小加倍。如果hash得到的槽位值为空，锁可用，
     * 新的Cell会被创建。否则，如果槽位存在，会使用cas，重试采用双hash，用二级hash来尝试发现空余槽位。
     * table的大小是会封顶的，因为当线程数大于cpu数的时候，假设每个线程绑定一个cpu（核），那么存在一个完美的hash
     * 用于将线程映射到槽位上以消除冲突。当我们达到容量时，我们通过随机的变化冲突线程的hash来寻找上述映射。
     * 因为搜索是随机的，冲突只会在cas失败的时候被知晓，收敛会变慢，因为线程不会永久的绑定在某个cpu上。
     * 然而，尽管有这些限制，可观测到的冲突概率很小。
     * 当hash到Cell的线程终止，扩容table引起没有线程映射到它时，Cell会不被使用。我们不会尝试检测和移除这样的Cell，
     * 因为是在这样的假设下：长期运行的实例，被观察的冲突会重新出现，所以这些Cell最后会被重新需要，短期运行的没有关系。
     *
     * final void longAccumulate(long x, LongBinaryOperator fn, boolean wasUncontended)
     * x,需要增加的值，fn，增加时的回调函数，wasUncontended，false表示有竞争，在cas失败的时候该值为false
     * 执行过程如上所述
     */
}
