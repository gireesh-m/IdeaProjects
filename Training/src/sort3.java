/*
ID: gireesh1
LANG: JAVA
TASK: sort3
*/
import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class sort3 {
    public static void main (String [] args) throws IOException {
        // Use BufferedReader rather than RandomAccessFile; it's much faster
        BufferedReader f = new BufferedReader(new FileReader("sort3.in"));
        // input file name goes above
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("sort3.out")));
        // Get line, brzeak into tokens
        int length = Integer.parseInt(f.readLine());
        int[] medals = new int[length];
        for (int i = 0; i < length; i++) {
            int num = Integer.parseInt(f.readLine());
            medals[i] = num;
        }
        int[][] map = new int[4][4];
        int l = medals.length;
        int[] sorted = Arrays.copyOf(medals, l);
        Arrays.sort(sorted);
        for (int i = 0; i < l; i++) {
            int from = medals[i];
            int to = sorted[i];
            map[from][to]++;
        }
        int tot = 0;
        for (int from = 1; from < 4; from++) {
            for (int to = from + 1; to < 4; to++) {
                int exchange = Math.min(map[from][to], map[to][from]);
                tot += exchange;
                map[from][to] -= exchange;
                map[to][from] -= exchange;
            }
        }
        for (int from = 2; from < 4; from++) {
            tot += 2*map[1][from];
        }
        out.println(tot);
        out.close();
    }
}
