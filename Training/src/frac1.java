import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Greenish on 1/31/2018.
 */
public class frac1 {

    public static int gcd(int num, int dem) {
        if (num == 0) return dem;
        else return gcd(dem, num % dem);
    }
    public static void main (String [] args) throws IOException {
        BufferedReader f = new BufferedReader(new FileReader("beads.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("beads.out")));
        ArrayList<frac> lst = new ArrayList<>();
        int length = Integer.parseInt(f.readLine());
        for (int i = 1; i <= length; i++) {
            for (int num = 1; num < i; num++) {
                if (gcd(num, i) == 1) {
                    lst.add(new frac(num, i));
                }
            }
        }
        Collections.sort(lst);
        for (frac fr : lst) {
            out.println(fr.num + "/" + fr.dem);
        }
        out.close();
    }

    private static class frac implements Comparable<frac> {
        int num;
        int dem;
        public frac(int num, int dem) {
            this.dem = dem;
            this.num = num;
        }

        @Override
        public int compareTo(frac that) {
            double thisVal = this.num/this.dem;
            double thatVal = that.num/that.dem;
            if (thisVal > thatVal) {
                return 1;
            } else if (thatVal > thisVal) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}
