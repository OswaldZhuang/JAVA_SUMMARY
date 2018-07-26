package vm.memory;


/*
 * 关于Java对象的引用
 */
public class AboutReference {
    /*
     * 引用类型
     */

    /*
     * 强引用
     * 即在代码之中普遍存在的引用,垃圾收集器绝对不会回收强引用
     */

    /*
     * class java.lang.ref.ReferenceQueue<T>
     * 当垃圾收集器监测到可达性的变化时,注册了该队列的引用对象会被垃圾收集器加入该队列中
     * 该类的主要方法为boolean enqueue(Reference<? extends T> r),
     * 而实际上队列的组织是通过Reference.next实现的
     * public Reference<? extends T> poll() 将队列中的元素出队
     * public Reference<? extends T> remove(long timeout)
     * throws IllegalArgumentException, InterruptedException 从队列中移除元素,阻塞时间
     * 为timeout
     */

    /*
     * abstract class java.lang.ref.Reference<T>
     * 引用对象的抽象基类
     * 当垃圾收集器监测到引用的可达性已经改变为某种状态,那么它就会将实例的状态改变为Inactive或者Pending,这取决于
     * 该实例是否在创建的时候注册了Reference Queue(如果注册了队列就是Pending状态)
     * 一个引用实例有如下四种内部状态:
     * Active:新创建的实例是active的状态,该状态下,next=null;queue=ReferenceQueue(或者ReferenceQueue.NULL);
     * Pending:该状态的元素等待被Reference-handler线程加入到队列中,该状态下,next=this;queue=ReferenceQueue;
     * Enqueued:表示元素已经入队,该状态下,next=nextReference;queue=ReferenceQueue.ENQUEUED
     * Inactive:元素的最终状态,该状态下,queue=ReferenceQueue.NULL;next=this
     * 综上所述,垃圾收集器仅仅需要检查next来决定引用实例是否需要特殊对待
     *
     * 为了确保并发的垃圾收集线程在检测active引用对象的时候不会和其他调用enqueue()方法的线程发生相互干扰
     * Reference类中新增了Reference discovered属性来指向检测到的对象,该属性也用作指向pending list中的对象
     *
     * 该类的属性和方法如下:
     * private T referent 引用
     * volatile ReferenceQueue<? super T> queue 引用队列
     * Reference next 引用队列的下一个元素
     * transient private Reference<T> discovered 被发现的引用
     * Reference#Lock 用于垃圾收集器同步的锁,在该类中,是一个static的对象
     * private static Reference<Object> pending = null; 等待入队的对象,垃圾收集器将对象放入到该队列中,与此同时
     * Reference-handler线程将其中的元素移除
     * private static class ReferenceHandler extends Thread 该线程用于将pending list中的引用加入到引用队列中
     * 该线程是守护线程,且优先级是最高的,在Reference的static块中就被启动,其run方法是不断循环并执行tryHandlePending(true)
     * 因此在任何Java程序中该线程都会作为守护线程在后台执行
     * static boolean tryHandlePending(boolean waitForNotify) waitForNotify表示是否在没有pending的
     * Reference时候等待,true则会调用lock.wait阻塞,false则直接返回
     * 该方法执行过程如下:
     * 如果pending非空的话,先保存pending的引用为r,然后将discovered赋给pending,discovered置空,最后调用queue.enqueue将引用r入队
     *
     * public T get() 得到被引用的对象
     * public void clear() 将被引用的对象赋值为null,垃圾收集器不会调用该方法
     * public boolean isEnqueued() 判断是否入队
     * public boolean enqueue() 将该引用入队到注册的队列中,垃圾收集器不会调用该方法
     *
     */

    /*
     * strongly reachable:对象可以被线程访问而不需要遍历任何引用对象
     * softly reachable:对象只能够通过遍历soft reference来访问
     * weakly reachable:对象只能够通过遍历weak reference来访问,如果一个实例是weakly rechable,那么
     * 该实例便有资格finalize
     * phantom reachable:实例已经被finalize,而phantom reference指向该实例
     * finally:该实例不可达
     */

    /*
     * 软引用 class java.lang.ref.SoftReference<T> extends Reference<T>
     * 软引用对象的回收取决于垃圾收集器
     * 软引用常常被用来实现内存敏感的缓存
     * 当一个对象是"softly reachable"时,垃圾收集器会选择自动清除所有指向这个对象的软引用对象
     * 同时或者稍后将这些清除掉的软引用入队(前提是注册了引用队列)
     * 所有指向"softly reachable"对象的软引用都会保证在OOM之前被清除掉(也就是说如果内存不够了,垃圾
     * 收集器就会将这些对象回收)
     * 该类的直接实例可被用于实现简单的缓存,只要软引用对象所指向的对象是"strongly reachable"(也就是说正在被使用)
     * 那么该软引用就不会被清除掉
     * 所以采用该类实现的缓存可以通过保持强引用指向实例来防止被清除掉,而其余是否被清除由垃圾收集器决定
     * 该类新增的属性为:
     * static private long clock 该属性由垃圾收集器更新
     */
    public static void useSoftReference(){

    }

    /*
     * 弱引用 class java.lang.ref.WeakReference<T> extends Reference<T>
     * 弱引用对象无法保证被其引用的对象不被finalize,如果垃圾收集器监测到一个对象是weakly reachable,
     * 那么垃圾收集器就会自动清除掉该对象的所有弱引用
     * 同时或者稍后将这些清除掉的弱引用入队(前提是注册了引用队列)
     * 弱引用一般用于实现规范映射(canonicalizing mappings)
     * 该类没有新添加的属性
     */

    /*
     * 虚引用 class java.lang.ref.PhantomReference<T> extends Reference<T>
     * 如果垃圾收集器监测到一个对象是phantom reachable,那么直接将该引用入队,虚引用
     * 所指向的引用对象是无法得到的(因为已经被finalize),因此get方法返回null
     *
     */

    /*
     * Final 引用 class java.lang.ref.FinalReference<T> extends Reference<T>
     * 该对象用于实现finalization
     */

    /*
     * final class java.lang.ref.Finalizer extends FinalReference<Object>
     *
     * private static class FinalizerThread extends Thread
     * 该线程在类的static块中启动,并且设置成为守护线程
     * 在run方法中,将不断循环,并且将引用队列中的引用出队,交由sun.misc.JavaLangAccess
     * 进行处理(调用其invokeFinalize(this.get())将自身所指向的引用处理掉)
     */



    public static void main(String[] args){
    }

}
