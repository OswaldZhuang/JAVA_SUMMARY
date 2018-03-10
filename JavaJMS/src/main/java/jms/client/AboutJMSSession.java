package jms.client;


public class AboutJMSSession {
    
    /*
     * JMS的Session是JMS中的重要模块之一
     * Interface javax.jms.Session extends Runnable, AutoCloseable
     * 这说明实际上session是一个线程,并且可以自动关闭(实际上指的是在将它定义在try中,
     * try块执行完或catch了exception之后,JVM会自动调用其close()方法将它关闭)
     * session实际上是一个单线程的上下文(context,实际上上下文可以理解为一个表示场景的类,他的作用就是
     * 表示一个场景的应用环境,从代码的角度,context持有大量与场景相关的对象的引用),
     * session作用如下:
     * 1.session是message consumer和message producer的工厂
     * 2.session是temporary queue和temporary topic的工厂
     * 3.支持事物机制
     * 4.在message得到应答之前都会将之保留
     * 5.session是QueueBrowsers的工厂
     * 一旦connection启动(即调用start方法),那么connection中的session就会
     * 用于在线程中收发消息
     */
    

}
