package concurrence.chapter2_threadsafe;

import java.util.concurrent.atomic.AtomicReference;

/*
 * 原子类
 */
public class UseAtomicObject {
    
    private final AtomicReference<ClassA> a = 
            new AtomicReference<UseAtomicObject.ClassA>();
    
    class ClassA{
        
    }
}
