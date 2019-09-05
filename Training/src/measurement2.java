
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class measurement2 {


    public static void main (String [] args) throws IOException {
        // Use BufferedReader rather than RandomAccessFile; it's much faster
        BufferedReader f = new BufferedReader(new FileReader("measurement.in"));
        // input file name goes above
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("measurement.out")));
        // Get line, break into tokens
        StringTokenizer st = new StringTokenizer(f.readLine());
        int changes = Integer.parseInt(st.nextToken());
        int startingGallons = Integer.parseInt(st.nextToken());
        ArrayList<String> distinctIds = new ArrayList<>();
        String[] file = new String[changes];
        for (int i=0; i < changes; i++) {
            file[i] = f.readLine();
        }
        ArrayList<int[]> dayCount = new ArrayList<>();
        Arrays.sort(file, new Comparator<String>() {
            @Override
            public int compare(String entry1, String entry2) {
                StringTokenizer st = new StringTokenizer(entry1);
                StringTokenizer st2 = new StringTokenizer(entry2);
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st2.nextToken());
                return a - b;
            }
        });

        for (int i=0; i < changes; i++) {
            st = new StringTokenizer(file[i]);
            int day = Integer.parseInt(st.nextToken());
            String cowName = st.nextToken();
            int cowId;
            if (distinctIds.contains(cowName)) {
                cowId = distinctIds.indexOf(cowName);
            } else {
                distinctIds.add(cowName);
                cowId = distinctIds.size() -1;
                dayCount.add(new int[changes]);
            }
            int change = Integer.parseInt(st.nextToken());
            for (int j = i; j < changes; j++) {
                dayCount.get(cowId)[j] += change;
            }
        }
        int max;
        String whichCowsHaveMax = "";
        for (int i = 0; i < distinctIds.size(); i++) {
            whichCowsHaveMax += Integer.toString(i);
        }
        String prevWhichCowsHaveMax = "";
        int changesReq = 0;
        for (int i = 0; i < changes; i++) {
            prevWhichCowsHaveMax = whichCowsHaveMax;
            max = 0;
            whichCowsHaveMax = "";
            for (int j = 0; j < distinctIds.size(); j++) {
                int num = dayCount.get(j)[i];
                if (num > max) {
                    max = num;
                    whichCowsHaveMax = Integer.toString(j);
                } else if (max == num) {
                    whichCowsHaveMax += Integer.toString(j);
                }
            }
            if (!whichCowsHaveMax.equals(prevWhichCowsHaveMax)) {
                changesReq++;
            }
        }
        out.println(changesReq);
        out.close();

    }

}
