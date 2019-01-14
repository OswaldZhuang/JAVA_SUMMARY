package ds.sort;

/**
 * 堆排序
 * 小根堆：每个节点的值不大于子节点的值
 * 大根堆：每个节点的值不小于子节点的值
 */
public class HeapSort {

    private int[] a = new int[]{ 1, 3, 4, 5, 2, 6, 9, 7, 8, 0};
    /**
     * 对于小根堆，比如说：
     *              3
     *            /  \
     *           8   15
     *         /  \
     *        31  25
     * 其数组中的存放形式为 [3，8，15，31，25]
     *
     * 如果当前元素在数组中以r[i]表示，那么其左子节点为r[2*i+1]，其右子节点为r[2*i+2],
     *
     * 大根堆反之亦然
     */

    /**
     * 堆排序的第一步是构造初始根堆
     * 形象的说就是先将一个无序的序列转化为一个二叉树，然后调整二叉树使之符合小（大）根堆的要求
     * 而调整的方法为从序列最后一个非叶子节点元素开始遍历（并且只找非叶子节点），将其与其子节点比较，如果不符合条件，那么交换
     * n表示传入的数组长度，i表示树的根节点
     */
    public void constructBigHeap(int[] m, int n, int i) {
        //此处构造大根堆

        //首先认为根节点就是最大值
        int largest = i;

        int right = 2*i + 2;
        int left  = 2*i + 1;

        if(right < n && m[right] > m[largest]){
            largest = right;
        }

        if(left < n && m[left] > m[largest]){
            largest = left;
        }

        if(largest != i){
            int tmp = a[largest];
            a[largest] = a[i];
            a[i] = tmp;
            //递归的将子树构造为大根堆
            constructBigHeap(m, n, largest);
        }

    }

    /**
     * 第二步即开始排序
     * 排序的思路为将序列最后一个元素与第一个元素做比较，如果符合条件则输出，否则交换并输出
     * 剔除序列的最后一个元素形成新的序列，采用第一步的方法调整这个序列，调整完成之后再重复上述步骤
     */
    public void heapSort(){
        int n = a.length;
        //实际上该方法包含第一步的内容
        for(int i = (n/2 - 1); i >= 0; i--){
            constructBigHeap(a, n, i);
            printArr();
        }

        for(int j = n - 1; j >= 0; j--){
            int tmp = a[0];
            a[0] = a[j];
            a[j] = tmp;
            constructBigHeap(a, j, 0);
            printArr();
        }
    }

    public void printArr(){
        for(int i = 0 ; i < a.length; i++){
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        HeapSort sort = new HeapSort();
        sort.heapSort();
    }
}
/**
 * 执行结果为：
 * 1 3 4 5 2 6 9 7 8 0
 * 1 3 4 8 2 6 9 7 5 0
 * 1 3 9 8 2 6 4 7 5 0
 * 1 8 9 7 2 6 4 3 5 0
 * 9 8 6 7 2 1 4 3 5 0
 * 8 7 6 5 2 1 4 3 0 9
 * 7 5 6 3 2 1 4 0 8 9
 * 6 5 4 3 2 1 0 7 8 9
 * 5 3 4 0 2 1 6 7 8 9
 * 4 3 1 0 2 5 6 7 8 9
 * 3 2 1 0 4 5 6 7 8 9
 * 2 0 1 3 4 5 6 7 8 9
 * 1 0 2 3 4 5 6 7 8 9
 * 0 1 2 3 4 5 6 7 8 9
 * 0 1 2 3 4 5 6 7 8 9
 */
