package concurrence.chapter3_sharedobj;

import java.util.HashMap;
import java.util.Map;

//栈封闭
//栈封闭是线程封闭的一种特例，栈封闭中，只能
//通过局部变量才能访问对象。
public class StackConfinement {
    
    public void baseTypeConfinement() {
        //对于基本类型的局部变量，任何其他线程
        //都不能获得它的引用，因此其始终封闭在线程中
        int count = 0;
        
    }
    
    public void referenceTypeConfinement() {
        Map<String, Object> map;
        //对于引用类型的局部变量，需要保证其引用不会逸出，
        //也就是说，方法内部的其他对象不会调用到该引用
        //只要满足以上要求，即便是引用线程不安全的对象
        //该对象仍然是线程安全的
        map = new HashMap<>();
    }

}
