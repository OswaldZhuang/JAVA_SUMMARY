package concurrence.chapter3_sharedobj;


//ThreadLocal类能够有效的保证线程封闭
//ThreadLocal对象通常用于防止对可变的单例变量
//或全局变量进行共享
//ThreadLocal提供了get和set等方法，这些方法为每个
//使用该变量的线程都存有一份副本，因此对于同一个线程而言，
//get总是返回该线程set的值（而不是其他线程set的值）
public class UseThreadLocal {
    
    //ThreadLocal类里面包含一个内部类叫做ThreadLocalMap,
    //ThreadLocalMap中的Entry保存的是(ThreadLocal<?> k, Object v)
    //每个Thread当中持有ThreadLocalMap对象
    //因此：多个线程就会持有多个ThreadLocalMap对象，
    //它们保存的是同一个ThreadLocal引用，但是value的值是各自独立的
    ThreadLocal<Integer> count = new ThreadLocal<>();
    
    public void ThreadLocalUse() {
        //set方法首先会通过当前线程找到其对应的ThreadLocalMap
        //然后 map.set(this, value)
        //也就是说Thread对象里的ThreadLocalMap对象会保存多个
        //(ThreadLocal,value)键值对
        count.set(new Integer(5));
        
        //所以get方法自然是也是根据前线程找到其对应的ThreadLocalMap
        //然后再ThreadLocalMap.Entry e = map.getEntry(this)
        //最后T result = (T)e.value
        count.get();
    }
    

}
