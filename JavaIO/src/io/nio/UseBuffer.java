package io.nio;

import java.nio.ByteBuffer;

public class UseBuffer {
    
    //Buffer的底层实现基于数组,分配后大小不能改变
    //实际上allocate返回的是HeapByteBuffer(但HeapByteBuffer还是调用的ByteBuffer的构造器..)
    ByteBuffer buffer = ByteBuffer.allocate(1000);
    
    public void operate() {
        buffer.put((byte)1);
        /*
         * flip方法将limit设置为position所在位置
         * 并将position置0
         */
        buffer.flip();
        /*
         * clear()方法并没有把buffer清空,
         * 
         */
        buffer.clear();
    }
}
