/*
ID: gireesh1
LANG: JAVA
TASK: dualpal
*/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

class dualpal {

    private static boolean isPalnDrome(String number) {
        int length = number.length();
        for (int i=0;i<length/2;i++) {
            if (number.charAt(i) != number.charAt(length-1-i)) {
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args) throws IOException {
        // Use BufferedReader rather than RandomAccessFile; it's much faster
        BufferedReader f = new BufferedReader(new FileReader("dualpal.in"));
        // input file name goes above
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("dualpal.out")));
        // Get line, break into tokens
        StringTokenizer st = new StringTokenizer(f.readLine());

        int n = Integer.parseInt(st.nextToken());
        int s = Integer.parseInt(st.nextToken());

        int total = 0;
        for (int i = s+1;total<n;i++) {
            int palindromeCount = 0;
            for (int base=2;base <= 10;base++) {
                if (isPalnDrome(Integer.toString(i, base))) {
                    palindromeCount++;
                    if (palindromeCount > 1) {break;}
                }
            }
            if (palindromeCount > 1) {
                total++;
                out.println(i);
            }
        }
        out.close();
    }
}