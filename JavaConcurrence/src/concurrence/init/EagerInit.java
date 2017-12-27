package concurrence.init;

/*提前初始化*/
/*由于static修饰的属性在类初始化的时候就会加载，
因此静态的对象无论在被构造还是被引用的时候都不需要
同步。但是，多个线程对其进行操作的时候仍要需要足够的
同步确保数据不被破坏*/
public class EagerInit {
    private static EagerInit eagerInit = new EagerInit();
    
    private EagerInit getInstance() {
        return eagerInit;
    }
}
