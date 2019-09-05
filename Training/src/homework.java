
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class homework {



    public static void main (String [] args) throws IOException {
        // Use BufferedReader rather than RandomAccessFile; it's much faster
        BufferedReader f = new BufferedReader(new FileReader("homework.in"));
        // input file name goes above
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("homework.out")));
        // Get line, break into tokens
        int problems = Integer.parseInt(f.readLine());

        StringTokenizer st = new StringTokenizer(f.readLine());
        int[] scores = new int[problems];
        int sum = 0;
        for (int i = 0; i < problems; i++) {
            scores[i] = Integer.parseInt(st.nextToken());
            sum += scores[i];
        }

        int[] minValue = new int[problems];
        minValue[problems-1] = scores[problems-1];
        for (int i = problems - 2; i >= 0; i--) {
            minValue[i] = Math.min(minValue[i+1], scores[i]);
        }
        double maxScore = 0;
        ArrayList<Integer> lst = new ArrayList<>();
        for (int i = 1; i < problems-1; i++) {
            sum -= scores[i-1];
            double score = (double)(sum-minValue[i])/(problems-i-1);

            if (score > maxScore) {
                lst.clear();
                lst.add(i);
                maxScore = score;
            } else if (score == maxScore) {
                lst.add(i);
            }

        }
        if (!lst.isEmpty()) {
            for (int i = 0; i < lst.size(); i++) {
                out.println(lst.get(i));
            }
        }
        out.close();

    }

}
