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
    
}
