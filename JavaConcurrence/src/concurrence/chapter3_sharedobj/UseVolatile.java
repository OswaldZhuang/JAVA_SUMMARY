package concurrence.chapter3_sharedobj;

public class UseVolatile {
    /*
     * 使用volatile能够保证可见性,因为对于volatile变量的更改会立刻
     * 反映到内存中,使得其他线程立刻“看到”
     * volatile会禁止指令重排
     */
    
    private volatile int count;
    /*
     * 但需要注意的是:对于复合操作,比如count++,单纯使用volatile无法保证同步
     * 还是需要一定的同步操作
     */
}
