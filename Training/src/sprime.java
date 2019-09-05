/*
ID: gireesh1
LANG: JAVA
TASK: sprime
*/
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;


/**
 * Created by Greenish on 12/26/2017.
 */
public class sprime {

    public static boolean isPrime(int n) {
        // fast even test.
        if(n > 2 && (n & 1) == 0)
            return false;
        // only odd factors need to be tested up to n^0.5
        for(int i = 3; i * i <= n; i += 2)
            if (n % i == 0)
                return false;
        return true;
    }



    public static void main (String [] args) throws IOException {
        // Use BufferedReader rather than RandomAccessFile; it's much faster
        BufferedReader f = new BufferedReader(new FileReader("sprime.in"));
        // input file name goes above
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("sprime.out")));
        // Get line, brzeak into tokens
        int length = Integer.parseInt(f.readLine());
        int[] others = {1,3,5,7,9};
        Queue<Integer> potentialSPrimes = new LinkedList<>();
        potentialSPrimes.add(2);
        potentialSPrimes.add(3);
        potentialSPrimes.add(5);
        potentialSPrimes.add(7);
        for (int i = 1; i < length; i++) {
            int queueSize = potentialSPrimes.size();
            for (int j = 0; j < queueSize; j++) {
                String start = Integer.toString(potentialSPrimes.remove());
                for (int k = 0; k < others.length; k++) {
                    int newStart = Integer.parseInt(start + others[k]);
                    if (isPrime(newStart)) {
                        potentialSPrimes.add(newStart);
                    }
                }
            }
        }
        for (int i : potentialSPrimes) {
            out.println(i);
        }

        out.close();
    }
}
