package ds.sort;
/*
 * 桶排序
 */
public class BucketSort {
	/*
	 * 桶排序的思想在于:
	 * 将待排序的序列尽量均匀的装入到一个"桶"序列中,
	 * 这就涉及到序列元素与桶之间的映射关系,
	 * 合理的构造映射关系是提升排序效率的关键.
	 * 之后对每个桶内的序列进行排序(比如采用插入排序)
	 * 最后合并所有已经排序完成的桶形成最终的有序序列
	 * 
	 * 在排序之前,需要知道其中元素的取值区间
	 */
	
	private class Node{
		int val;
		Node next;
		public Node(int val) {
			this.val = val;
		}
		public Node() {
		}
	}
	
	public void bucketSort(int[] arr, int max) {
		Node[] buckets = new Node[max/10];
		for (int i : arr) {
			Node new_node = new Node(i);
			int hash = hash(i);
			if(buckets[hash] == null) {
				//头节点放置空节点,也叫哨兵节点,便于后序遍历操作
				buckets[hash] = new Node();
				buckets[hash].next = new_node;
			}else {
				//将值插入到适当的位置(插入排序)
				Node node = buckets[hash];
				while(node.next != null && node.next.val < i) {
					node = node.next;
				}
				new_node.next = node.next;
				node.next = new_node;
				 
			}
		}
		//最后,将buckets中的所有元素取出,组成有序序列
		int i = 0;
		for(Node node : buckets) {
			if(node != null) {
				Node iNode = node.next;
				for( ; iNode  != null; iNode = iNode.next) {
					arr[i] = iNode.val;
					System.out.print(iNode.val + ", ");
					i++;
				}
			}
			System.out.println();
		}
	}
	
	private int hash(int val) {
		return val/10;
	}
	
	public static void main(String[] args) {
		int[] arr = {23, 12, 45, 31, 21, 18, 16, 24, 9, 1, 0, 36, 76, 43, 12, 66};
		BucketSort bucketSort = new BucketSort();
		bucketSort.bucketSort(arr, 80);
		for(int i = 0; i< arr.length; i++) {
			System.out.print(arr[i]+",");
		}
	}
/*
运行结果如下:
 0, 1, 9, 
12, 12, 16, 18, 
21, 23, 24, 
31, 36, 
43, 45, 

66, 
76, 
0,1,9,12,12,16,18,21,23,24,31,36,43,45,66,76,
*/

}
