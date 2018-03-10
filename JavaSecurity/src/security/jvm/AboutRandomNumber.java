package security.jvm;

public class AboutRandomNumber {
    /*
     * JDK中的随机数通过java.security.SecureRandom实现
     * 所依赖的算法为SHA1PRNG
     * 实际上,该算法提供者在底层是依赖于操纵系统提供的随机数
     * linux中,与之相关的是/dev/random和/dev/urandom
     * 其中:
     * /dev/random是一个阻塞的发生器,它返回小于熵池噪声总数的随机字节
     * 其可生成高随机性的公钥或一次性密码本,若熵池空了,则对/dev/random
     * 的读操作将会被阻塞,直到收集到了足够的环境噪声为止
     * /dev/urandom是一个非阻塞的发生器,dev/random的一个副本是/dev/urandom
     * 它会重复使用熵池中的数据以产生伪随机数
     * linux的随机数生成器采用SHA1算法
     * -Djava.security.egd表示熵收集守护进程(entropy gathering daemon)
     */

}
