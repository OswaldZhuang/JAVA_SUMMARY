package construct;

/*
 * 单例模式
 * 一个类当且仅需要一个对象
 */
public class SingletonPattern {
    
    /*
     * 通用代码
     * 采用static修饰以保证在类加载的时候该对象就被
     * 创建出来,这样就不会产生线程安全的问题
     * 但这种写法的缺点在于没有实现lazy-init
     */
    static class A {
        
        private static final A a = new A();
         
        public static A getInstance() {
            return a;
        }
        private A() {
        }
    }
    
    /*
     * 非线程安全的lazy-init
     */
    static class M{
        private static M m = null;
        
        public static M getInstance() {
            if(m == null) {
                m = new M();
            }
            return m;
        }
    }
    
    /*
     * 线程安全的lazy-init
     * 但是并不推荐使用,因为每次得到实例的开销太大
     * (存在锁的操作)
     */
    static class L{
        private static L l = null;
        
        public synchronized static L getInstance() {
            if(l == null) {
                l = new L();
            }
            return l;
        }
    }
    
    /*
     * lazy-init并且线程安全的写法
     * 改写法的好处在于即便加载了B类,BHolder也不会被创建
     * 只有显示得调用(或者说引用)到BHolder的时候它才会被
     * 创建,而只有getInstance方法能够引用到它,因此实现
     * 了lazy-init
     */
    static class B {
        private static class BHolder{
            public static B b = new B();
        }
        public static B getInstance() {
            return BHolder.b;
        }
    }
    
    /*
     * 通过枚举(看起来很少用到)
     * 该方式没有线程安全的问题,
     */
    static enum C {
        instance;
        private C() {
            
        }
    }
}
