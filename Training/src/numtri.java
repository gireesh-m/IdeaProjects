/*
ID: gireesh1
LANG: JAVA
TASK: numtri
*/
import java.io.*;
import java.util.StringTokenizer;

public class numtri {
    public static void main (String [] args) throws IOException {
        // Use BufferedReader rather than RandomAccessFile; it's much faster
        BufferedReader f = new BufferedReader(new FileReader("numtri.in"));
        // input file name goes above
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("numtri.out")));
        // Get line, brzeak into tokens
        int size = Integer.parseInt(f.readLine());
        StringTokenizer st;
        int[][] graph = new int[size][size];
        for (int i = 0; i < size; i++) {
            st=new StringTokenizer(f.readLine());
            for (int j = 0; j < i+1; j++) {
                if (j < i + 1) {
                    graph[i][j] = Integer.parseInt(st.nextToken());
                }
            }
        }
        int[][] highestValue = new int[size][size];
        highestValue[0][0] = graph[0][0];
        for (int i = 1; i < size; i++) {
            for (int j = 0; j < i+1; j++) {
                if (j == 0) {
                    highestValue[i][0] = highestValue[i-1][0] + graph[i][0];
                } else if (j == i) {
                    highestValue[i][j] = highestValue[i-1][j-1] + graph[i][j];
                } else {
                    highestValue[i][j] = Math.max(highestValue[i-1][j], highestValue[i-1][j-1]) + graph[i][j];
                }
            }
        }
        int maxVal = 0;
        for (int j = 0; j < size; j++) {
            if (highestValue[size-1][j] > maxVal) {
                maxVal = highestValue[size-1][j];
            }
        }
        out.println(maxVal);
        out.close();

    }

}
