package leetcode.string;

/**
 * 子序列问题
 */
public class SubString {
    /**
     * 最长无重复子序列的长度
     * 采用滑动窗口法
     * i，j两个指针，i指针不动，j指针前移，
     * 并将沿途的出现的字符保存起来，如果j移动过程中
     * 出现重复的字符，那么算出其长度并将i移动到j的位置，
     * 重复上述过程
     */
    public int maxUniqueSubStr(String str){
        int[] map = new int[256];
        int len = 0;
        for(int i = 0, j = 0; j < str.length(); j++){
            i = Math.max(map[str.charAt(j)], i);
            len = Math.max(len, j - i + 1);
            map[str.charAt(j)] = j + 1;
        }
        return len;
    }

    /**
     * 最长回文子串
     */
    public String longestPalindromicSubstring(String str){
        /**
         * 动态规划法
         * 如果s是回文字符串，那么 c + s + c（c表示一个字符）也是一个回文字符串
         * 单个字符是回文字符串
         * c + c是回文字符串（c是一个字符）
         */
        /**
         * i表示起始位置，j表示终止位置
         * 状态转移方程为：
         * f(i, j) == true; i == j
         * f(i, j) == true; j - i == 1 && str.charAt(i) == str.charAt(j)
         * f(i, j) == f(i + 1, j - 1) && (str.charAt(i) == str.charAt(j))
         */

        return null;
    }

    /**
     * 实现String#indexOf
     */
    public static int indexOf(String haystack, String needle) {
        if(needle == null || needle.length() == 0) return 0;

        char[] h = haystack.toCharArray();
        char[] n = needle.toCharArray();

        int h_i = 0;
        int n_i = 0;
        int last_index = 0;
        while(h_i < h.length && n_i < n.length){
            if(h[h_i] == n[n_i]){
                if(h[h_i] == n[0] && last_index != 0) last_index = h_i;
                h_i ++;
                n_i ++;
            }else{
                if(last_index != 0) {
                    h_i = last_index;
                    last_index = 0;
                }
                else h_i ++;
                n_i = 0;
            }
        }
        if(n_i == n.length) return (h_i - n.length);
        return -1;
    }

    public static void main(String[] args) {
        indexOf("mississippi", "issipi");
    }
}
