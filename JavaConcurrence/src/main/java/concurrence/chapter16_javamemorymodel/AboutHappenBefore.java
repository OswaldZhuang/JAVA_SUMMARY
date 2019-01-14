package concurrence.chapter16_javamemorymodel;


public class AboutHappenBefore {
    /**
     *  happen-before不能理解为"先于发生"，
     *  比如A hb B 是指A的操作对变量的改变在B操作中是可见的
     *  happen-before描述的是可见性而不是行为
     */
    /**
     * 8大规则：
     * 1 单线程规则
     *  单线程的书写在前面的操作happen-before书写在后面的操作，即后面的操作能够"看到"前面的操作所带来的变化
     *  因此前后操作的顺序不一定非要严格按照书写顺序，也就是可以指令重排
     * 2 监视器锁规则
     *  锁的unlock操作happen-before该锁的lock操作
     *  即锁的释放对获取锁是可见的
     * 3 volatile变量规则
     *  volatile变量的写操作happen-before其读操作，也就是volatile变量的语意：写是立刻可见的
     * 4 线程启动规则
     *
     * 5 线程join规则
     *
     * 6 线程中断规则
     *
     * 7 线程终结规则
     *
     * 8 happen-before传递性规则
     *  A hb B，B hb C，那么A hb C
     */

}
