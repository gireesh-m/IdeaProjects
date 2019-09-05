/*
ID: gireesh1
LANG: JAVA
TASK: skidesign
*/
import java.io.*;
import java.util.Arrays;

public class skidesign {

    public static int sum(int[] stuff) {
        int sum = 0;
        for (int i : stuff) {
            sum += i;
        }
        return sum;
    }

    public static void findMaxIndex(int[] s) {}

    public static void main (String [] args) throws IOException {
        // Use BufferedReader rather than RandomAccessFile; it's much faster
        BufferedReader f = new BufferedReader(new FileReader("skidesign.in"));
        // input file name goes above
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("skidesign.out")));
        // Get line, break into tokens
        int numProb = Integer.parseInt(f.readLine());
        int[] hillHeights = new int[numProb];
        for (int i = 0; i < numProb; i++) {
            hillHeights[i] = Integer.parseInt(f.readLine());
        }
        Arrays.sort(hillHeights);
        int[] diff = new int[numProb];
        for (int i = 1; i < numProb; i++) {
            diff[i] = hillHeights[i] - hillHeights[i - 1];
        }
        while(sum(hillHeights) > 17) {

        }

        int minCost = 1000000000;
        for (int i = 0; i <= 83; i++) {
            int cost = 0;
            int x;
            for (int j = 0; j < numProb; j++) {
                if (hillHeights[j] < i) {
                    x = i - hillHeights[j];
                } else if (hillHeights[j] > i + 17) {
                    x = hillHeights[j] - i - 17;
                } else {
                    x = 0;
                }
                cost += x*x;
            }

            minCost = Math.min(minCost, cost);
        }
        out.println(minCost);
        out.close();

    }
}
