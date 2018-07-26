package vm.memory;

public class Summary {
    /*
     * JVM的内存结构:
     * 1.方法区:用于存储类信息,比如类的字段,方法,常量池等等,JDK1.7及以前称之为永久区,
     *      JDK1.8称之为Metaspace(元数据区)
     *      
     * 2.堆(Heap):用于保存对象
     *

     * 3.直接内存(Direct Memory):直接向操作系统申请的内存,比如NIO的ByteBuffer.allocate方法
     * 
     * 4.栈(Stack):线程私有的空间,栈中保存的是栈帧,栈帧保存着[局部变量表,操作数栈,帧数据区]
     * 
     * 5.本地方法栈(Native Method Stack)
     * 
     * 6.程序计数器(Program Counter Register) 保存了当前的虚拟机字节码指令的地址
     */
    

}
