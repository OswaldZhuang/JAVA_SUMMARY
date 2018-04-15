package ds.tree.binary_tree_series;
/*
 * 二叉搜索树 Binary Sort Tree
 * 1.左子树所有节点的值小于根节点的值
 * 2.右子树所有节点的值大于根节点的值
 * 3.没有值相等的节点
 */
public class BSTree<T extends Comparable<T>> {
    
    class BSNode<T>{
         T key;
         BSNode<T> left;
         BSNode<T> right;
         BSNode<T> parent;
    }
    
    /*
     * 查找节点
     */
    public BSNode<T> search(BSNode<T> node, T key){
        while(node != null) {
            //当前节点的值比目标节点的值小
            if(node.key.compareTo(key) < 0) {
                node = node.right;
            }
            //当前节点的值比目标节点的值大
            else if(node.key.compareTo(key) > 0) {
                node = node.left;
            }
            //大小相同,则找到
            else {
                return node;
            }
        }
        //未找到,返回空
        return null;
    }
    /*
     * 二叉搜索树插入节点
     * bsn为给定的树的根节点,node为待插入节点
     */
    public void insert_node(BSNode<T> bsn, BSNode<T> node) {
        
        while(true) { 
            //当前位置的值比插入节点值大
            if(bsn.key.compareTo(node.key) > 0) {
                if(bsn.left == null) {
                    //插入到左节点
                    bsn.left = node;
                    node.parent = bsn;
                    return;
                }else {
                    bsn = bsn.left;
                }
            }
            //当前位置的值比插入节点值小
            else if(bsn.key.compareTo(node.key) < 0) {
                if(bsn.right == null) {
                    //插入到右节点
                    bsn.right = node;
                    node.parent = bsn;
                    return;
                }else {
                    bsn = bsn.right;
                }
            }
        }
        
    }
    /*
     * 二叉搜索树删除节点
     */
}
