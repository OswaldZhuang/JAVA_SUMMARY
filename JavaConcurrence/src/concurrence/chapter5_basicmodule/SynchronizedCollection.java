package concurrence.chapter5_basicmodule;

/*
 * 同步容器类
 * 如Vector,Collections.synchronizedXXX(xxx)
 * 这样的容器的并发性能较低,因为他们仅仅简单的对每个方法进行加锁
 * 这样每次仅仅只能由一个线程可以访问该容器
 */
public class SynchronizedCollection {
    
    /*
     * 关于ConcurrentModificationException
     * 对collection采用iterator方式进行迭代的时候
     * 如果在iterator迭代的过程中使用了collection本身的
     * remove方法(而不是iterator的remove,或者是其他的修改方法),
     * 那么(hashNext()或next())就会抛出ConcurrentModificationException,这是因为
     * iterator本身存有collection的计数器,如果实际的数量和
     * 期待的不一样,那么就会抛出上述异常,这种方法也被称之为快速失败
     * 机制(fail-fast)
     * 在多线程环境下,这个并发修改的异常更是普遍(如果没有做好同步的话)
     */
    
    /*
     * 要解决上述迭代的问题,最快能够想到的方法就是对容器对象加锁,使得每个
     * 时间点仅仅只能有一个线程能够访问该容器,但是,如果容器内的值过多
     * 那么加锁会影响系统的性能(迭代时间过长或者其他线程对其竞争或者死锁等等).
     * 另一个解决方案是克隆这个容器,在克隆的容器上进行迭代,注意,即便如此,克隆
     * 过程中仍然需要对容器加锁
     */
}
