package leetcode.Array;

public class Sum {


    //两数相加
    //给定一个数组和一个目标值，找出所有和为这个目标值的两个数
    public void twoSum(int[] a, int targ){
        //可以用一张表把那个数所需要的另一个数记录起来
        //然后查找数组中是否存在这个数
        //该解法存在的问题是：结果存在重复
        int k[] = new int[100]; //*假设数组中的数都小于100，且为正整数
        for (int i : a){
            k[targ - i] = i;
        }
        for(int m : a){
            if(k[m] != 0){
                System.out.printf("%s, %s \n", m, k[m]);
            }
        }
    }

    //三数相加
    //给定一个数组和一个目标值，找出三个数之和等于目标值
    public void threeSum(int[] a, int targ){
        //与两数之和类似


    }
}
