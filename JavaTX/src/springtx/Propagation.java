package springtx;

public class Propagation {
    /**
     * spring中事务的传播
     *
     * 1 REQUIRED 方法必须在事务中运行，如果不存在事务，那么就会新建事务
     * 2 SUPPORTS 非事务地执行，如果存在事务，那么在事务中运行
     * 3 MANDATORY 当前方法必须在一个事务中运行，如果不存在，那么抛出异常
     * 4 REQUIRES_NEW 执行该方法的时候开启一个新的事务，并把当前事务挂起
     * 5 NOT_SUPPORTED 非事务地执行，如果存在事务，那么将其挂起
     * 6 NEVER 非事务地执行，如果存在事务，那么抛出异常
     * 7 NESTED 嵌套事务，比如数据库的事务
     */
}
