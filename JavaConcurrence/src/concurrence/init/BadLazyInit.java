package concurrence.init;

/*这种延迟加载模式存在线程安全性问题，当A，B两个
 * 线程同时在调用getInstance方法时，线程A
 * 看到badLazyInit为空，于是初始化badLazyInit
 * 如果初始化的方法时间较长，那么在badLazyInit
 * 被初始化完成之前线程B也会认为其为空，B也会进行
 * 初始化*/
public class BadLazyInit {
    
    private static BadLazyInit badLazyInit = null;
    
   /*不仅是以上的问题，由于JMV的指令重排，可能会使得B线程看到的情况是：对
    * badLazyInit的写入操作在badLazyInit的各个域的写入操作
    * 之前发生(即便A线程并不是这么做的)，这样，B线程看到的就是一个
    * 被部分初始化的实例*/
    public static BadLazyInit getInstance() {
        if(badLazyInit == null) {
            badLazyInit = new BadLazyInit();
        }
        return badLazyInit;
    }

}
