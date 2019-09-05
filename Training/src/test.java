import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Greenish on 11/22/2017.
 */
public class test {

    public static void main(String[] args) {
        int[][] hi = {{1,2},{0,1},{2,1}};
        java.util.Arrays.sort(hi, new java.util.Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                return Integer.compare(b[0], a[0]);
            }
        });
        System.out.println(Arrays.deepToString(hi));

        //int[] yo = hi - by;

//        Arrays.parallelSort(st);
//        System.out.println(Arrays.toString(st));
    }

}
