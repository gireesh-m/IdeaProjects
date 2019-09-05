/*
ID: zed.9er1
LANG: JAVA
TASK: friday
*/
import java.io.*;

public class friday {
    public static void main (String [] args) throws IOException {
        BufferedReader f = new BufferedReader(new FileReader("friday.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("friday.out")));

        int day = 2;
        int[] count = {0,0,0,0,0,0,0};


        int[] month = {31,28,31,30,31,30,31,31,30,31,30,31};
        int years = Integer.parseInt(f.readLine());


        int year = 1900;

        for (int i = 0; i < years; i++) {
            if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
                month[1] = 29;
            } else {
                month[1] = 28;
            }
            for (int j = 0; j < month.length; j++) {
                int curDate = 1;


                while (curDate <= month[j]) {
                    if (day == 7) {


                        day = 0;
                    }
                    if (curDate == 13) {
                        count[day]++;
                    }


                    curDate++;


                    day++;
                }
            }
            year++;


        }
        out.print(count[0]);

        for (int i = 1; i < count.length; i++) { out.print(" " + count[i]); }
        out.println();
        //c lose
        out.close();

    }

}
