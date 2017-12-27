package base.object;

/*
 * 内部类与内部接口
 * 分为成员内部类和方法内部类(局部内部类)
 * 成员内部类在编译之后的文件格式通常为OutClass$InnerClass.class
 */
public class InnerClass {
    
    private String attr1;
    
    private static String attr2 = "attr2";
    
    class inner_class{
        /*
         * 1.普通内部类可以访问任何外部类的属性,因为普通内部类持有外部类的this引用
         * 2.要产生普通内部类对象必须首先产生外部类对象(即new InnerClass.(new inner_class()))
         * 也可以说:普通内部类的存在必须是以对象方式,并且前提是外部类对象存在
         * 3.外部类的静态方法无法访问普通内部类
         * 4.普通内部类里不能存在静态方法,静态属性和静态初始化块(因为这样做没有任何意义)
         */
        public void method() {
        }
    }
    
    static class static_inner_class{
        /*
         * 静态内部类只能访问外部类的静态属性
         */
        public void method() {
        }
        
        public static void static_method() {
            
        }
    }
    
    /*
     * 内部接口默认static修饰
     */
    interface inner_interface{
        
    }
    
}
