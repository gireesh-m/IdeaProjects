/*
ID: gireesh1
LANG: JAVA
TASK: palsquare
*/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

class palsquare {

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
        BufferedReader f = new BufferedReader(new FileReader("palsquare.in"));
        // input file name goes above
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("palsquare.out")));
        // Get line, break into tokens
        StringTokenizer st = new StringTokenizer(f.readLine());

        int base = Integer.parseInt(st.nextToken());

        for (int i = 1;i<300;i++) {
            if (isPalnDrome(Integer.toString(i*i, base))) {
                out.println(Integer.toString(i, base).toUpperCase() + " " + Integer.toString(i*i, base).toUpperCase());
            }
        }
        out.close();
    }
}