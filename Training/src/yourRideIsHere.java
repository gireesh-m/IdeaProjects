/*
ID: zed.9er1
LANG: JAVA
TASK: ride
*/
import java.io.*;
import java.util.*;
class ride {


    public static void main (String [] args) throws IOException {

        BufferedReader f = new BufferedReader(new FileReader("ride.in"));

        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("ride.out")));

        String c = f.readLine();
        String g = f.readLine();
        int cometProd = (int)c.charAt(0) - 64;
        for (int i = 1; i < c.length(); i++) {
            cometProd *= (int) c.charAt(i) - 64;
        }
        int groupProd = (int)g.charAt(0) - 64;


        for (int i = 1; i < g.length(); i++) {
            groupProd *= (int) g.charAt(i) - 64;
        }


        int remainderComet = cometProd % 47;


        int remainderGroup = groupProd % 47;


        if (remainderComet == remainderGroup) {
            out.println("GO");
        } else {
            out.println("STAY");
        }
        out.close();

    }
}
