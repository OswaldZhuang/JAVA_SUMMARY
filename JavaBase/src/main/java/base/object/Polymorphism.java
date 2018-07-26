package base.object;

//多态性
//简单来说，就是一个引用，多种状态
//(父类的引用指向子类的对象)
//引用所指向的具体类型，以及调用的该引用的
//方法是在程序运行期间决定的(而不是编译期间)
//即类型多态和方法多态
public class Polymorphism {
    
    static interface A{
        public void doSomething();
    }
    
    static class B implements A{

        @Override
        public void doSomething() {
            System.out.println("This is method of B");
            
        }
        
        public void method(String name) {
            
        }
        
    }
    
    static class C extends B{
        
        //方法重写
        //动态绑定
        @Override
        public void doSomething() {
            System.out.println("This is method of C");
        }
        
        
        //方法重载 overload
        //静态绑定
        public void method(int name) {
            
        }
        
    }
    
    public static void main(String[] args) {
        //静态类型，变量的声明类型，也叫做编译时类型，即A
        //动态类型，变量实际指向的类型，也即运行时类型，即C
         A a1 = new C();
         A a2 = new B();
         
         C c1 = new C();
         
         B b = new C();
         //动态单分派，根据方法的调用者
         //动态绑定，根据动态类型决定调用方法
         b.doSomething();
         
         a1.doSomething();
         
         //静态多分派，根据方法的调用者和方法的方法的参数
         //静态绑定，根据静态类型决定调用方法
         c1.method(1);
         c1.method("1");
    }

}
