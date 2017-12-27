package construct;

/*
 * 原型模式:
 * 用原型实例指定创建对象的种类,并且通过拷贝这些原型创建新对象
 */
public class PrototypePattern {
    
    //原型模式简单的说就是原型类实现Cloeable接口
    //在创建实例的时候不是用new,而是clone
    class Prototype implements Cloneable{
        /*
         * 实际上默认clone方法的原理是把堆中的二进制流拷贝
         * 到新分配的一块内存村中,因此对象中的引用仅仅拷贝
         * 引用,
         * 实现深拷贝需要重写clone方法
         */
    }
}
