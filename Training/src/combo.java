/*
ID: gireesh1
LANG: JAVA
TASK: combo
*/
import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class combo {

    public static int distance(int i, int j, int numMax) {
        int max = Math.max(i,j);
        int min = Math.min(i,j);
        if (numMax - max >= 2 || min >= 3) {
            return max-min;
        } else {
            return Math.abs(numMax - max) + min;
        }
    }

    public static void main (String [] args) throws IOException {
        // Use BufferedReader rather than RandomAccessFile; it's much faster
        BufferedReader f = new BufferedReader(new FileReader("combo.in"));
        // input file name goes above
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("combo.out")));
        // Get line, break into tokens
        int numMax = Integer.parseInt(f.readLine());
        int[] johnCombo = new int[3];
        int[] masterCombo = new int[3];
        StringTokenizer st=new StringTokenizer(f.readLine());
        johnCombo[0] = Integer.parseInt(st.nextToken());
        johnCombo[1] = Integer.parseInt(st.nextToken());
        johnCombo[2] = Integer.parseInt(st.nextToken());
        st=new StringTokenizer(f.readLine());
        masterCombo[0] = Integer.parseInt(st.nextToken());
        masterCombo[1] = Integer.parseInt(st.nextToken());
        masterCombo[2] = Integer.parseInt(st.nextToken());
        if (numMax < 6) {
            out.println((int)Math.pow(numMax, 3));
        } else {
            int count = 125;
            for (int i = masterCombo[0]-2; i <= masterCombo[0]+2;i++) {
                for (int j = masterCombo[1]-2; j <= masterCombo[1]+2;j++) {
                    for (int k = masterCombo[2]-2; k <= masterCombo[2]+2;k++) {
                        if (distance((i%numMax),(johnCombo[0]),numMax) >= 3 || distance((j%numMax),(johnCombo[1]),numMax) >= 3 || distance((k%numMax),(johnCombo[2]),numMax) >= 3) {
                            count++;
                        }
                    }
                }
            }
            out.println(count);
        }
        out.close();

    }

}
