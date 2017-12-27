package behavior;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

/*
 * 迭代器模式:
 * 迭代器提供一种方法访问一个容器对象中各个元素,而又不需要暴露该对象的内部细节
 * 详见Java的Collection类
 */
public class IteratorPattern {
    List<String> list = new ArrayList<>();
    
    public void useIterator() {
        /*
         * Iterator的实现类是集合的一个内部类,对iterator的操作
         * 实际上是就是对集合类的操作(因为iterator持有集合内部数据结构
         * 的引用)
         */
        java.util.Iterator<String> iterator = list.iterator();
        while(iterator.hasNext()) {
            /*
             * iterator.next()会检查是否有更改,如果发现预期值和
             * 实际值不同,就会抛出ConcurrentModificationException()
             */
            String element = iterator.next();
        }
    }
}
