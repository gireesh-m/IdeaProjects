import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by Greenish on 2/25/2018.
 */
public class dirtraverse {
    public static int maxLevel = 0;
    public static int[] levelDiff;
    public static int numFiles = 0;
    public static int numFolder = 0;
    public static ArrayList<Folder> foldersArray;
    public static ArrayList<Integer> folderCountPerLevel;

    public static Folder read(BufferedReader f, int id, int level, Folder parent) throws IOException  {
        StringTokenizer st = new StringTokenizer(f.readLine());
        String name = st.nextToken();
        int thing = Integer.parseInt(st.nextToken());
        if (level > maxLevel) {
            maxLevel = level;
        }
        if (thing == 0) {
            numFiles++;
            return new Folder(id, level, false, name.length(), parent);
        } else {
            numFolder++;
            folderCountPerLevel.set(level, folderCountPerLevel.get(level)+1);
            Folder folder = new Folder(id, level, true, name.length(), parent);
            folderCountPerLevel.add(0);
            for (int i = 0; i < thing; i++) {
                folder.addStuff(read(f, Integer.parseInt(st.nextToken()), level+1, folder));
            }
            foldersArray.add(folder);
            return folder;
        }

    }

    public static int countTotFiles (Folder currFolder) {
        int sum = 0;
        for (int i = 0; i < currFolder.fileId.size(); i++) {
            Folder f = currFolder.fileId.get(i);
            if (f.isFolder) {
                sum += countTotFiles(f);
            }
        }
        currFolder.numFile = sum + currFolder.numFile;
        return currFolder.numFile;
    }

    public static int count(Folder currFolder, int length) {
        int thisFolderLength = currFolder.length;
        if (currFolder.isFolder == false) {
            return length+currFolder.length;
        } else {
            int sum = 0;
            for (int i = 0; i < currFolder.fileId.size(); i++) {
                sum += count(currFolder.fileId.get(i), length+thisFolderLength+1);
            }
            return sum;
        }
    }

    public static void main (String [] args) throws IOException {
        // Use BufferedReader rather than RandomAccessFile; it's much faster
        BufferedReader f = new BufferedReader(new FileReader("dirtraverse.in"));
        // input file name goes above
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("dirtraverse.out")));
        // Get line, brzeak into tokens
        int num = Integer.parseInt(f.readLine());
        Folder bessie;
        foldersArray = new ArrayList<Folder>();

        folderCountPerLevel = new ArrayList<Integer>();
        folderCountPerLevel.add(0);
        bessie = read(f, 1, 0, null);
        int maxLenght = 0;
        int curLength = 0;
        int sum = count(bessie, 0);
        //sum -= 7*numFiles;
        int bestSum = 0;
        countTotFiles(bessie);
        for (int i = 0; i < numFolder; i++) {
            Folder currFolder = foldersArray.get(i);
            int curSum = sum;
            if (currFolder.id != 1) {
                curSum -= (currFolder.length + 1) * currFolder.numFile;
            }
            Folder parentFolder = currFolder.parent;
            while (parentFolder != null) {
                curSum -= (parentFolder.length+1)*currFolder.numFile;
                curSum += 3*(parentFolder.numFile-currFolder.numFile);
                parentFolder = parentFolder.parent;
            }
            if (curSum < bestSum || bestSum == 0) {
                bestSum = curSum;
            }
        }
        out.println(bestSum);
        out.close();

    }
    private static class Folder {
        int id, level, length;
        ArrayList<Folder> fileId;
        boolean isFolder;
        Folder parent;
        int numFile;
        int numFolder;
        public Folder(int id, int level, boolean isFolder, int length, Folder parent) {
            this.id = id;
            this.level = level;
            this.isFolder = isFolder;
            this.length = length;
            this.fileId = new ArrayList<Folder>();
            this.parent = parent;
            this.numFile = 0;
            this.numFolder = 0;
        }
        public void addStuff(Folder folder) {
            fileId.add(folder);
            if (folder.isFolder) {
                numFolder++;
            } else {
                numFile++;
            }
        }
    }
}
