/*
ID: gireesh1
LANG: JAVA
TASK: holstein
*/
import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class holstein {
    public static String bestSeq = "";
    public static int[][] vitaminPerFeed;
    public static int[] minVitamin;
    public static int vitaminNum;
    public static int numOfFeed;
    public static void dfs(int[] vitRemain, String seq, int index) {
        if (seq.length() > bestSeq.length() && bestSeq.length() != 0) {
            return;
        }
        int[] vitSub = subt(vitRemain, vitaminPerFeed[index]);
        if (allNegative(vitSub)) {
            if (bestSeq.length() > seq.length() || bestSeq.length() == 0) {
                bestSeq = seq;
            } else if (bestSeq.length() == seq.length() && stringSum(seq) < stringSum(bestSeq)) {
                bestSeq = seq;
            }
        } else {
            for (int i = 0; i < numOfFeed; i++) {
                dfs(vitSub, seq + Integer.toString(i+1), i);
            }
        }
    }
    public static int stringSum(String s) {
        int sum = 0;
        for (int i = 0; i < s.length(); i++) {
            sum += Character.getNumericValue(s.charAt(i));
        }
        return sum;
    }
    public static int[] subt(int[] a, int[] b) {
        int[] neww = new int[a.length];
        if (a.length != b.length) {
            throw new ArrayIndexOutOfBoundsException();
        } else {
            for (int i = 0; i < a.length; i++) {
                neww[i] = a[i] - b[i];
            }
        }
        return neww;
    }
    public static boolean allNegative(int[] a) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] > 0) {
                return false;
            }
        }
        return true;
    }
    public static void main (String [] args) throws IOException {
        // Use BufferedReader rather than RandomAccessFile; it's much faster
        BufferedReader f = new BufferedReader(new FileReader("src/holstein.in"));
        // input file name goes above
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("holstein.out")));
        // Get line, brzeak into tokens

        vitaminNum = Integer.parseInt(f.readLine());
        minVitamin = new int[vitaminNum];
        StringTokenizer st = new StringTokenizer(f.readLine());
        for (int i = 0; i < vitaminNum; i++) {
            minVitamin[i] = Integer.parseInt(st.nextToken());
        }
        numOfFeed = Integer.parseInt(f.readLine());
        vitaminPerFeed = new int[numOfFeed][vitaminNum];
        for (int i = 0; i < numOfFeed; i++) {
            st = new StringTokenizer(f.readLine());
            for (int j = 0; j < vitaminNum; j++) {
                vitaminPerFeed[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        for (int i = 0; i < numOfFeed; i++) {
            dfs(minVitamin, Integer.toString(i+1), i);
        }
        int[] sequ = new int[bestSeq.length()];
        for (int i = 0; i < bestSeq.length(); i++) {
            sequ[i] = Character.getNumericValue(bestSeq.charAt(i));
        }
        Arrays.sort(sequ);
        out.print(bestSeq.length());
        for (int i = 0; i < sequ.length; i++) {
            out.print(" " + Integer.toString(sequ[i]));
        }
        out.println();
        out.close();
    }
}
