package ds.sort;


/*
 * 归并排序
 */
public class MergeSort {
	/*
	 * 归并排序采用分而治之的思想:
	 * 分:将长序列二分为短序列,直到最终序列中只有1个元素或者2个元素,这样排序
	 * 会最简单
	 * 治:将已经排序好的两个序列合并为一个有序的序列,具体过程如下:
	 * 分别用游标i,j指向序列A,B的头个元素,比较A[i] 与B[j]大小
	 * 取小者放入新的数组中,并向后移动小者游标,按照上述步骤继续比较和
	 * 操作,直到所有游标遍历完序列所有元素
	 * 采用分治,最终得到有序序列
	 * 其时间复杂度为nlogn
	 */
	int[] arr;
	int[] temp;
	public void mergeSort(int[] arr, int left, int right) {
		//递归方法的终止条件就是left == right,则个时候,序列只有一个元素
		//于是,就可以执行merge操作,也就是说,整个递归终止于两个元素开始merge
		//比如:mergeSort(arr, 0, 0),mergeSort(arr, 1, 1)不会做任何操作,下一步执行merge(arr, 0, 0, 1)
		//而上述操作是mergeSort(arr, 0, 1)的一整步操作
		if(left < right) {
			int mid = (left + right)/2;
			mergeSort(arr, left, mid);
			mergeSort(arr, mid + 1, right);
			merge(arr, left, mid, right);
		}
		
	}
	
	//归并
	public void merge(int[] arr, int left, int mid, int right) {
		int i = left;
		int j = mid + 1;
		int t = 0;
		while(i <= mid && j <= right ) {
			if(arr[i] > arr[j]) {
				temp[t++] = arr[j++];				
			}else {
				temp[t++] = arr[i++];
			}
		}
		//如果j已经到了right而i还未到达mid,那么将i之后所有元素复制到t位置之后
		//(因为自序列已经有序)
		while(i <= mid) {
			temp[t++] = arr[i++];
		}
		//同上
		while(j <= right) {
			temp[t++] = arr[j++];
		}
		//将已经排好序的序列复制到原序列中
		//这是因为归并的时候需要前面归并好的数据,而数据源始终是arr
		//因此需要复制
		t = 0;
		while(left <= right){
            arr[left++] = temp[t++];
            System.out.printf("%s, ", temp[t-1]);
        }
		System.out.println();
		
	}
	
	public static void main(String[] args) {
		MergeSort ms = new MergeSort();
		int[] arr = {23, 12, 45, 31, 21, 18, 16, 24, 9, 1, 0, 36, 76, 43, 12, 66};
		int[] new_arr = new int[16];
		ms.arr = arr;
		ms.temp = new_arr;
		ms.mergeSort(arr, 0, 15);
		for(int i = 0; i< arr.length; i++) {
			System.out.print(arr[i]+",");
		}
	}
/*
* 执行结果为:
12, 23, 
31, 45, 
12, 23, 31, 45, 
18, 21, 
16, 24, 
16, 18, 21, 24, 
12, 16, 18, 21, 23, 24, 31, 45, 
1, 9, 
0, 36, 
0, 1, 9, 36, 
43, 76, 
12, 66, 
12, 43, 66, 76, 
0, 1, 9, 12, 36, 43, 66, 76, 
0, 1, 9, 12, 12, 16, 18, 21, 23, 24, 31, 36, 43, 45, 66, 76, 
0,1,9,12,12,16,18,21,23,24,31,36,43,45,66,76,
*/
	
}
