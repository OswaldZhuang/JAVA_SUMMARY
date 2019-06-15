package leetcode.Tree;

import java.util.HashMap;
import java.util.Map;

public class ConstructTree {

    public class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
   }

    /**
     * 利用前序遍历和中序遍历还原二叉树
     */
    private int[] preo;
    private int[] ino;
    private int root_p = 0;//前序根节点位置

    private Map<Integer, Integer> map = new HashMap<>();

    private TreeNode consTree(int left, int right){

        if(left == right) return null;

        int index = map.get(preo[root_p]);//中序的根节点位置

        //根位置的下一个值一定是左子树的根位置（前序遍历）
        root_p ++;

        TreeNode n = new TreeNode(ino[index]);

        //此处的并不是index - 1而是index
        n.left = consTree(left, index);
        n.right = consTree(index + 1, right);

        return n;
    }
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if(preorder.length == 0 || inorder.length == 0) return null;

        preo = preorder;
        ino = inorder;

        for(int i = 0 ; i < ino.length; i ++){
            map.put(ino[i], i);
        }
        //此处传入的right为ino.length
        return consTree(0, ino.length);
    }

}
