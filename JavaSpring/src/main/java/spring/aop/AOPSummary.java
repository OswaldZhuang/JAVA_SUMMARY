package spring.aop;

/**
 * Spring AOP
 */
public class AOPSummary {
    /**
     * AOP: aspect oriented programming
     * 简单来说就是把代码"织入"到目标代码中实现功能的增强
     */

    /**
     * AOP的术语：
     * JoinPoint：简单来说就是一个代码的执行位置，比如代码中一个方法的调用，在spring中，JoinPoint指的就是方法的调用
     * Advice：在特定的JoinPoint处由Aspect执行的行为 @Before @After @Around @AfterReturning @AfterThrowing
     * PointCut：PointCut是和JoinPoint相匹配的断言（本质就是代表JoinPoint），简单来说就是一个表达式用来表示JoinPoint @Pointcut
     * （Spring的AOP使用AspectJ风格的PointCut表达式）
     * Aspect：即"切面"，指的是增强之后的功能（具体的话指的是一个类），@Aspect
     * Introduction：代表一个类型（类或者接口）声明额外的方法和属性，
     *
     * AOP的实现采用动态代理，SpringAOP采用JDK和Cglib的方式进行动态代理
     */
}
