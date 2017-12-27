package io.serialize;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

/*
 * 序列化工作原理
 */
public class HowSerializeWorks {
    
    public void analyse() throws IOException{
        
        /*
         * ObjectOutputStream extends OutputStream 
         *          implements ObjectOutput, ObjectStreamConstants
         * ObjectOutput extends DataOutput
         */
        
        
        /*
         * 对象初始化的时候会初始化以下对象:
         * BlockDataOutputStream(核心组件,用于写block-data,实际上是内部的DataOutputStream在工作,
         * 而DataOutputStream实际上是调用的BlockDataOutputStream的方法,这也是回调方式)
         * HandleTable(轻量级的hash表,用于映射对象到整数)
         * ReplaceTable(轻量级的hash表,用于映射替换的对象)
         * enableOverride = flase
         * 然后通过BlockDataOutputStream写入流的头部信息(STREAM_MAGIC,STREAM_VERSION,short类型)
         */
        /*
         * ObjectOutputStream还有一个无参数的构造器(protected),无参数的构造器中,上述的内部对象全部设为null,并且enableOverride
         * 为true
         * 同时会调用SecurityManager#checkPermission(SUBCLASS_IMPLEMENTATION_PERMISSION)
         * 其中SUBCLASS_IMPLEMENTATION_PERMISSION = new SerializablePermission("enableSubclassImplementation")
         * SecurityManager用于检查调用方法的进程是否有权限去调用该方法
         * 序列化是SecurityManager检查调用权限的项目之一,此处不展开讨论其实现原理
         */
        ObjectOutput out = new ObjectOutputStream(new FileOutputStream(new File("a.txt")));
        
        /*
         * 该方法会向流中写入类的签名以及可序列化的属性(包括父类的)
         * 
         */
        out.writeObject(new HowSerializeWorks());
    }
}
