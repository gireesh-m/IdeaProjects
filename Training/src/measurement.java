
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class measurement {


    public static void main (String [] args) throws IOException {
        // Use BufferedReader rather than RandomAccessFile; it's much faster
        BufferedReader f = new BufferedReader(new FileReader("measurement.in"));
        // input file name goes above
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("measurement.out")));
        // Get line, break into tokens
        StringTokenizer st = new StringTokenizer(f.readLine());
        int changes = Integer.parseInt(st.nextToken());
        int startingGallons = Integer.parseInt(st.nextToken());
        ArrayList<int[]> dayCount = new ArrayList<>();
        ArrayList<String> distinctIds = new ArrayList<>();
        int TOTAL_DAYS = 1000000;
        int maxDay = 0;
        for (int i=0; i < changes; i++) {
            st = new StringTokenizer(f.readLine());
            int day = Integer.parseInt(st.nextToken());
            if (day > maxDay) {
                maxDay = day;
            }
            String cowName = st.nextToken();
            int cowId;
            if (distinctIds.contains(cowName)) {
                cowId = distinctIds.indexOf(cowName);
            } else {
                distinctIds.add(cowName);
                cowId = distinctIds.size() -1;
                dayCount.add(new int[TOTAL_DAYS]);
            }
            int change = Integer.parseInt(st.nextToken());
            for (int j = day-1; j < TOTAL_DAYS; j++) {
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
        for (int i = 0; i < maxDay; i++) {
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
