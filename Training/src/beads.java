/*
ID: zed.9er1
LANG: JAVA
TASK: beads
*/


import java.io.*;

public class beads {
    public static int length = 0;
    public static int ci(int i) {
        if (i < 0) {
            if (Math.abs(i) > length) {
                return length + length + i;
            } else {
                return length + i;
            }
        } else if (i >= length) {
            if (i >= 2 * length) {
                return i - length - length;
            } else {
                return i - length;
            }
        } else {
            return i;
        }
    }


    public static void main (String [] args) throws IOException {
        BufferedReader f = new BufferedReader(new FileReader("beads.in"));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("beads.out")));
        length = Integer.parseInt(f.readLine());

        String ba = f.readLine();

        int size = 2;
        for (int i = 0; i < ba.length(); i++) {
            boolean[] visited = new boolean[length];
            visited[ci(i-1)] = true;
            // check left
            char start = ba.charAt(ci(i - 1));
            int j = 1;
            char curChar = ba.charAt(ci(i-1-j));
            while (curChar == start || start == 'w'|| curChar == 'w') {
                if (start == 'w' && curChar != 'w') {
                    start = curChar;
                }
                if (visited[ci(i-1-j)]) {
                    break;
                } else {
                    visited[ci(i - 1 - j)] = true;
                }
                j++;
                curChar = ba.charAt(ci(i-1-j));
            }
            int left = j;
            int right = 0;
            if (!visited[ci(i)]) {
                start = ba.charAt(ci(i));
                visited[ci(i)] = true;
                j = 1;
                curChar = ba.charAt(ci(i+j));

                while (curChar == start || start == 'w' || curChar == 'w') {
                    if (start == 'w' && curChar != 'w') {start = curChar;}

                    if (visited[ci(i+j)]) {break;} else {
                        visited[ci(i+j)] = true;
                    }

                    j++;

                    curChar = ba.charAt(ci(i+j));
                }

                right = j;

            }
            if (left + right > size) {size = left + right;}
        }
        out.println(size);

        //c lose
        out.close();
    }
}
