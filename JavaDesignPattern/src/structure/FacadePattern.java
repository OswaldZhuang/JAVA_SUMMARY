package structure;

/*
 * 门面模式:
 * 子系统中对于一系列接口提供一个统一接口。
 * 门面定义了一个更高层的接口,这使得子系统更加易用。
 */
public class FacadePattern {
    //子系统的模块一
    class SubModual_1{
        //模块中的某个功能,下同
        public void func1() {
            
        }
    }
    //子系统的模块二
    class SubModual_2{
        public void func2() {
            
        }
    }
    //子系统的模块三
    class SubModual_3{
        public void func3() {
            
        }
    }
    //门面
    class Facade{
        //门面封装了子系统的各个模块
        private SubModual_1 modual_1 = new SubModual_1();
        private SubModual_2 modual_2 = new SubModual_2();
        private SubModual_3 modual_3 = new SubModual_3();
        //提供统一的访问方式
        public void func() {
            modual_1.func1();
            modual_2.func2();
            modual_3.func3();
        }
    }
}
