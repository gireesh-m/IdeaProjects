
import java.io.*;
import java.util.Arrays;
import java.util.Stack;
import java.util.StringTokenizer;

public class suffle2 {
    public static int[] performShuffle(int[] cows, int[] shuffle) {
        int[] newCows = new int[shuffle.length];
        for (int i = 0; i < shuffle.length; i++) {
            if (cows[i] != 0 || newCows[shuffle[i]-1] == 0) {
                newCows[shuffle[i] - 1] = cows[i];
            }
        }
        return newCows;
    }
    public static void main (String [] args) throws IOException {
        // Use BufferedReader rather than RandomAccessFile; it's much faster
        BufferedReader f = new BufferedReader(new FileReader("shuffle.in"));
        // input file name goes above
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("shuffle.out")));
        // Get line, break into tokens
        int numCows = Integer.parseInt(f.readLine());
        // I keep forgetting to read more lines... ;(
        StringTokenizer st=new StringTokenizer(f.readLine());

        int[] cows = new int[numCows];
        for (int i = 1; i <= numCows; i++) {
            cows[i-1] = i;
        }
        int[] shuffle = new int[numCows];
        for (int i = 0; i < numCows; i++) {
            shuffle[i] = Integer.parseInt(st.nextToken());
        }
        int[] newCows = new int[numCows];
        int maxCount = 0;
        for (int i = 0; i < 1000; i++) {
            newCows = performShuffle(cows, shuffle);
            cows = newCows;
        }
        int count = 0;
        for (int j = 0; j < newCows.length; j++) {
            if(newCows[j] == 0) {
                count++;
            }
        }
        if (count > maxCount) {
            maxCount = count;
        }

        out.println(numCows-maxCount);
        out.close();

    }

}
