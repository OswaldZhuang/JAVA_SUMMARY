package concurrence.chapter3_sharedobj;

import java.util.HashSet;
import java.util.Set;

public class Immutability {
    /*
     * 使用关键字final
     * final域可以保证初始化过程的安全性
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
    
    /*
     * 使用volatile
     * 
     */
}
