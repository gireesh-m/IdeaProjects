
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class measruemtntest {
    public static String whichCowsHaveMax(ArrayList<int[]> dayCount, int max, int index) {
        String s = "";
        for (int i = 0; i < dayCount.size(); i++) {
            if (dayCount.get(i)[index] == max) {
                s += Integer.toString(i);
            }
        }
        return s;
    }

    public static void main (String [] args) throws IOException {

        int[][] cowVals = new int[100][3]; // Bessie, Elsie, Mildred

        for (int i = 0; i < 100; i++) {
            cowVals[i][0] = 7;
            cowVals[i][1] = 7;
            cowVals[i][2] = 7;
        }
        ArrayList<String> distinctIds = new ArrayList<>();
        int TOTAL_DAYS = 1000000;
        int changes = 4;
        StringTokenizer st = new StringTokenizer("asdf asdf");
        String[] file = {"7 3 +3", "4 2 -1", "9 3 -1", "1 1 +2"};
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

        System.out.println(Arrays.toString(file));
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
        System.out.println(Arrays.deepToString(dayCount.toArray()));
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
        System.out.println(changesReq);
        System.out.close();

    }

}
