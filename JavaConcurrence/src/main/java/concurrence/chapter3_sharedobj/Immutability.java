package concurrence.chapter3_sharedobj;

import java.util.HashSet;
import java.util.Set;

/*
 * 不变性
 */
public class Immutability {
    /*
     * 使用关键字final
     * final域可以保证初始化过程的安全性(因为在初始化的时候仅仅只有一个线程能够初始化该类)
     * 从而可以不必同步这些对象
     */
    private final Set<String> set = new HashSet<>();
    
    public Immutability() {
        set.add("马云");
        set.add("马化腾");
        set.add("李彦宏");
    }
    
    public boolean exists(String name) {
        return set.contains(name);
    }
    
}
