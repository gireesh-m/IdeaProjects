/*
ID: gireesh1
LANG: JAVA
TASK: milk
*/
import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class milk {
    public static void main (String [] args) throws IOException {
        // Use BufferedReader rather than RandomAccessFile; it's much faster
        BufferedReader f = new BufferedReader(new FileReader("milk.in"));
        // input file name goes above
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("milk.out")));
        // Get line, break into tokens
        StringTokenizer st=new StringTokenizer(f.readLine());
        int milkNeeded = Integer.parseInt(st.nextToken());
        int numOfFarmers = Integer.parseInt(st.nextToken());
        int [] price = new int[numOfFarmers];
        int [] amount = new int[numOfFarmers];
        int totalCost = 0;
        for (int i = 0 ; i < numOfFarmers; i++) {
            st=new StringTokenizer(f.readLine());
            price[i]=Integer.parseInt(st.nextToken());
            amount[i]=Integer.parseInt(st.nextToken());
        }
        for (int i = 0; i < numOfFarmers; i++) {
            int lowestPriceIndex = i;
            for (int j = i+1; j < numOfFarmers; j++) {
                if (price[j] < price[lowestPriceIndex]) {
                    lowestPriceIndex = j;
                }
            }
            if (lowestPriceIndex!=i) {
                int priceTemp = price[lowestPriceIndex];
                int amountTemp = amount[lowestPriceIndex];
                price[lowestPriceIndex] = price[i];
                amount[lowestPriceIndex] = amount[i];
                price[i] = priceTemp;
                amount[i] = amountTemp;
            }
        }
        int i = 0;
        while (milkNeeded > 0 && i < numOfFarmers) {
            if (milkNeeded>=amount[i]) {
                milkNeeded-=amount[i];
                totalCost=totalCost+price[i]*amount[i];
            } else {
                totalCost=totalCost+price[i]*milkNeeded;
                milkNeeded=0;
            }
            i++;
        }
        out.println(totalCost);
        out.close();

    }

}
