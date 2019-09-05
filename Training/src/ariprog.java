/*
ID: gireesh1
LANG: JAVA
TASK: ariprog
*/

import java.io.*;
public class ariprog {

    public static void main (String [] args) throws IOException {
        // Use BufferedReader rather than RandomAccessFile; it's much faster
        BufferedReader f = new BufferedReader(new FileReader("ariprog.in"));
        // input file name goes above
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("ariprog.out")));
        // Get line, break into tokens
        int progressionLength = Integer.parseInt(f.readLine());
        int upperBound = Integer.parseInt(f.readLine());
        int[] bisquares = new int[2*(upperBound+1)*(upperBound+1)];
        for (int i = 0; i <= upperBound; i++) {
            for (int j = 0; j <= upperBound; j++) {
                bisquares[(i*i) + (j*j)] = 1;
            }
        }

        int maxB = (int)Math.ceil((double)((bisquares.length-1)/progressionLength));
        boolean solutionFound = false;
        for (int b = 1; b <= maxB; b++) {
            int startValeMax = (bisquares.length - 1) - progressionLength*b;
            for (int startValueIndex = 0; startValueIndex <= startValeMax; startValueIndex++) {
                if (bisquares[startValueIndex] == 1) {
                    boolean seriesExist = true;
                    for (int i = 0; i < progressionLength; i++) {
                        if (startValueIndex + (i * b) < bisquares.length && seriesExist) {
                            seriesExist = bisquares[startValueIndex + (i * b)] == 1 && seriesExist;
                        } else {
                            seriesExist = false;
                            break;
                        }
                    }
                    if (seriesExist) {
                        solutionFound = true;
                        out.println(startValueIndex + " " + b);
                    }
                }
            }
        }
        if (!solutionFound) {
            out.println("NONE");
        }
        out.close();
    }
}
