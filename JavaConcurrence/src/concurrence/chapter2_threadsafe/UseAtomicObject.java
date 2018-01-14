package concurrence.chapter2_threadsafe;

import java.util.concurrent.atomic.AtomicInteger;

/*
 * 原子变量
 * 原子变量基于非阻塞的同步机制,
 * 其中所用到的技术为CAS(compare and swap 比较并交换,这是一种硬件指令)
 * 即:我认为V的值应该是A,如果是那么将V的值更新为B,否则不修改
 * 并告诉我V的值实际应该是多少
 * 使用原子变量并不会使得线程阻塞,而是让线程重试,直到成功为止
 * 因此在竞争非常激烈的情况下,使用原子变量比锁的效率更低
 */
public class UseAtomicObject {
    
    /*
     * 以AtomicInterger为例
     * 它的ComapreAndSet方法实际上
     * 是调用的Unsafe类的compareAndSwapInt方法
     * (这个方法是个native方法,用C语言实现)
     */
    private AtomicInteger a = new AtomicInteger(0);
    
    public void useAtomic() {
        //得到当前值并将其加1
        a.getAndIncrement();
    }
    
    /*
     * 非阻塞算法
     */
    
}
