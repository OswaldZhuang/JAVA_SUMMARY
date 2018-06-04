package io.nio;

public class AboutSelector {
    
    /*
     * abstract class java.nio.channels.Selector
     * 通过调用Selector#open()创建新的selector对象(该方法实际上是通过ava.nio.channels.spi.SelectorProvider.
     * provider().openSelector()来实现,其实现细节和具体的操作系统有关)
     * 调用Selector#close()方法来关闭selector
     * 注册在Selector上的channel在Selector中存储为SelectionKey
     * selector有三种方式来维护这些key值:
     * public abstract Set<SelectionKey> keys():表示目前注册在selector上的key
     * public abstract Set<SelectionKey> selectedKeys():表示这些key所代表的channel被检测到准备好了其key所感兴趣的操作
     * 被取消的key(cancelled-key),这些key所代表的channel还未从selector中取消注册,但是key已经被取消了,在selector中,这部分的key是无法访问的
     *
     * 在调用SelectableChannel#register(Selector,int)的时候,key被添加到selector的key set中
     * 当调用channel的关闭方法或者调用SelectionKey#cancel,key被加入到cancelled-key set中
     *
     * selection操作通过如下方法完成:
     * public abstract int select() throws IOException 选择已经准备好IO操作的channel,该方法是阻塞的,除非至少有一个channel
     * 的IO操作准备完成,或者被中断,或者wakeup方法调用,返回符合条件的channel的数量
     * public abstract int select(long timeout) throws IOException 同上,多了一个超时的限定规则
     * public abstract int selectNow() throws IOException 选择已经准备好IO操作的channel,该方法是非阻塞的,返回符合条件的channel的数量
     * 这些操作通常涉及到下面这些操作:
     * 1.cancelled-key set中的key会被移除掉,并且与之相关的channel会被取消注册,该步骤完成后,cancelled-key set变为空
     * 2.查询操作系统以检查selector上的channel是否有它的key所感兴趣的操作,如果channel已经准备好了这样的操作,那么执行如下步骤:
     *      2.1 如果该channel所对应的key还没有在selected-key set中,那么先将其加入到该set中,然后改变该key的ready-operation set
     *      以报告其channel上的哪些是已经准备就绪的
     *      2.2 如果其key已经在selected-key set中,那么更新ready-operation set
     *
     *  selector本身是线程安全的,但是对于它其中的key set而言,并不是
     *
     *  public abstract Selector wakeup() 该方法使得第一个正在阻塞的selection方法立刻返回
     *  如果调用该方法的时候还没有线程调用selection方法,那么下次调用selection方法的时候就会立刻返回(但是如果期间调用selectNow的话就会失效)
     *
     */

    public static void  main(String[] args){
        System.out.print(Integer.MAX_VALUE);
    }

}
