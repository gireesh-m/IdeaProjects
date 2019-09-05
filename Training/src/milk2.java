/*
ID: gireesh1
LANG: JAVA
TASK: milk2
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

class milk2 {

    static class Time implements Comparable<Time> {
        int time;
        int enter;

        public int compareTo (Time t) {
            if (this.time==t.time) {
                return this.enter-t.enter;
            } else {
                return this.time - t.time;
            }
        }
    }

    public static void main (String[] args) throws IOException  {

        // Use BufferedReader rather than RandomAccessFile; it's much faster
        BufferedReader f = new BufferedReader(new FileReader("milk2.in"));
        // input file name goes above
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("milk2.out")));
        // Get line, break into tokens
        int numOfProbs = Integer.parseInt(f.readLine());
        Time[] times=new Time[numOfProbs*2];
        for (int i=0; i<numOfProbs; i++) {
            StringTokenizer st = new StringTokenizer(f.readLine());
            Time t = new Time();
            t.time = Integer.parseInt(st.nextToken());
            t.enter = 0;
            times[i*2] = t;

            t=new Time();
            t.time=Integer.parseInt(st.nextToken());
            t.enter=1;
            times[i*2+1] = t;
        }

        Arrays.sort(times);
        int currentCount=0;
        int lastEmptyTime=times[0].time;
        int lastHasFarmerTime=0;
        int maxEmpty=0;
        int maxFarmer=0;

        for (int i=0; i<times.length; i++) {
            if (times[i].enter==0) {
                if (currentCount==0) {
                    maxEmpty=Math.max(times[i].time-lastEmptyTime,maxEmpty);
                    lastHasFarmerTime=times[i].time;
                }
                currentCount++;
            } else {
                currentCount--;
                if (currentCount==0) {
                    maxFarmer=Math.max(times[i].time-lastHasFarmerTime, maxFarmer);
                    lastEmptyTime=times[i].time;
                }
            }
        }

        out.println(maxFarmer + " " + maxEmpty);
        out.close();
    }
}