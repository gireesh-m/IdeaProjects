/*
ID: gireesh1
LANG: JAVA
TASK: snowboots
*/
import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;
public class snowboots {
    public static int[] tiles;
    public static int[][] boots;
    public static int tileCount;


    public static void main (String [] args) throws IOException {
        // Use BufferedReader rather than RandomAccessFile; it's much faster
        BufferedReader f = new BufferedReader(new FileReader("snowboots.in"));
        // input file name goes above
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("snowboots.out")));
        // Get line, brzeak into tokens
        StringTokenizer st = new StringTokenizer(f.readLine());
        tileCount = Integer.parseInt(st.nextToken());
        int bootCount = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(f.readLine());
        tiles = new int[tileCount];
        int tileMax = 0;
        for (int i = 0; i < tileCount; i++) {
            tiles[i] = Integer.parseInt(st.nextToken());
            if (tiles[i] > tileMax) {
                tileMax = tiles[i];
            }
        }
        boots = new int[bootCount][2];
        boolean prevFail = false;
        for (int i = 0; i < bootCount; i++) {
            st = new StringTokenizer(f.readLine());
            boots[i][0] = Integer.parseInt(st.nextToken());
            boots[i][1] = Integer.parseInt(st.nextToken());
            if (boots[i][0] > tileMax) {
                out.println(1);
            } else if (prevFail && i >=1 && boots[i-1][0] >= boots[i][0] && boots[i-1][1] >= boots[i][1]) {
                out.println(0);
            } else {
                int[] tileMaxStreak = new int[tileCount];
                tileMaxStreak[0] = 0;
                boolean possilbe = true;
                for (int j = 0; j < tileCount; j++) {
                    if (tiles[j] > boots[i][0]) {
                        tileMaxStreak[j] = 1 + tileMaxStreak[j - 1];
                    }
                    if (tileMaxStreak[j] >= boots[i][1]) {
                        possilbe = false;
                        break;
                    }
                }
                if (possilbe) {
                    out.println(1);
                } else {
                    out.println(0);
                }
            }
        }
        out.close();
    }
}
