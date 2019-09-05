/*
ID: gireesh1
LANG: JAVA
TASK: wormhole
*/
import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class wormhole {

    public static int numWormHole;
    public static int[] partner;
    public static int[] nextOnRight;

    public static boolean cycleExists() {
        for (int i = 1; i <= numWormHole; i++) {
            int pos = i;
            for (int count = 0; count < numWormHole; count++) {
                pos = nextOnRight[partner[pos]];
            }
            if (pos != 0) {
                return true;
            }
        }
        return false;
    }

    public static int solve() {
        int i;
        int total = 0;
        for (i = 1; i <= numWormHole; i++) {
            if (partner[i] == 0)
                break;
        }
        if (i >= numWormHole) {
            if (cycleExists()) {
                return 1;
            } else {
                return 0;
            }
        }
        for (int j = i+1; j <= numWormHole; j++) {
            if (partner[j] == 0) {
                partner[j] = i;
                partner[i] = j;
                total += solve();
                partner[i] = partner[j] = 0;
            }
        }
        return total;
    }
    public static void main (String [] args) throws IOException {
        // Use BufferedReader rather than RandomAccessFile; it's much faster
        BufferedReader f = new BufferedReader(new FileReader("wormhole.in"));
        // input file name goes above
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("wormhole.out")));
        // Get line, break into tokens
        numWormHole = Integer.parseInt(f.readLine());
        int[][] wormHoles = new int[numWormHole+1][2];
        for (int i = 1; i <= numWormHole; i++) {
            StringTokenizer st = new StringTokenizer(f.readLine());
            wormHoles[i][0] = Integer.parseInt(st.nextToken());
            wormHoles[i][1] = Integer.parseInt(st.nextToken());
        }
        partner = new int[numWormHole+1];
        nextOnRight = new int[numWormHole + 1];
        for (int i = 1; i <= numWormHole; i++) {
            for (int j = 1; j <= numWormHole; j++) {
                if (wormHoles[i][0] < wormHoles[j][0] && wormHoles[i][1] == wormHoles[j][1]) {
                    if (nextOnRight[i] == 0 ||
                            wormHoles[j][0] - wormHoles[i][0] <
                                    wormHoles[nextOnRight[i]][0] - wormHoles[i][0]) {
                        nextOnRight[i] = j;
                    }
                }
            }
        }
        out.println(solve());
        out.close();

    }

}
