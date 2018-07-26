package base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * annotation即表示元数据,
 * 它可以在编译,类加载,运行时被读取,并执行相应处理
 * 访问和处理annotation的工具称为APT(Annotation Processing Tool)
 * annotation可以作用在类,构造器,方法,成员变量,参数,局部变量等
 */

public class UseAannotation {
    
    /*五种基本注解
     *  @Deprecated
     *  @Override
     *  @SuppressWarnings
     *  @SafeVarargs
     *  @FunctionalInterface
     *  */
    
    /*
     * 自定义注解
     */
    /*
     * @Retentio表示annotation的保留时间
     * RUNTIME:只是将annotation记录在class文件中,运行时JVM可以获取
     * CLASS:只是将annotation记录在class文件中,
     * SOURCE:只是将anntation记录在源文件中
     */
    @Retention(value = RetentionPolicy.RUNTIME)
    
    /*
     * @Target表示annotation作用于哪个程序单元
     *  ANNOTATION_TYPE
     *  CONSTRUCTOR
     *  FIELD
     *  LOCAL_VARIABLE
     *  METHOD
     *  PACKAGE
     *  PARAMETER
     *  TYPE
     */
    @Target(value = ElementType.METHOD)
    
    /*
     * @Documented使得该注解能够被javadoc提取
     */
    @Documented
    
    /*
     * @Inherited指明该注解有继承性
     */
    @Inherited
    public @interface myAnnotation{
        /*
         * 定义annotation的成员变量
         * 形式为: 类型 变量名() default 默认值
         */
        String name() default "zxd";
        int id() default 8;
    }
    
    
    /*
     * Java8:
     * 定义重复注解
     * 首先要定义容器注解
     * 并且容器注解的保留期要比容器里的注解要长
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(value = ElementType.TYPE)
    public @interface Tags{
        Tag[] value();
    }
    
    @Repeatable(Tags.class)
    @Retention(RetentionPolicy.RUNTIME)
    @Target(value = ElementType.TYPE)
    public @interface Tag{
    }
    
    /*
     * Java8:
     * TypeAnnotation
     * TypeAnnotation可以在任何使用到类型的地方应用
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE_USE)
    public @interface NotNull{
    }
    
    
}
