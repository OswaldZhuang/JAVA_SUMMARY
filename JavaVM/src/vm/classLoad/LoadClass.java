package vm.classLoad;

/*
 * class文件加载
 */
public class LoadClass {
    
    /*
     * class文件加载过程
     * 加载 -> 连接(验证 -> 准备 -> 解析) -> 初始化
     * class文件只有在主动使用的情况下才会被装载(即以上整个流程),主动使用包括:
     * 1.创建对象:new, 反射, 反序列化
     * 2.调用类的静态方法,即invokestatic指令
     * 3.使用类或接口的static字段(final除外),即putstatic,getstatic指令
     * 4.反射类的方法(比如getMethod)
     * 5.含有main方法的类
     * 引用final常量不会引起类的初始化,因为在class文件生成的时候,JVM会直接将final常量
     * 植入目标类(根据final不变性),不再使用被引用类
     */

    /*
     * 加载阶段:
     * 通过类全名(即包名+类名),由ClassLoader获取类的二进制流数据(比如说通过.class,jar,war)
     * 解析其二进制数据为方法区内的数据结构
     * 创建java.lang.Class实例,表示该类型(也就是说一个类实际上是
     * Class的实例,这也是反射实现的关键,通过Class能够访问自定义类的各种属性)
     */

    /*
     * 验证阶段:
     * 格式验证-> magic num,版本...
     * 语意验证->是否继承final,是否有父类,是否实现抽象方法...
     * 字节码验证->跳转指令是否指向正确位置
     * 符号引用验证->符号引用的直接引用是否存在
     */

    /*
     * 准备阶段:
     * 主要为类分配内存空间,并且常量字段(final)在该阶段也会被赋予正确的值
     */

    /*
     * 解析阶段:
     * 将类,接口,字段和方法的符号引用转化为直接引用
     */

    /*
     * 初始化阶段:
     * 该阶段主要执行<clinit>方法,该方法由类静态成员的赋值与static块合并而成
     * <clinit>是线程安全的,当多个线程试图去初始化一个类的时候,仅仅有一个线程能执行,
     * (相当于先获得该类的锁,然后才能进行初始化).
     * 然而这可能会导致意想不到的死锁,当某个类A试图在static块中初始化类B,而类B又试图在
     * static块中初始化类A,线程1初始化类A,线程2初始化类B,两条线程同时进行.
     * 线程1初始化类A的时候得到了A的锁,然后在static块中准备初始化B,需要B的锁
     * 线程2初始化类B的时候得到了B的锁,然后在static块中准备初始化A,需要A的锁
     * 这样死锁就导致了
     */
    
}
