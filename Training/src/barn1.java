/*
ID: gireesh1
LANG: JAVA
TASK: barn1
*/
import java.io.*;
import java.util.*;

public class barn1 {
    public static void main (String [] args) throws IOException {
        // Use BufferedReader rather than RandomAccessFile; it's much faster
        BufferedReader f = new BufferedReader(new FileReader("barn1.in"));
        // input file name goes above
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("barn1.out")));
        // Get line, break into tokens
        StringTokenizer st=new StringTokenizer(f.readLine());

        int numberOfBoards = Integer.parseInt(st.nextToken());
        int stalls = Integer.parseInt(st.nextToken());
        int occupiedStalls = Integer.parseInt(st.nextToken());
        int[] stallNumbers = new int[occupiedStalls];
        for (int i = 0; i < occupiedStalls; i++) {
            stallNumbers[i] = Integer.parseInt(f.readLine());
        }
        Arrays.sort(stallNumbers);
        int[] stallGap = new int[occupiedStalls - 1];
        for (int i = 0; i < occupiedStalls - 1; i++) {
            stallGap[i] = stallNumbers[i+1] - stallNumbers[i];
        }
        Arrays.sort(stallGap);
        int sum = Math.min(numberOfBoards, occupiedStalls);
        for (int i = 0; i <= stallGap.length - numberOfBoards; i++) {
            sum += stallGap[i];
        }
        out.println(sum);
        out.close();

    }

}
