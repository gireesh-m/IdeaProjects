/*
ID: gireesh1
LANG: JAVA
TASK: namenum
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

class namenum {

    public static long convertMatrix(String s) {
        long result = 0;
        for (int i = 0;i<s.length();i++) {
            char curVal = s.charAt(i);
            if (curVal <= 'P') {
                result = result*10+(((s.charAt(i)-'A')/3)+2);
            } else {
                result = result*10+(((s.charAt(i)-'A'-1)/3)+2);
            }
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        // Use BufferedReader rather than RandomAccessFile; it's much faster
        BufferedReader f = new BufferedReader(new FileReader("namenum.in"));
        // input file name goes above
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("namenum.out")));
        // Get line, break into tokens
        long digits=Long.parseLong(f.readLine());
        f=new BufferedReader(new FileReader("dict.txt"));
        HashMap<Long,ArrayList<String>> map=new HashMap<>();
        String s;
        while ((s=f.readLine())!=null) {
            long value= convertMatrix(s);
            ArrayList<String> list;
            if (!map.containsKey(value)) {
                list=new ArrayList<String>();
                map.put(value, list);
            } else {
                list=map.get(value);
            }
            list.add(s);
        }

        if (map.containsKey(digits)) {
            StringBuilder sb=new StringBuilder();
            Iterator<String> it=map.get(digits).iterator();
            while (it.hasNext()) {
                sb.append(it.next());
                sb.append("\n");
            }
            s=sb.toString();
        } else {
            s="NONE\n";
        }
        out.print(s);
        out.close();
    }
}