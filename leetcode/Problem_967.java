import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Problem_967 {
    
    private class TrieNode {
        public int digit;
        public List<TrieNode> children;

        public TrieNode(int digit) {
            this.digit = digit;
            this.children = new ArrayList<>();
        }
    }
    
    private ArrayList<Integer> answer;
    
    public int[] numsSameConsecDiff(int n, int k) {
        TrieNode root = new TrieNode(0);
        for (int i = 1; i < 10; i++) {
            TrieNode childNode = dfs(i, n, k);
            if (childNode != null) {
                root.children.add(childNode);
            }
        }    
        answer = new ArrayList<>();
        for (TrieNode node : root.children) {
            readTrie(node, new int[n], n-1);
        }
        return answer.stream().mapToInt(i -> i).toArray();
    }
    
    public void readTrie(TrieNode node, int[] currInts, int depth) {
        currInts[depth] = node.digit;
        if (depth == 0) {
            int answerInt = 0;
            for (int i = 0; i < currInts.length; i++) {
                answerInt += Math.pow(10,i) * currInts[i];
            }
            this.answer.add(answerInt);
        } else {
            for (TrieNode child : node.children) {
                readTrie(child, currInts, depth-1);
            }
        }
        
    }
    
    public TrieNode dfs(int digit, int depth, int k) {
        if (depth == 1) {
            return new TrieNode(digit);
        } else {
            TrieNode node = new TrieNode(digit);
            boolean foundSubnode = false;
            for (int i = 0; i < 10; i++) {
                if (Math.abs(i - digit) == k) {
                    TrieNode childNode = dfs(i, depth-1, k);
                    if (childNode != null) {
                        node.children.add(childNode);
                        foundSubnode = true;
                    }
                }
            }
            if (foundSubnode) {
                return node;
            } else {
                return null;
            }
        }
    }

    public static void main(String[] args) {
        Problem_967 sol = new Problem_967();
        System.out.println(Arrays.toString(sol.numsSameConsecDiff(3, 7)));
    }
    
}