package ds.tree.non_binary_tree_series;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/*
 * B树
 * 类似于平衡二叉树(AVL树),与之不同的是B树属于多叉树(平衡多路查找树)
 * m阶B树特征如下:
 * 每个节点最多拥有m棵子树
 * 根节点至少拥有2棵子树(单根除外)
 * 除了根节点以外,每个分支节点至少有Ceil(m/2)棵子树(ceil表示向上取整)
 * 所有叶子节点都在同一层
 * 有k棵子树的节点存在k-1个关键码,并且关键码按照递增排序(并且叶子节点的关键码的值在父节点两个关键码的值的区间内)
 * 所有叶子节点具有相同的深度,即高度h(也说明B树是平衡的)
 */
public class BTree {
    
    /*
     * 关于磁盘读写:
     * 定位磁盘上的某个数据区需要经历以下步骤:
     * 1.移动"动臂"将磁头移动到所需的柱面上,(这也叫定位或查找),这段时间最长(因为是机械操作)
     * 2.根据盘面号指定盘面上的磁道
     * 3.转动磁盘,将指定块号的磁道段移动到磁头下
     * 因此,根据上述过程,缩短磁盘读写的有效方式是减小动臂的移动,即相关的数据放在同一柱面(或者磁道)
     * B树等数据结构的设计就是用来解决磁盘高效查找的问题
     */
	                                                      
    
    //节点的定义,节点存储在一个磁盘块上 
    class BTreeNode{
        boolean isRoot = false;
        //节点的父节点
        BTreeNode parent;
        //节点的子节点
        LinkedList<BTreeNode> children;
        //节点的关键字
        LinkedList<Integer> keys;
       }
    
    private BTreeNode root;
    
    public BTreeNode findKey(int key, BTreeNode node) {
    	
    	if(node == null) {
    		return null;
    	}else {
    		//比首节点小
    		if(key < node.keys.getFirst()) {
    			return findKey(key, node.children.getFirst()); 
    		}
    		//比尾节点大
    		if(key > node.keys.getLast()) {
    			return findKey(key, node.children.getLast());
    		}
    		//在两节点之间
    		for(int i = 0; i < node.keys.size() - 1; i++) {
    			int k = node.keys.get(i);
    			int k_next = node.keys.get(i + 1); 
    			if(k == key || k == k_next) {
    				return node;
    			}else if(k < key && k_next > key){
					return findKey(key, node.children.get(i + 1));
				}
    		}
    	}
    	return null;
    }
    
        public static void main(String[] args) {
			for(int i = 0 ; i< 9 ;i++) {
				System.out.println(i);
			}
		}

}
