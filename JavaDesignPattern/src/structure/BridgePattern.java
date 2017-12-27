package structure;
/*
 * 桥接模式:
 * 将抽象和实现解耦,使得二者可以独立变化
 * 其思想在于将抽象和实现的继承关系(强耦合)转化为聚合/组合关系(弱耦合)
 * 聚合关系就是所谓的"桥"
 * e.g JDBC DriverManager Driver
 */
public class BridgePattern {
    
    //实现角色
    interface Implementor{
        void doSomething();
    }
    
    //抽象角色
    //该角色事实上是被高层调用者调用的
    abstract class Abstraction{
        //抽象角色持有实现角色的引用即"桥"
        //这样,抽象角色和实现角色可以各自变化
        protected Implementor implementor;
        public Abstraction(Implementor implementor) {
            this.implementor = implementor;
        }
        public void request() {
            implementor.doSomething();
        }
    }
    //实现角色的某个具体实现
    class ConcreteImplementor implements Implementor{
        @Override
        public void doSomething() {
            //具体实现
        }
    }
    //抽象角色的某个具体实现
    //修正抽象角色,用于修正父类行为
    class RefinedAbstraction extends Abstraction{

        public RefinedAbstraction(Implementor implementor) {
            super(implementor);
        }
        @Override
        public void request() {
            super.request();
            //另外的业务逻辑
        }
    }
}
