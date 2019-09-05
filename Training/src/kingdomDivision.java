import com.sun.org.apache.xalan.internal.xsltc.util.IntegerArray;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * Created by Greenish on 10/7/2017.
 */
public class kingdomDivision {

    public static int bfs(boolean[] visited, Queue<Integer> cityLinks) {
        return 0;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Queue<Integer> traverseOrder = new LinkedList<Integer>();
        Queue<Integer>[] cityLinks = new LinkedList[100001];
        for (int i = 0; i < cityLinks.length; i++) {
            cityLinks[i] = new LinkedList<Integer>();
        }
        int max = 0;
        while(in.hasNext()) {
            String input = in.nextLine();
            String[] inputAR = input.split(" -> ");
            int cityS = Integer.parseInt(inputAR[0]);
            int cityE = Integer.parseInt(inputAR[1]);
            cityLinks[cityS].add(cityE);
            max = Integer.max(max, cityE);
        }
        boolean[] visited = new boolean[max+1];
        //while (traverseOrder.add())
    }
}
