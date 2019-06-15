package hugedata;

/**
 * 找相同的值
 */
public class Common {

    /**
     * 给定a、b两个文件，各存放50亿个url，每个url各占64字节，内存限制是4G，
     * 你找出a、b文件共同的url
     *
     * 5*10^3*10^3*10^3*64 = 320 G 因此内存一定不够
     * 那么可以采用分治的办法，将大文件划分为小文件，比如分成1000个小文件
     * 遍历文件中的url，对url取hash值，然后hash%1000 决定该url写到哪个文件中
     * 这样，a被分成了1000个文件，b被分成了1000个文件，这些文件是一一对应的关系（因为字符串相同，hash值必然相同）
     * 只要求出这些文件对中相同的url即可
     */

}
