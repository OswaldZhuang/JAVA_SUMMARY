package base.type;

/**
 * 一般类型
 */
public class Primitive {
    /**
     * java的一般类型有：
     * int 32bit
     * long 64bit
     * float 32bit
     * double 64bit
     * char 16bit
     */

    /**
     * 浮点数的表达形式为：
     * (-1)^s * 1.ddd···ddd * B^e
     * s为符号位
     * 1.ddd···ddd 为 尾数（尾数中数字的个数为精度）
     * B 为 基数（2进制则其为2）
     * e 为 指数（即小数点后面的数字的个数）（用移码表示，实际上真正在表示的时候还需要减去一个bias值，32位是127，64位是1023）
     */

    public void useDouble(){
        /**
         * 双精度浮点数：
         * 64 bit
         * 1 bit: 符号位
         * 11 bit：指数位
         * 52 bit：尾数（有效位数）
         * 因此双精度的浮点数表示为：
         * (-1)^s * (1+0.d52d51d50···d2d1d0) * 2^(2^(k10k9···k1k0) - 1023)
         */

        /**
         * 在Double中存在一些特殊的规则
         * 特殊数字的指数位全部为1
         *
         * NaN(not a number)
         * 0 (111，1111，1111)，(1000，0000，0000，···)
         *
         * positive infinity（正无穷）
         * 0 (111，1111，1111)，(0000，0000，0000，···)
         *
         * negative infinity（负无穷）
         * 1 (111，1111，1111)，(0000，0000，0000，···)
         *
         *
         */
    }

    public static void main(String[] args) {
        System.out.println(
                9999999999999999l - 9999999999999998l
        );
        System.out.println(9999999999999999.0);
        System.out.println(Double.toHexString(9999999999999999.0));//使用double会造成精度丢失，实际上该数为10^16
        System.out.println(9999999999999998.0);
        System.out.println(Double.toHexString(9999999999999998.0));

        System.out.println(Double.toHexString(0.0));
    }
}
