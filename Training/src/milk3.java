/*
ID: gireesh1
LANG: JAVA
TASK: milk3
*/
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * Created by Greenish on 12/7/2017.
 */
public class milk3 {

    public static class Node {
        public int[] milk = new int[3];

        public Node(int a, int b, int c) {
            this.milk[0] = a;
            this.milk[1] = b;
            this.milk[2] = c;
        }

        public Node(Node n) {
            this.milk[0] = n.milk[0];
            this.milk[1] = n.milk[1];
            this.milk[2] = n.milk[2];
        }

    }

    private static Node pour(Node n, int start, int end, int endCap) {
        Node node = new Node(n);
        int pourVal = n.milk[start];
        if (pourVal > endCap - n.milk[end]) {
            pourVal = endCap - n.milk[end];
        }
        node.milk[start] = node.milk[start] - pourVal;
        node.milk[end] = node.milk[end] + pourVal;

        return node;
    }
    public static void main (String [] args) throws IOException {
        // Use BufferedReader rather than RandomAccessFile; it's much faster
        BufferedReader f = new BufferedReader(new FileReader("milk3.in"));
        // input file name goes above
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("milk3.out")));
        // Get line, break into tokens
        StringTokenizer st=new StringTokenizer(f.readLine());
        int[] milkValues = new int[3];
        int[][] pourDirections = {{0,1}, {0,2}, {1,0}, {1,2}, {2,0}, {2,1}};
        for (int i = 0; i < 3; i++) {
            milkValues[i] = Integer.parseInt(st.nextToken());
        }
        boolean [][][] flag = new boolean[21][21][21];
        ArrayList<Integer> lst = new ArrayList<Integer>();
        Stack<Node> stack = new Stack<Node>();
        stack.push(new Node(0,0,milkValues[2]));
        while (!stack.empty()) {
            Node n = stack.pop();
            if (n.milk[0] == 0 && !lst.contains(n.milk[2])) {
                lst.add(n.milk[2]);
            }
            flag[n.milk[0]][n.milk[1]][n.milk[2]] = true;
            Node otherN;

            for (int i = 0; i < pourDirections.length; i++) {
                otherN = pour(n, pourDirections[i][0], pourDirections[i][1], milkValues[pourDirections[i][1]]);
                if (!flag[otherN.milk[0]][otherN.milk[1]][otherN.milk[2]]) {
                    stack.push(otherN);
                }
            }
        }
        Collections.sort(lst);
        out.print(lst.get(0));
        for (int i = 1; i < lst.size(); i++) {
            out.print(" " + lst.get(i));
        }
        out.println();
        out.close();
    }
}
