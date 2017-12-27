package behavior;
/*
 * 状态模式:
 * 当一个对象的内部状态改变时,允许这个对象改变它的行为,
 * 这看起来就好像改变了它的类一样
 * 状态模式的设计思想在于把状态都抽象到每个类中,在不同的状态下
 * 实现不同的动作,避免将判断状态实施动作的逻辑耦合在一个类中(形成
 * 复杂的if else结构),而状态统一交给状态上下文(Context)管理
 */
public class StatusPattern {
    
    /*
     * 定义状态的抽象类
     */
    abstract static class Status{
        
        protected Context context;
        
        public void setContext(Context context) {
            this.context = context;
        }
        
        public Context getContext() {
            return context;
        }
        //某种状态下的具体行为
        //handle1对应status1
        public abstract void handle1();
        //handle2对应status2
        public abstract void handle2();
    }
    
    static class Status1 extends Status{

        @Override
        public void handle1() {
            //在status1下所执行的业务逻辑
        }

        @Override
        public void handle2() {
            //首先将状态过渡到status2
            super.context.setCurrentStatus(Context.status2);
            //把具体逻辑委托给status2完成
            super.context.getCurrentStatus().handle2();
        } 
        
    }
    static class status2 extends Status{

        @Override
        public void handle1() {
            super.context.setCurrentStatus(Context.status1);
            super.context.getCurrentStatus().handle1();
        }

        @Override
        public void handle2() {
            //在status2下所执行的业务逻辑
        }
        
    }
    
    /*
     * 状态上下文,用于具体状态的切换
     * 也就是状态机
     */
    static class Context{
        public static final Status status1 = new Status1();
        public static final Status status2 = new status2();
        //当前状态
        private Status currentStatus;
        //获取当前状态
        public Status getCurrentStatus() {
            return currentStatus;
        }
        public void setCurrentStatus(Status currentStatus) {
            this.currentStatus = currentStatus;
            currentStatus.setContext(this);
        }
        //具体的动作交给具体的状态处理
        public void handle1() {
            currentStatus.handle1();
        }
        public void handle2() {
            currentStatus.handle2();
        }
        
    }
    
    

}
