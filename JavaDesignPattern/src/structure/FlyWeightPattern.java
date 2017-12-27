package structure;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*
 * 享元模式:
 * 使用共享对象来有效支持大量细粒度的对象
 * 其思想在于对象的可共享部分和不可共享部分的剥离
 */
public class FlyWeightPattern {
    
    static abstract class FlyWeight{
        //内部状态,不随环境改变而改变的可共享部分
        private final String intrinsic = "xxx";
        //外部状态,会随环境改变而改变的不可共享部分,该变量
        //由客户端决定,是对象池中的key
        private String extrinsic;
        
        public FlyWeight(String extrinsic) {
            this.extrinsic = extrinsic;
        }
        //定义一些公共方法
    }
    
    static class ConcreteFlyWeight extends FlyWeight{

        public ConcreteFlyWeight(String key) {
            super(key);
        }
        
    }
    
    static class FlyWeightFactory{
        //定义对象池
        private static Map<String, FlyWeight> pool = new ConcurrentHashMap<>();
        
        //ConcurrentHashMap保证了该方法的线程安全性
        public static FlyWeight getFlyWeight(String key) {
            FlyWeight result = null;
            if(pool.containsKey(key)) {
                result = pool.get(key);
            }else {
                result = new ConcreteFlyWeight(key);
                pool.put(key, result);
            }
            return result;
        }
    }
}
