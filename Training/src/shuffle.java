
import java.io.*;
import java.util.StringTokenizer;

public class shuffle {
    public static int[] performShuffle(int[] cows, int[] shuffle) {
        int[] newCows = new int[shuffle.length];
        for (int i = 0; i < shuffle.length; i++) {
            newCows[shuffle[i]] = cows[i];
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
        int[] shuffle = new int[numCows];
        for (int i = 0; i < numCows; i++) {
            shuffle[i] = Integer.parseInt(st.nextToken());
        }
        st=new StringTokenizer(f.readLine());
        int[] cows = new int[numCows];
        for (int i = 0; i < numCows; i++) {
            cows[i] = Integer.parseInt(st.nextToken());
        }

        int[] newShuffle = new int[numCows];
        for (int i = 0; i < numCows; i++) {
            newShuffle[shuffle[i]-1] = i;
        }
        int[] finalCows = performShuffle(performShuffle(performShuffle(cows, newShuffle), newShuffle), newShuffle);
        for (int i = 0; i < finalCows.length; i++) {
            out.println(finalCows[i]);
        }
        out.close();

    }

}
