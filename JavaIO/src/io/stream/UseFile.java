package io.stream;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class UseFile {
    
    File file = new File("C:/a.txt");
    
    public void use_file() throws IOException {
        
        //文件过滤器类FilenameFilter判断file是否符合相应条件
        file.list((dir, name)->name.endsWith(".java") || new File(name).isDirectory());
        
        //RandomAccessFile由于包含文件指针，因此其看可以访问文件的任意位置
        //r,只读模式
        //rw,读写模式
        //rwd,相比于rw，要求文件更新的内容要写到物理设备上
        //rws,相比于rw，要求文件更新的内容和元数据要写到物理设备上,(需要至少一次的底层IO操作)
        //注意,RandomAccessFile并不能向文件的任意位置插入内容,即使在任意位置
        //写入也会覆盖掉很后面的内容,正确的做法是先将插入位置后面的内容缓存起来,再进行插入,
        //最后在插入的内容后面追加缓存的内容
        RandomAccessFile r_file = new RandomAccessFile("C:/b.txt", "r");
        
        //定位文件指针
        r_file.seek(1000L);
        //返回文件指针位置
        long pos = r_file.getFilePointer();
        
        
    }
}
