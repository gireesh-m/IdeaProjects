/*
ID: gireesh1
LANG: JAVA
TASK: pprime
*/
import java.io.*;
import java.util.StringTokenizer;


/**
 * Created by Greenish on 12/26/2017.
 */
public class pprime {

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
        BufferedReader f = new BufferedReader(new FileReader("pprime.in"));
        // input file name goes above
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("pprime.out")));
        // Get line, brzeak into tokens
        StringTokenizer st = new StringTokenizer(f.readLine());
        String min = st.nextToken();
        String max = st.nextToken();
        int maxVal = Integer.parseInt(max);
        int minVal = Integer.parseInt(min);
        int minLength  = min.length();
        int maxLength = max.length();
        for (int length = minLength; length <= maxLength; length++) {
            int paliFirstHalfLength = (int)Math.ceil((double)length/2);
            int secondHalfLength = (int)Math.floor((double)length/2);

            int maxI = (int)Math.pow(10, paliFirstHalfLength);
            for (int i = (int)Math.pow(10, paliFirstHalfLength-1); i < maxI; i++) {
                String pali = Integer.toString(i);
                pali += new StringBuilder(pali.substring(0,secondHalfLength)).reverse().toString();
                int palindrome = Integer.parseInt(pali);
                if (palindrome > maxVal) {
                    break;
                } else if(palindrome >= minVal && isPrime(palindrome)) {
                    out.println(palindrome);
                }
            }
        }
        out.close();
    }
}
