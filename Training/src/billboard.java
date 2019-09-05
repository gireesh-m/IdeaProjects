
import java.io.*;
import java.util.StringTokenizer;

public class billboard {
    public static boolean inRange(int x, int y, int[] arr) {
        if (x < arr[2] && x >= arr[0] && y < arr[3] && y >= arr[1]) {
            return true;
        } else {
            return false;
        }
    }
    public static void main (String [] args) throws IOException {
        // Use BufferedReader rather than RandomAccessFile; it's much faster
        BufferedReader f = new BufferedReader(new FileReader("billboard.in"));
        // input file name goes above
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("billboard.out")));
        // Get line, break into tokens
        StringTokenizer st=new StringTokenizer(f.readLine());
        int[] bill1 = new int[4];
        for (int i = 0; i < 4; i++) {
            bill1[i] = Integer.parseInt(st.nextToken());
        }
        st=new StringTokenizer(f.readLine());
        int[] bill2 = new int[4];
        for (int i = 0; i < 4; i++) {
            bill2[i] = Integer.parseInt(st.nextToken());
        }
        st=new StringTokenizer(f.readLine());
        int[] truck = new int[4];
        for (int i = 0; i < 4; i++) {
            truck[i] = Integer.parseInt(st.nextToken());
        }

        int areaCountBill1 = 0;

        for (int x = 0; x < bill1[2]-bill1[0]; x++) {
            for (int y = 0; y < bill1[3]-bill1[1]; y++) {
                if (!inRange(x+bill1[0], y+bill1[1], truck)) {
                    areaCountBill1++;
                }
            }
        }
        int areaCountBill2 = 0;
        for (int x = 0; x < bill2[2]-bill2[0]; x++) {
            for (int y = 0; y < bill2[3]-bill2[1]; y++) {
                if (!inRange(x+bill2[0], y+bill2[1], truck)) {
                    areaCountBill2++;
                }
            }
        }
        out.println(areaCountBill1+areaCountBill2);
        out.close();

    }

}
