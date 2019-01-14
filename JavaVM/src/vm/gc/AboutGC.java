package vm.gc;

public class AboutGC {
    /*
     * 判断对象是否"活着"
     * 采用"可达性分析算法":
     * 当一个对象到"GC Roots"没有任何引用链相连,则这个对象是不可用的
     * GC Roots包括:
     * 虚拟机栈(栈帧中的本地变量表)中引用的对象
     * 方法区中static对象
     * 方法区中常量引用的对象(比如final对象)
     * native 方法引用的对象
     */

    /*
     * Object
     * protected void finalize() throws Throwable { }
     * 当垃圾收集器认定没有引用指向这个对象的时候,该方法会被垃圾收集器调用
     * 该方法可能会作出任何动作,包括重新使得对象可达(通过重写该方法使得对象再次可达)
     * java语言并不保证哪个线程会调用finalize方法,只会保证当finalize
     * 方法调用的时候不会持有任何用户可见的同步锁,
     * 如果finalize抛出未捕获的异常,那么异常将被忽略,并且该方法会被终结
     * 该方法调用完毕后,如果虚拟机再次认定该对象不可达,那么该对象就可能
     * 会被回收
     * finalize方法最多被虚拟机调用一次
     * 该方法并不建议使用
     */

    /**
     * minor gc
     *  即新生代的gc
     * full gc
     *  == major gc
     *  整个堆的gc，在老年代空间不足的情况下会触发
     *  Runtime.getRuntime.gc()会显式的触发full gc
     * 在执行full gc之前是不会执行minor gc的，但是可以通过设置参数来指定-XX: +ScavengeBeforeFullGC
     */


}
