package concurrence.chapter15_unblock;

/*
 * Java中的Unsafe类
 */
public class AboutUnsafe {
    /*
     * sun.misc.Unsafe 
     * 该类无法通过new来进行实例化(因为他的构造器是私有的),其提供了
     * 静态的getUnsafe方法来获取实例,然而再调用这个方法的时候,方法内部会通过
     * 反射检查调用者的ClassLoader是否是SystemDomainLoader,如果不是
     * 将会抛出SecurityException
     * Unsafe类提供了许多底层的操作,包括:
     * 1.CAS操作:
     *   public final native boolean compareAndSwapXXX(Object obj, long valueOffset, XXX expect, XXX update);
     *   Unsafe中仅仅提供了三种类型的CAS操作(Object,long,int)
     *   object表示CAS操作的那个对象,valueOffset表示相应字段在该对象中的偏移量,expect为期待值,update为更新的值
     * 2.线程的唤醒与挂起操作:
     *   public native void unpark(Object thread)
     *   
     *   public native void park(boolean isAbsolute, long returnNanoTime)
     *   阻塞线程,isAbsolute表示是否为绝对时间,false表示相对时间,true表示绝对时间
     *   returnNanoTime表示多少时间后返回这个方法
     */
}
