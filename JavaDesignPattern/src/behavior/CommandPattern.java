package behavior;
/*
 * 命令模式:
 * 将一个请求封装为一个对象,以不同的请求,队列或日志请求把客户端参数化
 * 并且支持撤销操作
 */
public class CommandPattern {
    
    //命令调用者
    class Invoker{
        private  Command command;
        public void setCommand(Command command) {
            this.command = command;
        }
        //执行具体的命令
        public void doAction() {
            command.execute();
        }
    }
    //命令执行者,或者说命令接受者
    interface Executor{
        public void doExecute();
    }
    
    //抽象命令
    abstract class Command{
        private Executor executor;
        public void setExecutor(Executor executor) {
            this.executor = executor;
        }
        public void execute() {
            executor.doExecute();
        }
    }
    
    //具体某个命令
    class SomeCommand extends Command{
        @Override
        public void execute() {
            //其他处理逻辑
            super.execute();
        }
        
    }
    
}
