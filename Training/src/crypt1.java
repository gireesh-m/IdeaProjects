/*
ID: gireesh1
LANG: JAVA
TASK: crypt1
*/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

class crypt1 {

    public static boolean isNumGood(int check, int length, boolean [] flag) {
        if ((int) Math.log10(check) + 1 > length) {
            return false;
        }
        length--;
        for (int i = length; i >= 0; i--) {
            int divisor = (int) Math.pow(10, i);
            int digit = check/divisor;
            if (!flag[digit]) {
                return false;
            } else {
                // %= divsor..?
                check -= (digit * divisor);
            }
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader f = new BufferedReader(new FileReader("crypt1.in"));
        int numOfNumers=Integer.parseInt(f.readLine());
        boolean[] allowedNumbersFlag=new boolean[10];
        int[] allowedNumbersList = new int[numOfNumers];
        StringTokenizer st=new StringTokenizer(f.readLine());

        for (int i=0;i<numOfNumers;i++) {
            int temp = Integer.parseInt(st.nextToken());
            allowedNumbersFlag[temp] = true;
            allowedNumbersList[i] = temp;
        }


        int onesPlace;
        int tensPlace;
        int hundredsPlace;

        int topRowOptionsIndex = 0;
        int bottomRowOptionsIndex = 0;
        int [] topRowOptions=new int [numOfNumers*numOfNumers*numOfNumers];
        int [] bottomRowOptions=new int [numOfNumers*numOfNumers];

        for (int firstNumCount=0;firstNumCount<numOfNumers;firstNumCount++) {
            onesPlace=allowedNumbersList[firstNumCount];
            for (int secondNumCount=0;secondNumCount<numOfNumers;secondNumCount++) {
                tensPlace=allowedNumbersList[secondNumCount]*10;
                for (int thirdNumCount=0;thirdNumCount<numOfNumers;thirdNumCount++) {
                    hundredsPlace=allowedNumbersList[thirdNumCount]*100;
                    topRowOptions[topRowOptionsIndex]=onesPlace+tensPlace+hundredsPlace;
                    topRowOptionsIndex++;
                }
            }
        }
        for (int firstNumCount=0;firstNumCount<numOfNumers;firstNumCount++) {
            onesPlace=allowedNumbersList[firstNumCount];
            for (int secondNumCount=0;secondNumCount<numOfNumers;secondNumCount++) {
                tensPlace=allowedNumbersList[secondNumCount]*10;
                bottomRowOptions[bottomRowOptionsIndex]=onesPlace+tensPlace;
                bottomRowOptionsIndex++;
            }
        }
        int partialProduct1;
        int partialProduct2;
        int totalSolutions=0;
        for (int i = 0; i < topRowOptionsIndex; i++) {
            for (int j = 0; j < bottomRowOptionsIndex; j++) {
                //ones digit
                int temp = bottomRowOptions[j]-(bottomRowOptions[j]/10)*10;
                partialProduct1 = topRowOptions[i]*temp;
                //tens digit
                temp = bottomRowOptions[j]/10;
                partialProduct2 = topRowOptions[i]*temp;
                int result = (partialProduct2*10)+partialProduct1;
                if (isNumGood(partialProduct1,3,allowedNumbersFlag) && isNumGood(partialProduct2,3,allowedNumbersFlag) && isNumGood(result,4,allowedNumbersFlag)) {
                    totalSolutions++;
                }
            }
        }
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("crypt1.out")));
        out.println(totalSolutions);
        out.close();
    }

}