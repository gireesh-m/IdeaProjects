/*
ID: gireesh1
LANG: JAVA
TASK: transform
*/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

class transform {

    public static char [][] rotate270Degree (char [][] c) {
        char [][] toReturn = new char[c.length][c.length];
        int newX; int newY;
        for (int i = 0;i<c.length;i++) {
            for (int i2 = 0;i2<c[i].length;i2++) {
                newX = c[i].length-1-i2;
                newY = i;
                toReturn[newX][newY] = c[i][i2];
            }
        }
        return toReturn;
    }

    public static char [][]  rotate180Degree (char [][] c) {
        char [][] toReturn = new char[c.length][c.length];
        int newX;
        int newY;
        for (int i = 0;i<c.length;i++) {
            for (int i2 = 0;i2<c[i].length;i2++) {
                newX = c[i].length-1-i;
                newY = c[i].length-1-i2;
                toReturn[newX][newY] = c[i][i2];
            }
        }
        return toReturn;
    }

    public static char[][] rotate90Degree (char [][] c) {
        char [][] toReturn = new char[c.length][c.length];
        int newx;
        int newy;
        for (int i = 0;i<c.length;i++) {
            for (int i2 = 0;i2<c[i].length;i2++) {
                newx = i2;
                newy = c[i].length-1-i;
                toReturn[newx][newy] = c[i][i2];
            }
        }
        return toReturn;
    }

    public static char[][] reflection (char [][] c) {
        char [][] toReturn = new char[c.length][c.length];
        for (int i = 0;i<c.length;i++) {
            for (int i2 = 0;i2<c[i].length;i2++) {
                toReturn[i][i2] = c[i][i2];
            }
        }
        for (int i = 0;i<c.length;i++) {
            for (int i2 = 0;i2<c[i].length/2;i2++) {
                char temp = toReturn[i][i2];
                toReturn[i][i2] = toReturn[i][c.length-1-i2];
                toReturn[i][c.length-1-i2] = temp;
            }
        }
        return toReturn;
    }

    public static boolean compare (char [][] beg, char[][] target) {
        for (int i = 0;i<beg.length;i++) {
            for (int i2 = 0;i2<beg[i].length;i2++) {
                if (beg[i][i2] != target[i][i2]) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        // Use BufferedReader rather than RandomAccessFile; it's much faster
        BufferedReader f = new BufferedReader(new FileReader("transform.in"));
        // input file name goes above
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("transform.out")));
        // Get line, break into tokens
        int c = Integer.parseInt(f.readLine());
        char [][] startMatrix = new char[c][c];
        String s;
        for (int i = 0;i<c;i++) {
            s = f.readLine();
            for (int i2 = 0;i2<c;i2++) {
                startMatrix[i][i2] = s.charAt(i2);
            }
        }
        char [][] target = new char[c][c];
        for (int i = 0;i<c;i++) {
            s = f.readLine();
            for (int i2 = 0;i2<c;i2++) {
                target[i][i2] = s.charAt(i2);
            }
        }
        char [][] t90degree = rotate90Degree(startMatrix);
        if (compare(t90degree,target)) {
            out.println(1);
            out.close();
        }
        char [][] t180degree = rotate180Degree(startMatrix);
        if (compare(t180degree,target)) {
            out.println(2);
            out.close();
        }
        char [][] t270degree = rotate270Degree(startMatrix);
        if (compare(t270degree,target)) {
            out.println(3);
            out.close();
        }
        char [][] tReflex = reflection(startMatrix);
        if (compare(reflection(startMatrix),target)) {
            out.println(4);
            out.close();
        }
        t90degree = rotate90Degree(tReflex);
        if (compare(t90degree,target)) {
            out.println(5);
            out.close();
        }
        t180degree = rotate180Degree(tReflex);
        if (compare(t180degree,target)) {
            out.println(5);
            out.close();
        }
        t270degree = rotate270Degree(tReflex);
        if (compare(t270degree,target)) {
            out.println(5);
            out.close();
        }
        if (compare(startMatrix,target)) {
            out.println(6);
            out.close();
        }
        out.println(7);
        out.close();

    }

}