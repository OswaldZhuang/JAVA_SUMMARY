package structure;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/*
 * 代理模式:为其他对象提供一种代理以控制对这个对象的访问
 * 委托模式
 * 代理模式关注于对被代理对象的访问控制
 */
public class ProxyPattern {
    
    /*
     * 普通代理
     */
    /*
     * 定义某种主题
     */
    interface Subject{
        void doSomething();
    }
    
    
    /*
     * 该主题的代理
     */
    class Subject_Proxy implements Subject{
        private Subject subject;

        public Subject_Proxy(Subject subject) {
            this.subject = subject;
        }

        @Override
        public void doSomething() {
            /*
             * 代理类包装了原来的类,使得在原来的类的基础上增加了代理类的逻辑
             */
            before();
            this.subject.doSomething();
            after();
        }
        //代理类独有的方法
        private void before() {
            
        }
        //代理类独有的方法
        private void after() {
            
        }
    }
    
    
    /*
     * 强制代理
     * 与普通代理不同,强制代理由真实的角色去管理代理对象
     * 强制代理的好处在于具体的角色必须和相应的代理对象共同存在,
     * 否则无法调用具体角色的业务逻辑
     */
    
    interface mandatory_Subject{
        void doSomething() throws Exception;
        mandatory_Subject getProxy();
    }
    
    class concrete_mandatory_Subject implements mandatory_Subject{
        
        private mandatory_Subject proxy = null;
        
        @Override
        public void doSomething() throws Exception{
            if(isProxy()) {
                //执行相应的业务逻辑
            }else {
                //如果没有指定代理则抛出异常
                throw new Exception();
            }
        }

        @Override
        public mandatory_Subject getProxy() {
            proxy = new mandatory_Proxy(this);
            return proxy;
        }
        
        private boolean isProxy() {
            if(proxy == null) {
                return false;
            }else {
                return true;
            }
        }
        
    }
    
    //强制代理的代理类和普通代理的代理类基本没有差别
    class mandatory_Proxy implements mandatory_Subject{
        
        private mandatory_Subject concrete;
        
        public mandatory_Proxy(mandatory_Subject concrete) {
            this.concrete = concrete;
        }

        @Override
        public void doSomething() throws Exception{
            //代理类对原业务逻辑的增强
            before();
            concrete.doSomething();
            after();
        }
        
        private void before() {
        }
        private void after() {
        }

        @Override
        public mandatory_Subject getProxy() {
            return this;
        }
        
    }

    /*
     * JDK动态代理
     * 普通代理的被代理类是在程序中写好的,而动态代理在运行阶段才指定代理对象
     * AOP的关键技术
     * java.lang.reflect.InvocationHandler
     * java.lang.reflect.Proxy
     */
    /*
     * 每个Proxy有一个相关联的InvocationHandler
     * InvocationHandler用于处理被代理类的方法的调用
     * Proxy用于产生新的代理实例
     */
    
    class Subject_method_InvocationHandler implements InvocationHandler{
        //被代理的类
        private Subject subject;
        
        public Subject_method_InvocationHandler(Subject subject) {
            this.subject = subject;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            /*
             * 代理实例的接口方法(这里即doSomething)会被编码并分发到invoke方法
             */
            Subject_Proxy subject_Proxy = (Subject_Proxy)proxy;
            subject_Proxy.before();
            subject.doSomething();
            subject_Proxy.after();
            return null;
        }
    }
    
    class DynamicProxy{
        //测试动态代理
        public void test(){
            Subject subject = new Subject() {
                @Override
                public void doSomething() {
                    
                }
            };
            Subject_method_InvocationHandler h = new Subject_method_InvocationHandler(subject);
            /*
             * 该方法会首先根据classLoader和Interfaces找到代理类
             * 再通过invocationHandler得到代理实例
             */
            Subject subjectProxy = (Subject)java.lang.reflect.Proxy.newProxyInstance(subject.getClass().getClassLoader(),
                    subject.getClass().getInterfaces(), h);
            subjectProxy.doSomething();
        }
    }
    
    
}
