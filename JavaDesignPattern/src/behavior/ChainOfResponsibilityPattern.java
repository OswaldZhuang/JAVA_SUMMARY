package behavior;
/*
 * 责任链模式:
 * 使多个对象都有机会处理请求,从而避免了请求的
 * 发送者和接受者之间的耦合.将这些对象连成一条
 * 链,并沿着这条链传递该请求,直到有对象处理它为止(或者
 * 说责任链上的对象处理请求的不同部分)
 * 其思想在于将处理者分成多个,每个处理者的责任单一,避免
 * 单个处理者处理能力很强但是代码膨胀的现象
 * e.g Netty Tomcat处理request
 */
public class ChainOfResponsibilityPattern {
    //请求的处理者
    abstract class Handler{
        //责任链上的下一个处理者
        private Handler next_handler;
        public void setNext_handler(Handler next_handler) {
            this.next_handler = next_handler;
        }
        /*
         * 实际上不一定是等级要一致才处理请求
         * 责任链上的每个处理者也可以只处理请求
         * 的某一个部分,实现单一职责,责任链走完后
         * 请求也就被完整的处理了
         */
        public final void handleRequest(Request r) {
            //如果处理者等级和请求等级一致
            if(getHandlerLevel().equals(r.getRequestLevel())) {
                doHandle(r);
            }else {
                //处理者等级不够时交给责任链上的下一个处理者
                if(next_handler != null) {
                    next_handler.handleRequest(r);
                }else {
                    //抛出异常或其他处理
                }
            }
        }
        protected abstract Level getHandlerLevel();
        protected abstract void doHandle(Request request);
    }
    
    interface Request{
        Level getRequestLevel();
    }
    //请求的处理等级
    class Level{
    }
    
}