/*
ID: gireesh1
LANG: JAVA
TASK: castle
*/
import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;


/**
 * Created by Greenish on 12/26/2017.
 */
public class castle {

    private static int[][] dirs = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}}; // N,E,S,W
    private static int[][] floorPlan;
    private static int[][] componentPlan;
    private static int[] cmps;
    private static int maxSize = 0;
    private static int bestX = 0;
    private static int bestY = 0;
    private static int bestDir = 0;
    private static int width;
    private static int length;
    public static boolean wallExists(int n, int d) {
        switch (d) {
            case 0:
                return (n == 2 || n == 3 || n == 6 || n == 7 || n == 10 || n == 11 || n == 14 || n == 15);
            case 1:
                return ((n >= 4 && n <= 7) || (n >= 12));
            case 2:
                return (n >= 8);
            case 3:
                return (n % 2 != 0);
        }
        return false;
    }

    public static void dfs(int y, int x, int k) {
        for (int i = 0; i < dirs.length; i++) {
            int newY = y+dirs[i][0];
            int newX = x+dirs[i][1];
            if (newX < width && newY < length && newX >= 0 && newY >= 0) {
                if (!wallExists(floorPlan[x][y],i) && componentPlan[newX][newY] == 0) {
                    componentPlan[newX][newY] = k;
                    cmps[k]++;
                    dfs(newX, newY, k);
                } else if (wallExists(floorPlan[x][y],i) && componentPlan[x][y] != componentPlan[newX][newY]) {
                    int newSize = cmps[componentPlan[x][y]] + cmps[componentPlan[newX][newY]];
                    if (newSize > maxSize) {
                        maxSize = newSize;
                        bestX = x;
                        bestY = y;
                        bestDir = i;
                    } else if ( newSize == maxSize) {
                        if (y < bestY) {
                            bestX = x;
                            bestY = y;
                            bestDir = i;
                        } else if (y <= bestY && x > bestX) {
                            bestX = x;
                            bestY = y;
                            bestDir = i;
                        } else if (x == bestX && y == bestY && i > bestDir) {
                            bestX = x;
                            bestY = y;
                            bestDir = i;
                        }

                    }
                }
            }
        }
    }

    public static void main (String [] args) throws IOException {
        // Use BufferedReader rather than RandomAccessFile; it's much faster
        BufferedReader f = new BufferedReader(new FileReader("src/castle.in"));
        // input file name goes above
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("castle.out")));
        // Get line, brzeak into tokens
        StringTokenizer st = new StringTokenizer(f.readLine());
        length = Integer.parseInt(st.nextToken());
        width = Integer.parseInt(st.nextToken());
        floorPlan = new int[width][length];
        componentPlan = new int[width][length];
        cmps = new int[2501];
        boolean[] visited = new boolean[2501];
        int k = 0;
        for (int i = 0; i < width; i++) {
            st = new StringTokenizer(f.readLine());
            for (int j = 0; j < length; j++) {
                floorPlan[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                if (componentPlan[i][j] == 0) {
                    k++;
                    componentPlan[i][j] = k;
                    cmps[k]++;
                    dfs(i,j,k);
                }
            }
        }
        System.out.println(Arrays.deepToString(componentPlan));
        visited = new boolean[2501];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                if (!visited[componentPlan[i][j]]) {
                    dfs(i,j,k);
                    visited[componentPlan[i][j]] = true;

                }
            }
        }
        out.println(k);
        int maxRoom = 0;
        for (int i = 0; i < cmps.length; i++) {
            if (cmps[i] > maxRoom) {
                maxRoom = cmps[i];
            }
        }
        out.println(maxRoom);
        out.println(maxSize);
        String dire = "";
        switch(bestDir) {
            case 0:
                dire="W";
            case 1:
                dire="E";
            case 2:
                dire="S";
            case 3:
                dire="N";
        }
        bestX++;
        bestY++;
        out.println(bestX + " " + bestY + " " + dire);
        out.close();
    }
}
