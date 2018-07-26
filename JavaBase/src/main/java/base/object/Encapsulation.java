package base.object;

//静态导入 
//只能导入static修饰的方法或属性
import static java.lang.Math.abs;
//普通导入
import java.io.File;
//封装性
public class Encapsulation {
    
    //任何地方可以访问
    public String id;
    //仅限于类自身访问
    private String name;
    //子类和同一个包中可以访问
    protected int age;
    
    public void method() {
        File file;
        abs(22.2);
    }
    
}
