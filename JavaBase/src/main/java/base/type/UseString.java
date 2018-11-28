package base.type;

/*
 * ASCII 采用8 bit, 实际只用了7 bit
 * Java采用Unicode编码字符集,即(Basic Multilingual Plane(BMP,基本多语言面)内的)字符采用16 bit(2 字节 , 即一个char)编码
 * 在此之外的采用更多的bit表示
 * UTF-8和UTF-16为字符集编码,即对字符集的编码方式,使之能够存储在计算机中
 * UTF-8是Unicode最为普遍的实现方式,它是变长的
 * UTF-16是定长的,固定16位
 * UTF(Unicode Transformation Formats)
 * BOM:byte order mark, 用于表明UTF-8编码方式, 其为: EF BB BF, 一般并不建议在字节流开头加入BOM
 * CodePoint:指的是一个Unicode中为字符分配的编号,以U+开头
 * CodeUnit:编码方法中对一个字符编码过后所占的最小存储单元
 */

/*Unicode和UTF-8的对应关系:
    U+ 0000 ~ U+ 007F: 0XXXXXXX
    U+ 0080 ~ U+ 07FF: 110XXXXX 10XXXXXX
    U+ 0800 ~ U+ FFFF: 1110XXXX 10XXXXXX 10XXXXXX
    U+10000 ~ U+1FFFF: 11110XXX 10XXXXXX 10XXXXXX 10XXXXXX
    */


/*
 * String对象的底层是由final char[]实现的
 * String是不可变的
 * 由于char只占2个字节,因此对于一些生僻的汉字来说,是不能用char表示的
 * 比如,char c = '𠃾', 编译器就会报错
 * 此时只能使用String来表示,又因为String维护的是一个char[]
 * 所以生僻的汉字通常是由2个char来表示(实际上底层是用一个int来实现的)
 * 这就是所谓的Unicode surrogate pair(USP),其分为高低两位
 * String类型是以UTF-16来编码的
 */
public class UseString {
    
    /*
     * 通过字面量申明的string会保存在MetaSpace中的
     * 常量池里
     */
    static String str1 = "zxd";
    
    /*
     * 该构造函数其实是不必要的
     * 因为“zxd”对象的value(实际上就是char[])
     * 会赋给str2的value,即str2和str1的value
     * 指向同一个对象
     * (注意:str1和str2仍然是两个不同的对象)
     */
    /*
     * 该方法声明的string保存在堆中
     */
    static String str2 = new String("zxd");
    
    /*
     * 编译期间,JVM会对字符串进行优化替换
     * 比如:
     * String str = "as" + "df";
     * 优化后的结果直接为str = "asdf"
     * 但是对于引用类型,JVM在编译期间无法确定其类型,故不会优化
     * 比如:
     * String a = "as";
     * String b = "df";
     * String c = a + b;
     * 但是如果用final修饰的话,还是会进行优化替换的
     */
    
    public static void aboutStringIntern() {
        /*
         * 关于String的本地函数intern()
         * public native String intern()
         * 当该方法被调用的时候,如果字符串常量池中存在该
         * String对象(equals方法返回true),
         * 那么直接返回常量池中的对象的引用,否则在常量池中添加指向堆中的字符串对象的引用
         */

        /**
         * str在调用intern方法后,其指向常量池中的引用,而常量池的引用指向堆中的字符串对象
         * 而在声明字面量的时候查找到常量池中有该字符串,因此str1指向常量池中的引用
         * 所以以下引用全部指向堆中的字符串对象
         */
        String str = new String("str") + new String("01");
        String str_ = str;
        str.intern();
        String str1 = "str01";
        System.out.println(str1 == str_);//true
        System.out.println(str == str1);//true

        /**
         * intern方法不会改变str2a的值,
         * 因此两个引用一个指向常量池中的字符串对象,一个指向堆中的字符串对象
         */
        String str1a = "str01";
        String str2a = new String("str") + new String("01");
        str2a.intern();
        System.out.println(str2a == str1a);//false
    }

    public static void main(String[] args) {
        //String str3 = "齉";
        //System.out.println(str3.getBytes().length);
        //System.out.println(str1 == str2);
        aboutStringIntern();
    }

}
