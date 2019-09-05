/*
ID: gireesh1
LANG: JAVA
TASK: gift1
*/
import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class gift1 {
    public static void main (String [] args) throws IOException {
        // Use BufferedReader rather than RandomAccessFile; it's much faster
        BufferedReader f = new BufferedReader(new FileReader("gift1.in"));
        // input file name goes above
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("gift1.out")));
        // Get line, break into tokens
        int numProb = Integer.parseInt(f.readLine());
        Map<String, Integer> map = new LinkedHashMap<>();
        for (int i = 0; i < numProb; i++) {
            String name = f.readLine();
            map.put(name, 0);
        }
        for (int i = 0; i < numProb; i++) {
            String startPerson = f.readLine();
            StringTokenizer st = new StringTokenizer(f.readLine());
            int money = Integer.parseInt(st.nextToken());
            int numOfPeople = Integer.parseInt(st.nextToken());
            if (numOfPeople > 0) {
                int giveMoney = (int) money / numOfPeople;
                for (int j = 0; j < numOfPeople; j++) {
                    String givePerson = f.readLine();
                    int givePersonMoney = (int) map.get(givePerson);
                    map.put(givePerson, givePersonMoney + giveMoney);
                }
                int returMoney = money % numOfPeople;
                int startPersonMoney = (int) map.get(startPerson);
                map.put(startPerson, startPersonMoney + returMoney - money);
            }
        }
        for (String name: map.keySet()) {

            String key = name.toString();
            String value = map.get(name).toString();
            out.println(key + " " + value);
        }
        out.close();

    }

}
