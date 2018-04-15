package ds.tree.non_binary_tree_series;

import java.util.ArrayList;
import java.util.List;

/*
 * B树
 * 类似于平衡二叉树(AVL树),与之不同的是B树属于多叉树(平衡多路查找树)
 * m阶B树特征如下:
 * 每个节点最多拥有m棵子树
 * 根节点至少拥有2棵子树(单根除外)
 * 除了根节点以外,每个分支节点至少有Ceil(m/2)棵子树(ceil表示向上取整)
 * 所有叶子节点都在同一层
 * 有k棵子树的节点存在k-1个关键码(或者说关键码的数量小于等于2t-1,t为B树的度),并且关键码按照递增排序
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
        //是否为根节点
        private boolean isRoot = false;
        //节点的父节点
        private BTreeNode parent;
        //节点的子节点
        private ArrayList<BTreeNode> children;
        //节点的关键字
        private int[] keys;
        
        public boolean isRoot() {
            return isRoot;
        }
        
        public void setRoot() {
            isRoot = true;
        }
        
        public int indexOfKey(int key) {
            //给出key,查找对应的索引
            //由于keys是有序的,因此可以有更好的搜索方法去查找关键字索引
            for(int i = 0; i < keys.length; i++) {
                if(key == keys[i]) {
                    return i;
                }
            }
            return -1;
        }
        
        public int shouldInsertIndex(int key) {
            //该方法名字叫做"应该插入的索引位置"
            //但实际上这个方法用于定位子节点位置,由于子节点的数目比关键字的数目多1
            //假如说当前节点的关键码是3,6,子节点分别为n1,n2,n3
            //现在要查找的个关键码为5,那么shouldInsertIndex(5)的返回值应该是1,
            //这样,向下查找节点的时候就应该选择n2,
            for(int i = 0;i < keys.length; i++) {
                if(keys[i] < key && keys[i+1] > key) {
                    return i+1;
                }
            }
            //表示keys中存在查找的值
            return -1;
        }
    }
    
    //查找关键码
    //@param node 开始查找的节点(一般是根节点)
    //@return 存在查找值的节点
    public BTreeNode findKey(int i, BTreeNode node) {
        
        int index = node.indexOfKey(i);
        //在该节点上未找到关键码
        if( index < 0) {
            List<BTreeNode> ch = node.children;
            if(ch != null)
                return findKey(i, ch.get(node.shouldInsertIndex(i)));
            return null;
        }
        return node;
    }
    
    //节点插入
    public void insertKey(int key,BTreeNode node) {
        //首先需要找到需要插入的节点
        //如果节点里的关键码不是满的(即关键码的数量小于m-1),那么直接将key加入到节点并且排序即可
        //如果关键码数量为m-1,那么先插入到这个节点当中,然后将这个节点按照中间关键字进行分裂,中间
        //关键字被移动到父节点,如果父节点也是满的,那么按照上述规则继续分裂
        //如果移动到根节点,根节点是满的,那么根节点分裂,树的高度增加1
        
    }
    

}
