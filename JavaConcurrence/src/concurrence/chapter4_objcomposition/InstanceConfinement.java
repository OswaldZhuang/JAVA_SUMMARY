package concurrence.chapter4_objcomposition;

/*
 * 实例封闭
 * 将数据封装在对象内部,将数据的访问权限封装在方法上,确保线程在访问
 * 数据时持有正确的锁
 */
public class InstanceConfinement {
    /*
     * JDK中有些类可以将非线程安全的容器类转化为线程安全的
     * 比如Collections.synchronizedList
     * 其具体实现实际上是采用装饰模式,利用装饰器类重写原有的方法,
     * (实际上装饰器类和传入的目标类实现了共同的接口)
     * 使得该方法变成线程安全的方法
     * 在使用时,需要仅仅使用装饰器类而非原来的类,否则线程可能会非安全
     */

}
