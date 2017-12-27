package io.serialize;

import java.io.Externalizable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayList;

/*
 * Java序列化
 * 序列化的工作机制:
 * 实际上,基本类型和可序列化的属性(Serializable和Externalizable)会被写入到"block-data"(块数据)记录中,
 * 块数据由头部和数据部分组成
 * 头部:标记,头部之后的长度(单位byte)
 * 连续的基本类型数据会被写入到一个block-data中
 * 每个block-data record最多1024bytes
 * 实现详见ObjectOutputStream#BlockDataOutputStream
 * 序列化的时候会把类签名和可序列化的属性写入到流中
 */
public class AboutSerialize {
    
    /*
     * 默认的序列化,只需要实现Serializable即可,Serializable接口是一个标记
     * 但是默认序列化机制存在如下问题:
     * 1.默认序列化会暴露私有属性
     * 2.默认序列化消耗的内存空间和时间较大
     * 3.可能会引起栈溢出(如果类里面的属性引用形成环)
     */
    class C implements Serializable{
        
    }
    
    class Super implements Serializable{
        
        /*
         * 版本
         * 设置版本是为了序列化的兼容性,同时也为了在不同的JVM间移植
         * 在反序列化的时候虚拟机会校验这个版本号以确定发送端的对象和接收端的对象可以兼容
         * 不兼容则会抛出InvalidClassException
         * 如果没有显示指定这个版本号,那么虚拟机会根据对象的各种方面计算这个值(同时也和
         * 虚拟机的实现有关)
         * 版本号最好设置为private,因为版本号即便被子类继承到,也是没有用的,
         * 数组类不能显式定义版本号(?)
         */
        private static final long serialVersionUID = 1L;
        
        private double height;
    }
    
    class A extends Super implements Serializable{
        /*
         * 可序列化的类的所有属性必须能够序列化
         * 序列化机制会递归地序列化引用属性
         * 静态属性不在序列化范围之内
         */
        private C c;
        
        private String name;
        
        transient private int age;
    }
    
    /*
     * 保存在磁盘中的序列化对象都有一个编号
     * JVM在序列化之前会检查该对象是否已经被序列化过
     * 若序列化过则只是输出编号
     */
    public void SerialAndDeserial() {
        try {
            ObjectOutput objectOutput = new ObjectOutputStream(
                    new FileOutputStream(new File("a.txt")));
            A a = new A();
            /*
             * 
             */
            objectOutput.writeObject(a);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
         * 序列化的顺序要和反序列化的顺序一致
         * 可序列化的类的父类要么也是可以被序列化的,要么带有public/protected的无参数的构造器(即父类不可被序列化时)
         * 不可序列化的父类属性不会被写入流中
         * 实际上,序列化的时候会先调用无参数的构造器生成对象，然后才是填充属性
         * 然而反序列化的时候并不会调用构造器生成对象
         */
        try {
            ObjectInput objectInput = new ObjectInputStream(
                    new FileInputStream(new File("a.txt")));
            A a1 = (A)objectInput.readObject();
        } catch (Exception e) {
            // TODO: handle exception
        }
        
    }
    
    /*
     * 自定义序列化
     * 1
     */
    class M implements Serializable{
        
        private String name;
        
        private int age;
        
        /*
         * 自定义序列化必须实现如下方法签名,详见Serializable.class
         * 
         */
        private void readObject(java.io.ObjectInputStream stream)
                throws IOException, ClassNotFoundException{
            this.name = (String)stream.readObject();
            this.age = stream.readInt();
        }
         private void writeObject(java.io.ObjectOutputStream stream)
             throws IOException{
             stream.writeObject(name);
             stream.writeInt(age);
         }
         /*
          * 当反序列化的流不完整的时候,该方法用于正确初始化反序列化对象
          */
         private void readObjectNoData()
             throws ObjectStreamException{
             
         }
         
         /*
          * 该方法用于在序列化某个对象时将其替换为其他对象
          */
         private Object writeReplace() throws ObjectStreamException{
             return new ArrayList<>();
         }
         
         /*
          * 该方法的返回值会代替原来反序列化的对象
          * 适用于单例类,枚举类的序列化(因为在反序列化的时候,如果采用默认的readObject,
          * 那么构造的对象并不是同一个,即反序列化多次就有多个不同对象,这样就破坏了单例和枚举)
          */
         private Object readResolve() throws ObjectStreamException{
             return null;
         }
         
    }
    
    /*
     * 自定义序列化
     * 2
     * 强制指定需要序列化哪些属性
     */
    public class X implements Externalizable{

        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
        }

        @Override
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        }
        
    }
    
}
