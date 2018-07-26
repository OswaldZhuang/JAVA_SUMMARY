package spring.ioc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/*
 * Spring装配:对bean依赖关系的管理
 * 依赖注入(DI),控制反转(IOC)
 * 即对内部bean的依赖并不是由自身控制,而是由Spring容器控制
 */
public class Assemble {
    
    @Component
    static class A{
        
    }
    
    @Component
    static class B{
        /*
         * 自动装配,可以不用写set方法
         */
        @Autowired
        private A a;
        
        private String name;
        
        public B(String name) {
            this.name = name;
        }
        
        public void say() {
            System.out.println(name);
        }
    }
    
    @Component
    static class C{
        /*
         * 对于多个同类型的依赖,并不建议使用@Autowire
         * 而是使用set方法(或者构造函数),因为容器无法知道应该装配哪个bean
         */
        private B b1;
        
        private B b2;
        
        public void setB1(B b1) {
            this.b1 = b1;
        }
        
        public void setB2(B b2) {
            this.b2 = b2;
        }
        
        public void say() {
            b1.say();
        }
    }
    
    @Component
    static class D{
        /*
         * 对于有多个同类型的bean,但是依赖其中一个
         * 仍然不建议使用@Autowire,而是使用set,或者使用构造函数
         * 原因同上
         */
        private B b;
        
        public void setB(B b) {
            this.b = b;
        }
        
        public void say() {
            b.say();
        }
    }
    
    @Configuration
    @ComponentScan
    static class Config{
        /*
         * 装配的配置
         * 如果没有指定name属性,那么bean的名字就是方法的名字
         * 否则是name属性
         */
        @Bean(name = "a")
        public A a() {
            return new A();
        }
        
        @Bean(name = "bk")
        public B b1() {
            return new B("b1");
        }
        
        @Bean(name = "bj")
        public B b2() {
            return new B("b2");
        }
        
        @Bean(name = "c") 
        public C c(B bj, B bk) {
            C c = new C();
            c.setB1(bj);
            c.setB2(bk);
            return c;
        }
        
        @Bean(name = "d")
        public D d(B bk) {
            D d = new D();
            d.setB(bk);
            return d;
        }
        
    }
    
    public static void main(String[] args) {
        ApplicationContext appCtx = new AnnotationConfigApplicationContext(Config.class);
       // C c = appCtx.getBean(C.class);
        //c.say();
    }
}
