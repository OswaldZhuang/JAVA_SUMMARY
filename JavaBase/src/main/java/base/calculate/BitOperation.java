package base.calculate;

/*
 * 位运算
 * java中:
 * 0x代表16进制表示
 * 0b代表2进制表示 
 * 0代表8进制表示
 * 
 * 负数以补码的方式存在:
 * 比如: -5
 * 原码为:1000,0000,0000,0000,0000,0000,0000,0101
 * 反码为:1111,1111,1111,1111,1111,1111,1111,1010
 * 补码(反码+1)为:1111,1111,1111,1111,1111,1111,1111,1011
 */
public class BitOperation {
	
	public static void main(String[] args) {
		
		/*
		 * &:与运算
		 */
		int a = 0b101 & 0b0111;
		System.out.println(a);
		/*
		 * |:或运算
		 */
		int b = 0b0011 | 0b1100;
		System.out.println(b);
		/*
		 * ^:亦或运算
		 * 1^1 = 0
		 * 1^0 = 1
		 * 0^0 = 0
		 */
		int c = 0b110 ^ 0b111;
		System.out.println(c);
		/*
		 * ~:非运算
		 */
		int d = ~0b0011;
		System.out.println(d);
		/*
		 * <<:左移
		 */
		int e = 0b0001 << 2;
		System.out.println(e);
		/*
		 * >>:右移
		 */
		int f = 0b1000 >> 2;
		System.out.println(f);
		/*
		 * >>>:无符号右移,即连同符号位一起向右移动
		 */
		int g = -1 >>> 2;
		System.out.println(g);
	}

}
