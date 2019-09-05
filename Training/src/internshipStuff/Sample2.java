/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sample1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author milind
 */
public class Sample2 {
  
  public static void main(String[] args) throws FileNotFoundException, Exception {
//    BinarySearch.driver(args);
//    Sqrt.driver();
//    Rotate.driver();
//    StringInt.driver();
//    Utility.driver();
//    Tree.driver();
//    Graph.driver();
//    BinTree.driver();
//    StringUtil.driver();
//    Combinatorics.driver();
//    Utility.driveFibonacci(10);
//    WordBreaker.driver();
//    RodCut.driver();
//    LCS.driver();
//    EditDistance.driver();
//    Sort.driver();
//    PotsOfGold.driver();
//    RandomWalk.driver();
    Knapsack2.driver();
  }

  private static List<Integer> readInts(String file) throws FileNotFoundException {
    List<Integer> values = new ArrayList<Integer>();
    Scanner scanner = new Scanner(new FileReader(file));
    while (scanner.hasNextInt()) {
      values.add(scanner.nextInt());
    }
    return values;
  }
  
  static class BinarySearch {
    static class Result {
      int index;
      boolean found;

      public Result(int index, boolean found) {
        this.index = index;
        this.found = found;
      }
    }
    
    static void driver(String[] args) throws FileNotFoundException {
      List<Integer> values = readInts(args[0]);
      Scanner scanner = new Scanner(System.in);

      while (scanner.hasNextInt()) {
        int find = scanner.nextInt();
        Result result = BinarySearch.indexLower(values, find);
        System.out.printf("%d: %s at %d\n", find, result.found? "Found" : "Not found", result.index);
      }
    }

    static int index1(List<Integer> values, int key) {
      int lo = 0;
      int hi = values.size() - 1;
      
      while (lo <= hi) {
        int mid = (lo + hi) / 2;
        int midValue = values.get(mid);
        if (key < midValue) {
          hi = mid - 1;
        } else if (key > midValue) {
          lo = mid + 1;
        } else
          return mid;
      }
      return -1;
    }
    
    static Result indexUpper(List<Integer> values, int key) {
      int lo = 0;
      int hi = values.size() - 1;
      boolean found = false;
      
      while (lo < hi) {
        int mid = (lo + hi + 1) / 2;
        assert (mid > lo && mid <= hi);
        
        int midValue = values.get(mid);
        if (key >= midValue) {
          lo = mid;
        } else {
          hi = mid - 1;
        }
      }
      assert (values.isEmpty() || lo == hi);
      assert (values.isEmpty() || lo < values.size());
      assert (values.isEmpty() || (values.get(lo) <= key && (lo + 1 == values.size() || values.get(lo + 1) >= key)));
      assert (values.isEmpty() || (lo == 0 || values.get(lo - 1) < key));
      
      if (lo == hi && values.get(lo) == key)
        found = true;
      if (lo == hi && values.get(lo) < key) {
        ++lo;
        assert(lo == values.size());
      }
      return new Result(lo, found);
    }
    
    static Result indexLower(List<Integer> values, int key) {
      int lo = 0;
      int hi = values.size() - 1;
      boolean found = false;
      
      while (lo < hi) {
        int mid = (lo + hi) / 2;
        assert (mid >= lo && mid < hi);
        
        int midValue = values.get(mid);
        if (key <= midValue) {
          hi = mid;
        } else {
          lo = mid + 1;
        }
      }
      assert (values.isEmpty() || lo == hi);
      assert (values.isEmpty() || lo < values.size());
      assert (values.isEmpty() || (values.get(lo) <= key && (lo + 1 == values.size() || values.get(lo + 1) >= key)));
      assert (values.isEmpty() || (lo == 0 || values.get(lo - 1) < key));
      
      if (lo == hi && values.get(lo) == key)
        found = true;
      if (lo == hi && values.get(lo) < key) {
        ++lo;
        assert(lo == values.size());
      }
      return new Result(lo, found);
    }
  }
  
  static class Sqrt {
    static void driver() throws Exception {
      Scanner scanner = new Scanner(System.in);
      while (scanner.hasNextDouble()) {
        double input = scanner.nextDouble();
        System.out.printf("sqrt(%f) = %f\n", input, sqrt(input));
      }
    }
    
    static double sqrt1(double input) throws Exception {
      if (input == 0)
        return 0;
      if (input < 0)
        throw new Exception("Cannot handle negative input");
      
      double sqrt = input;
      double epsilon = 1e-7;
      while (Math.abs(input / sqrt - sqrt) > epsilon) {
        sqrt = (input / sqrt + sqrt) / 2.0;
      }
      return sqrt;
    }
    
    static double sqrt(double input) throws Exception {
      if (input == 0) {
        return 0;
      }
      if (input < 0) {
        throw new Exception("Cannot handle negative input");
      }

      double sqrt = input;
      double epsilon = 1e-7;
      while (Math.abs(input - sqrt * sqrt) > epsilon) {
        sqrt = sqrt + (input - sqrt * sqrt) / (2 * sqrt);
      }
      return sqrt;
    }
  }
  
  static class Rotate {
    static void driver() {
      int[] x = {1, 2, 3, 4, 5, 6, 7, 8};
      printArray(rotate(x, 2));
      int n = 4;
      int[] y = new int[n * n];
      fillArray(y, n, n);
      printArray(y, n, n);
      rotateRightSq(y, n);
      printArray(y, n, n);
    }
    
    static int[] rotate(int[] entries, int by) {
      int[] rotated = Arrays.copyOf(entries, entries.length);
      if (entries.length == 0) {
        return rotated;
      }
      by = by % entries.length;
      if (by == 0) {
        return rotated;
      }
      int numRotated = 0;
      int start = 0;
      while (numRotated < entries.length) {
        int i = start;
        int lastEntry = rotated[i];
        int n = entries.length;
        for (i = start; true; i = (i + by) % n) {
          int newPos = (i + by) % n;
          int temp = rotated[newPos];
          rotated[newPos] = lastEntry;
          lastEntry = temp;
          ++numRotated;
          if (newPos == start)
            break;
        }
        ++start;
      }
      return rotated;
    }
    
    static void rotateRightRect(int[] entries, int m, int n) {
      // doesn't work. non-trivial to do right per wikipedia
      assert (m >= 0 && n >= 0 && entries.length == m * n);
      if (m == 0)
        return;
      if (n == 0)
        return;
      if (m == 1 && n == 1)
        return;
      
      int numRotated = 0;
      int iS = 0, jS = 0;
      while (numRotated < m * n) {
        int i = iS, j = jS;
        int lastEntry = entries[index(i, j, m, n)];
        while(true) {
          int iNew = j, jNew = m - i - 1;
          int newPos = index(iNew, jNew, n, m);
          int temp = entries[newPos];
          entries[newPos] = lastEntry;
          lastEntry = temp;
          ++numRotated;
          i = row(newPos, m, n); 
          j = col(newPos, m, n);
          if (i == iS && j == jS)
            break;
        }
        ++jS;
        if (jS == n) {
          ++iS;
          jS = 0;
        }
      }
    }
    
    static int index(int i, int j, int m, int n) {
      int pos = i * n + j;
      assert i >= 0 && i < m && j >= 0 && j < n;
      assert pos >= 0 && pos < m * n;
      System.out.printf("%d, %d of (%d, %d) => %d\n", i, j, m, n, pos);
      return pos;
    }
    
    static int row(int newPos, int m, int n) {
      return newPos / n;
    }
    
    static int col(int newPos, int m, int n) {
      return newPos % n;
    }
    
    static void printArray(int[] x) {
      for (int x1 : x) {
        System.out.printf("%d ", x1);
      }
      System.out.println();
    }
    
    static void printArray(int[] x, int m, int n) {
      assert x.length == m * n;
      for (int i = 0; i < x.length; ++i) {
        System.out.printf("%d ", x[i]);
        if ((i + 1) % n == 0)
          System.out.println();
      }
    }

    private static void rotateRightSq(int[] x, int n) {
      if (n <= 1)
        return;
      int lo = 0, hi = n - 1;
      while (lo < hi) {
        for (int i = lo; i < hi; ++i) {
          int top = index(lo, i, n, n);
          int right = index(i, hi, n, n);
          int bottom = index(hi, hi - (i - lo), n, n);
          int left = index(hi - (i - lo), lo, n, n);
          
          int xTop = x[top];
          int xRight = x[right];
          int xBottom = x[bottom];
          int xLeft = x[left];
          
          x[right] = xTop;
          x[bottom] = xRight;
          x[left] = xBottom;
          x[top] = xLeft;
        }
        ++lo;
        --hi;
      }
    }

    private static void fillArray(int[] y, int m, int n) {
      for (int i = 0; i < m; ++i) {
        for (int j = 0; j < n; ++j) {
          y[i * n + j] = i * n + j;
        }
      }
    }
  }
  
  static class StringInt {
    String value;
    int base;
    
    StringInt(String value) {
      this.value = value;
      this.base = 10;
    }
    
    StringInt(String value, int base) {
      this.value = value;
      this.base = base;
    }
    
    void increment() {
      StringBuilder sb = new StringBuilder();
      
      if (value == null || value.isEmpty())
        value = "0";
      int carry = 1;
      for (int i = value.length() - 1; i >= 0; --i) {
        char ch = value.charAt(i);
        int digit = ch - '0';
        digit += carry;
        carry = digit / base;
        digit = digit % base;
        assert (digit >= 0 && digit <= base - 1);
        char newCh = (char) ('0' + digit);
        sb.append(newCh);
      }
      if (carry != 0)
        sb.append((char) ('0' + carry));
      value = sb.reverse().toString();
    }
    
    void add(String add) {
      StringBuilder sb = new StringBuilder();
      int lenCur = value.length(), lenAdd = add.length();
      int maxLen = Math.max(lenCur, lenAdd);
      int carry = 0;
      for (int i = 0; i < maxLen; ++i) {
        int digitCur = digitFromEnd(value, i);
        int digitAdd = digitFromEnd(add, i);
        int digitNew = digitCur + digitAdd + carry;
        carry = digitNew / 10;
        digitNew = digitNew % 10;
        sb.append((char) ('0' + digitNew));
      }
      if (carry != 0) {
        sb.append((char) ('0' + carry));
      }
      value = sb.reverse().toString();
    }
    
    static int digitFromEnd(String s, int indexFromEnd) {
      int len = s.length();
      if (indexFromEnd >= len)
        return 0;
      char ch = s.charAt(len - indexFromEnd - 1);
      return ch - '0';
    }
    
    static void driver() {
      driveInc("123");
      driveInc("999");
      driveInc("1999");
      
      driveAdd("123", "456");
      driveAdd("23", "456");
      driveAdd("123", "56");
      driveAdd("9990","10");
      driveAdd("10", "9990");
      driveAdd("29990","10");
      driveAdd("10", "29990");
    }
    
    static void driveInc(String in) {
      StringInt si = new StringInt(in);
      for (int i = 0; i < 9; ++i) {
        System.out.print(si.value + " + 1 = ");
        si.increment();
        System.out.print(si.value + "\n");
      }
    }
    
    static void driveAdd(String v1, String v2) {
      StringInt si = new StringInt(v1);
      si.add(v2);
      System.out.printf("%s + %s = %s\n", v1, v2, si.value);
    }
  }
  
  static class Utility {
    static void driver() {
//      int[] x = {1, 2, 3, 4, 5};
//      int[] y = {1, 2, 3, 4, 5, 6};
//      Rotate.printArray(x);
//      reverseInPlace(x);
//      Rotate.printArray(x);
//
//      Rotate.printArray(y);
//      reverseInPlace(y);
//      Rotate.printArray(y);
      
//      driveMerge(new int[] {1, 3, 5, 7}, new int[] {2, 4, 6});
//      driveMerge(new int[] {2, 4, 6}, new int[] {1, 3, 5, 7});
//      driveMerge(new int[] {1, 3, 5}, new int[] {6, 8, 10});
//      driveMerge(new int[] {6, 8, 10}, new int[] {1, 3, 5, 7});
//      driveMerge(new int[] {1, 1, 2, 2, 3}, new int[] {1, 1, 2, 2, 3});
      
//      driveFibonacci(10);
//      driveMaxSubSum();
//      driveHashTab();
//      shuffle(10);
      int[] x = new int[] {1, 4, 6, 9, 11};
      int[] y = new int[] {2, 3, 5, 7, 8, 10};
      for (int k = 0; k < x.length + y.length; k++) {
        driveKthFrom2(x, y, k);
      }
    }
    
    static void reverseInPlace(int[] x) {
      int first = 0, last = x.length - 1;
      while (first < last) {
        int temp = x[first];
        x[first] = x[last];
        x[last] = temp;
        ++first;
        --last;
      }
    }
    
    static void driveMerge(int[] x, int[] y) {
      Rotate.printArray(x);
      Rotate.printArray(y);
      System.out.print("Merge: ");
      Rotate.printArray(mergeSort(x, y));
      System.out.print("\n");
    }
    
    static int[] mergeSort(int[] x, int[] y) {
      int lenX = x.length;
      int lenY = y.length;
      int[] z = new int[lenX + lenY];
      
      int iX = 0, iY = 0, iZ = 0;
      
      while (true) {
        if (iX >= lenX && iY >= lenY)
          break;
        if (iX < lenX && iY < lenY) {
          if (x[iX] <= y[iY]) {
            // pick from x
            z[iZ++] = x[iX++];
          } else { /* x[iX] > y[iY] */
            z[iZ++] = y[iY++];
          }
        } else if (iX < lenX) {
          assert(iY >= lenY);
          z[iZ++] = x[iX++];
        } else {
          assert(iX >= lenX && iY < lenY);
          z[iZ++] = y[iY++];
        }
      }
      assert(iX == lenX && iY == lenY && iZ == lenX + lenY);
      return z;
    }
    
    static void driveFibonacci(int n) {
      for (int i = 0; i < n; i++) {
        System.out.printf("F(%d) = %d\n", i, fibonacciRec(i));
      }
    }
    
    static int fibonacci(int n) {
      if (n == 0)
        return 0;
      if (n == 1)
        return 1;
      int f1back = 1, f2back = 0;
      int fnValue = 0;
      for (int i = 2; i <= n; i++) {
        fnValue = f1back + f2back;
        f2back = f1back;
        f1back = fnValue;
      }
      return fnValue;
    }
    
    static int fibonacciRec(int n) {
      if (n == 0)
        return 0;
      if (n == 1)
        return 1;
      return fibonacciRec(n -1) + fibonacciRec(n - 2);
    }
    
    static class SumRange {
      int sum;
      int start;
      int end;
      
      SumRange(int sum, int start, int end) {
        this.sum = sum;
        this.start = start;
        this.end = end;
      }
    }
    
    static void driveMaxSubSum() {
      Random random = new Random();
      for (int i = 0; i < 200; i++) {
        int[] x = new int[150];
        for (int j = 0; j < x.length; j++) {
          x[j] = random.nextInt(10);
          if (random.nextInt(100) < 50)
            x[j] = -x[j];
        }
        maxSubSum(x);
      }
    }
    
    static SumRange maxSubSumBrute(int[] x) {
      int maxSum = 0, maxStart = 0, maxEnd = 0;
      for (int i = 0; i < x.length; i++) {
        for (int j = x.length; j > i; j--) {
          int curSum = 0;
          for (int k = i; k < j; k++) {
            curSum += x[k];
          }
          if (curSum >= maxSum) {
            maxSum = curSum;
            maxStart = i;
            maxEnd = j;
          }
        }
      }
      return new SumRange(maxSum, maxStart, maxEnd);
    }
    
    static void maxSubSum(int[] x) {
      int maxSum = 0, maxStart = 0, maxEnd = 0;
      int curSum = 0, curStart = 0;
      
      for (int i = 0; i < x.length; i++) {
        curSum += x[i];
        if (curSum > maxSum) {
          maxSum = curSum;
          maxStart = curStart;
          maxEnd = i + 1;
        }
        if (curSum <= 0) {
          curSum = 0;
          curStart = i + 1;
        }
      }
      
      if (curSum > maxSum) {
        maxSum = curSum;
        maxStart = curStart;
        maxEnd = x.length;
      }
//      Rotate.printArray(x);
//      System.out.printf("maxSum = %d: from %d to %d\n", maxSum, maxStart, maxEnd);
      SumRange sumRange = maxSubSumBrute(x);
      if (sumRange.sum != maxSum || sumRange.start != maxStart || sumRange.end != maxEnd) {
        System.out.printf("maxSum = %d: from %d to %d\n", maxSum, maxStart, maxEnd);
        System.out.printf("Mismatch: %d: from %d - %d\n", sumRange.sum, sumRange.start, sumRange.end);
      }
    }
    
    static void driveHashTab() {
      HashTab<String, Integer> ht = new HashTab<>();
      for (int i = 0; i < 1000; ++i) {
        ht.put(Integer.valueOf(i).toString(), i);
      }
      for (int i = 0; i < 1000; ++i) {
        int value = ht.get(Integer.valueOf(i).toString());
        if (value != i)
          System.out.printf("Mismatch: %d %d\n", value, i);
      }
      
      for (int i = 2000; i < 3000; ++i) {
        Integer value = ht.get(Integer.valueOf(i).toString());
        if (value != null)
          System.out.printf("Expect missing value for %d: instead %d\n", i, value);
      }
    }
    
    static void shuffle(int n) {
      int[] cards = new int[52];
      for (int i = 0; i < cards.length; i++) {
        cards[i] = i;
      }
      Random random = new Random();
      shuffleIter(cards, n, random);
      for (int i = 0; i < n; i++) {
        System.out.printf("%s\n", describeCard(cards[i]));
      }
    }
    
    static void shuffleRec(int[] cards, int i, int n, Random random) {
      if (n == 0)
        return;
      int cardSelect = random.nextInt(52 - i);
      cardSelect += i;
      assert(cardSelect >= i && cardSelect < 52);
      // swap with first allowed
      int cardTemp = cards[i];
      cards[i] = cards[cardSelect];
      cards[cardSelect] = cardTemp;
      shuffleRec(cards, i + 1, n - 1, random);
    }
    
    static void shuffleIter(int[] cards, int n, Random random) {
      assert(n <= 52);
      for (int i = 0; i < n; i++) {
        int cardSelect = random.nextInt(52 - i);
        cardSelect += i;
        assert(cardSelect >= i && cardSelect < 52);
        int cardTemp = cards[i];
        cards[i] = cards[cardSelect];
        cards[cardSelect] = cardTemp;
      }
    }
    
    static String describeCard(int iCard) {
      String[] suits = new String[] {"Clubs", "Diamonds", "Hearts", "Spades"};
      int iSuit = iCard / 13;
      int number = iCard % 13;
      String[] cardValueOver10 = new String[] {"Jack", "Queen", "King"};
      String cardValue = (number > 10)? cardValueOver10[number - 11] : Integer.valueOf(number).toString();
      return String.format("%s of %s", cardValue, suits[iSuit]);
    }
    
    static void driveKthFrom2(int[] x, int[] y, int k) {
      System.out.printf("k=%d\n", k);
      int[] z = mergeSort(x, y);
      int kth = kthFrom2(x, y, k);
      if (kth != z[k]) {
        System.out.printf("Mismatch: at %d: %d != %d\n", k, kth, z[k]);
        Rotate.printArray(x);
        Rotate.printArray(y);
      }
    } 
    
    // k: 0-based index
    static int kthFrom2(int[] x, int[] y, int k) {
      int nX = x.length;
      int nY = y.length;

      if (k >= nX + nY)
        return Integer.MAX_VALUE;
      
      if (nX == 0)
        return y[k];
      if (nY == 0)
        return x[k];
      
      return kthFrom2Rec(x, 0, Math.min(nX - 1, k), y, 0, Math.min(nY - 1, k), k);
    }
    
    static int kthFrom2Rec(int[] x, int sX, int eX, int[] y, int sY, int eY, int k) {
      assert (k >= 0 && k < eX - sX + 1 + eY - sY + 1) : String.format("%d : %d-%d %d-%d", k, sX, eX, sY, eY);
      assert(sX <= eX && sY <= eY);
      if (k < 2) {
        // termination
        // return one of the remaining values in O(1)
        int[] z = mergeSort(Arrays.copyOfRange(x, sX, Math.min(sX + 2, eX + 1)), 
                Arrays.copyOfRange(y, sY, Math.min(sY + 2, eY + 1)));
        return z[k];
      }
      int mX = Math.min(sX + (k + 1) / 2, eX);
      int mY = Math.min(sY + (k + 1) / 2, eY);
      int medianX = x[mX];
      int medianY = y[mY];
      
      // another termination condition
      if (medianX <= medianY) {
        if (mX == sX) {
          assert(sX == eX);
          return y[sY + k - 1];
        }
        return kthFrom2Rec(x, mX, eX, y, sY, eY, k - (mX - sX));
      }
      else {
        if (mY == sY) {
          assert (sY == eY);
          return x[sX + k - 1];
        }
        return kthFrom2Rec(x, sX, eX, y, mY, eY, k - (mY - sY));
      }
    }
  }
  
  static class Tree {
    
    static void driver() throws Exception {
      String treeSpec = "1 ( 2 ( 4 5 ) 3 ( 6 7 ) 8 9 10 )";
      Node root = buildTree(treeSpec);
      System.out.printf("%s => %s\n", treeSpec, root.describe());
      root.DFS();
      root.BFS2();
      root.postOrder();
    }
    
    static Node buildTree(String in) throws Exception {
      Scanner scanner = new Scanner(in);
      Node root = Node.readRoot(scanner);
      if (scanner.hasNext()) {
        System.err.print("Extra input after tree spec ignored\n");
      }
      return root;
    }
    
    static class Node {
      int value;
      List<Node> children;
      static final int special = -1;
      
      Node() {
        this.children = new ArrayList<Node>();
      }
      
      Node(int value) {
        this.value = value;
        this.children = new ArrayList<Node>();
      }
      
      void DFS() {
        this.DFS(0);
      }
      
      void DFS(int level) {
        System.out.printf("%s%d\n", indent(level), value);
        for (Node child : children) {
          child.DFS(level + 1);
        }
      }
      
      void BFS() {
        Queue<Node> q = new ArrayDeque<>();
        int curLevel = 1, maxLevel = 0;
        int curNodes = 0, maxNodes = 0;
        q.add(this);
        q.add(new Node(Node.special));
        while (!q.isEmpty()) {
          Node node = q.remove();
          if (node.value == Node.special) {
            if (curNodes > maxNodes) {
              maxNodes = curNodes;
              maxLevel = curLevel;
            }
            curLevel++;
            curNodes = 0;
            System.out.print("\n");
            if (q.peek() != null)
              q.add(new Node(Node.special));
          } else {
            curNodes++;
            System.out.printf("%d ", node.value);
            for (Node child : node.children) {
              q.add(child);
            }
          }
        }
        System.out.printf("%d nodes at level %d\n", maxNodes, maxLevel);
      }
      
      void BFS2() {
        int curLevel = 1, maxLevel = 0;
        int curNodes = 0, maxNodes = 0;
        
        Queue<Node> q = new ArrayDeque<>();
        q.add(this);
        while (true) {
          List<Node> nodes = new ArrayList<Node>();
          while (!q.isEmpty()) {
            Node node = q.remove();
            nodes.add(node);
          }
          curNodes = nodes.size();
          if (curNodes > maxNodes) {
            maxNodes = curNodes;
            maxLevel = curLevel;
          }
          curNodes = 0;
          curLevel++;
          assert(q.isEmpty());
          for (Node node : nodes) {
            System.out.printf("%d ", node.value);
            for (Node child : node.children) {
              q.add(child);
            }
          }
          System.out.printf("\n");
          if (q.isEmpty())
            break;
        }
        System.out.printf("%d nodes at level %d\n", maxNodes, maxLevel);
      }
      
      void preOrder() {
        System.out.printf("%d\n", value);
        for (Node child : children) {
          child.preOrder();
        }
      }
      
      void inOrder() {
        if (children.size() > 0)
          children.get(0).inOrder();
        System.out.printf("%d\n", value);
        for (int i = 1; i < children.size(); i++) {
          Node child = children.get(i);
          child.inOrder();
        }
      }
      
      void postOrder() {
        for (Node child : children) {
          child.postOrder();
        }
        System.out.printf("%d\n", value);
      }
      
      String describe() {
        StringBuilder sb = new StringBuilder();
        this.describe(sb);
        return sb.toString();
      }
      
      void describe(StringBuilder sb) {
        sb.append(" ").append(value);
        if (!children.isEmpty()) {
          sb.append(" (");
          for (Node child: children) {
            child.describe(sb);
          }
          sb.append(" )");
        }
      }
      
      static Node readRoot(Scanner scanner) throws Exception {
        Node node = null;
        if (scanner.hasNext()) {
          String token = scanner.next();
          node = new Node(Integer.parseInt(token));
          if (scanner.hasNext()) {
            token = scanner.next();
            if (!token.equals("(")) {
              throw new Exception(String.format("Unexpected token %s found after root node", token));
            }
            node.children = readNodes(scanner);
          }
        }
        return node;
      }
      
      static List<Node> readNodes(Scanner scanner) throws Exception{
        List<Node> nodes = new ArrayList<Node>();
        while (scanner.hasNext()) {
          String token = scanner.next();
          if (token.equals("(")) {
            if (nodes.isEmpty())
              throw new Exception("Children without parent found");
            List<Node> children = readNodes(scanner);
            nodes.get(nodes.size() - 1).children = children;
          } else  if (token.equals(")")) {
            return nodes;
          } else {
            int value = Integer.parseInt(token);
            Node node = new Node(value);
            nodes.add(node);
          }
        }
        System.err.printf("Missing ')'\n");
        return nodes;
      }
      
      static String indent(int level) {
        StringBuilder sb = new StringBuilder(level * 2);
        for (int i = 0; i < level; ++i) {
          sb.append("  ");
        }
        return sb.toString();
      }
    }
  }
  
  static class BinTree {
    
    static class Node {
      int value;
      Node left;
      Node right;
      Node parent;
      
      Node(int value) {
        this.value = value;
      }

      Node() {
      }
    
      String describe() {
        StringBuilder sb = new StringBuilder();
        describe(sb);
        return sb.toString();
      }
      
      void describe(StringBuilder sb) {
        sb.append(" ").append(value);
        if (left != null || right != null) {
          sb.append(" (");
          if (left == null)
            sb.append(" - ");
          else
            left.describe(sb);
          if (right == null)
            sb.append(" - ");
          else
            right.describe(sb);
          sb.append(" )");
        }
      }
      
      Node find(int key) {
        if (value == key)
          return this;
        Node foundLeft = (left == null)? null : left.find(key);
        if (foundLeft != null)
          return foundLeft;
        Node foundRight = (right == null)? null : right.find(key);
        if (foundRight != null)
          return foundRight;
        return null;
      }
      
      static class AncRes {
        boolean found1;
        boolean found2;
        Node common;
      }
      
      AncRes commonAncestorRec(Node node1, Node node2) {
        AncRes arLeft = (left == null)? new AncRes() : left.commonAncestorRec(node1, node2);
        if (arLeft.common != null)
          return arLeft;
        AncRes arRight = (right == null)? new AncRes() : right.commonAncestorRec(node1, node2);
        if (arRight.common != null)
          return arRight;
        
        boolean found1 = (this == node1);
        boolean found2 = (this == node2);

        AncRes arCur = new AncRes();
        arCur.found1 = (found1 || arLeft.found1 || arRight.found1);
        arCur.found2 = (found2 || arLeft.found2 || arRight.found2);
        if (arCur.found1 && arCur.found2)
          arCur.common = this;
        return arCur;
      }
      
      Node commonAncestor(Node node1, Node node2) {
        assert(node1 != null && node2 != null);
        AncRes ar = commonAncestorRec(node1, node2);
        return ar.common;
      }
      
      static class AR {
        Node common;
        int found;
      }
      
      // Works if we assume either of the 2:
      // 1. both nodes are gauranteed to be in the tree
      // 2. the nodes are not identical
      static Node commonAncestor(Node root, Node n1, Node n2) {
        if (root == null || n1 == null || n2 == null)
          return null;
        if (n1 == n2)
          return n1;
//        AR ar = caRec(root, n1, n2);
//        return ar.common;
        AncRes ar = Node.commonAncestorRec(root, n1, n2);
        return ar.common;
      }
       
      static AR caRec(Node root, Node n1, Node n2) {
        AR arRet = new AR();
        if (root == null)
          return arRet;
        AR arL = caRec(root.left, n1, n2);
        if (arL.found == 2)
          return arL;
        AR arR = caRec(root.right, n1, n2);
        if (arR.found == 2)
          return arR;
        int foundAtRoot = (root == n1) || (root == n2)? 1: 0;
        arRet.found = arL.found + arR.found + foundAtRoot;
        if (arRet.found >= 2) {
          arRet.found = 2;
          arRet.common = root;
        }
        return arRet;
      }
      
      static AncRes commonAncestorRec(Node root, Node node1, Node node2) {
        if (root == null)
          return new AncRes();
        AncRes arLeft = commonAncestorRec(root.left, node1, node2);
        if (arLeft.common != null) {
          return arLeft;
        }
        AncRes arRight = commonAncestorRec(root.right, node1, node2);
        if (arRight.common != null) {
          return arRight;
        }

        boolean found1 = (root == node1);
        boolean found2 = (root == node2);

        AncRes arCur = new AncRes();
        arCur.found1 = (found1 || arLeft.found1 || arRight.found1);
        arCur.found2 = (found2 || arLeft.found2 || arRight.found2);
        if (arCur.found1 && arCur.found2) {
          arCur.common = root;
        }
        return arCur;
      }
      
      static Node convert(Tree.Node tn, boolean flip) {
        Node bn = new Node();
        bn.value = tn.value;
        if (tn.children.size() > 0)
          bn.left = Node.convert(tn.children.get(0), flip);
        if (tn.children.size() > 1)
          bn.right = Node.convert(tn.children.get(1), flip);
        if (flip) {
          Node temp = bn.left;
          bn.left = bn.right;
          bn.right = temp;
        }
        if (bn.left != null)
          bn.left.parent = bn;
        if (bn.right != null)
          bn.right.parent = bn;
        return bn;
      }
      
      static void inorder(Node root) {
        if (root == null)
          return;
        inorder(root.left);
        System.out.printf("%d ", root.value);
        inorder(root.right);
      }
      
      static void inorderIter(Node root) {
        Node cur = root;
        Stack<Node> stack = new Stack<>();
        
        while (!stack.isEmpty() || cur != null) {
          if (cur != null) {
            stack.push(cur);
            cur = cur.left;
          } else {
            cur = stack.pop();
            System.out.printf("%d ", cur.value);
            cur = cur.right;
          }
        }
      }
      
      static void preorder(Node root) {
        if (root == null)
          return;
        System.out.printf("%d ", root.value);
        preorder(root.left);
        preorder(root.right);
      }
      
      static void preorderIter(Node root) {
        Node cur = root;
        Stack<Node> stack = new Stack<>();
        
        while(!stack.isEmpty() || cur != null) {
          if (cur != null) {
            System.out.printf("%d ", cur.value);
            if (cur.right != null)
              stack.push(cur.right);
            cur = cur.left;
          } else {
            cur = stack.pop();
          }
        }
      }
      
      static void postorder(Node root) {
        if (root == null)
          return;
        postorder(root.left);
        postorder(root.right);
        System.out.printf("%d ", root.value);
      }
      
      static void postorderIter(Node root) {
        Node cur = root;
        Node prev = null;
        Stack<Node> stack = new Stack<Node>();
        
        if (root == null)
          return;
        stack.push(root);
        while(!stack.isEmpty()) {
          cur = stack.peek();
          if (prev == null || prev.left == cur || prev.right == cur) {
            if (cur.left != null) {
              stack.push(cur.left);
            } else if (cur.right != null) {
              stack.push(cur.right);
            }
          } else if (cur.left == prev) {
            if (cur.right != null)
              stack.push(cur.right);
          } else {
            assert(prev == cur || cur.right == prev);
            System.out.printf("%d ", cur.value);
            stack.pop();
          }
          prev = cur;
        }
      }
    }
    
    static class BST {
      Node root;
      
      BST() {
        root = null;
      }
      
      BST(Node root) {
        this.root = root;
      }
      
      boolean isBST() {
        return isBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
      }
      
      static boolean isBST(Node node, int min, int max) {
        if (node == null)
          return true;
        if (node.value <= min || node.value > max)
          return false;
        return isBST(node.left, min, node.value) && isBST(node.right, node.value, max);
      }
      
      Node insert(int newValue) {
        Node nodeCur = root;
        Node nodeParent = null;
        Node nodeNew = new Node(newValue);
        if (root == null) {
          root = nodeNew;
          return root;
        }
        
        while (nodeCur != null) {
          nodeParent = nodeCur;
          if (newValue <= nodeCur.value)
            nodeCur = nodeCur.left;
          else 
            nodeCur = nodeCur.right;
        }
        assert(nodeParent != null);
        if (newValue <= nodeParent.value)
          nodeParent.left = nodeNew;
        else
          nodeParent.right = nodeNew;
        nodeNew.parent = nodeParent;
        return nodeNew;
      } 
      
      void delete(Node nodeDelete) {
        if (nodeDelete == null)
          return;
        if (nodeDelete.left == null) {
          moveNode(nodeDelete.right, nodeDelete);
        } else if (nodeDelete.right == null) {
          moveNode(nodeDelete.left, nodeDelete);
        } else {
          if (nodeDelete.right.left == null) {
            moveNode(nodeDelete.right, nodeDelete);
            nodeDelete.right.left = nodeDelete.left;
          } else {
            Node nodeSuccessor = nodeDelete.right;
            while (nodeSuccessor.left != null) 
              nodeSuccessor = nodeSuccessor.left;
            assert(nodeSuccessor != null);
            moveNode(nodeSuccessor.right, nodeSuccessor);
            nodeSuccessor.left = nodeDelete.left;
            nodeSuccessor.right = nodeDelete.right;
            moveNode(nodeSuccessor, nodeDelete);
          }
        }
      }
      
      void moveNode(Node nodeFrom, Node nodeTo) {
        assert(nodeTo != null);
        if (nodeTo.parent == null) {
          root = nodeFrom;
          if (root != null)
            root.parent = null;
        } else {
          if (nodeTo.parent.left == nodeTo) {
            nodeTo.parent.left = nodeFrom;
          } else {
            assert(nodeTo.parent.right == nodeTo);
            nodeTo.parent.right = nodeFrom;
          }
          if (nodeFrom != null)
            nodeFrom.parent = nodeTo.parent;
        }
      }
      
      Node find(int value) {
        return find(root, value);
      }
      
      static Node find(Node node, int value) {
        if (node == null)
          return null;
        if (value == node.value)
          return node;
        else if (value < node.value)
          return find(node.left, value);
        else 
          return find(node.right, value);
      }
    }
    
    static void driveBST() {
      BST bst = new BST();
      for (int value : new int[] {5, 3, 1, 2, 6, 4}) {
        bst.insert(value);
        System.out.printf("Insert %d\n", value);
        if (bst.root != null) {
          System.out.printf("%s\n", bst.root.describe());
        }
      }
      for (int value : new int[]{3, 6, 1, 2, 5}) {
        System.out.printf("Delete %d\n", value);
        Node nodeDelete = bst.find(value);
        if (nodeDelete == null)
          System.out.printf("%d not found\n", value);
        else {
          bst.delete(nodeDelete);
          if (bst.root != null) {
            System.out.printf("%s\n", bst.root.describe());
          }
        }
      }
    }
    
    static void driver() throws Exception {
      Node tree1 = build(" 1 ( 2 ( 3  ) 5 ( 6  ) ) ", true);
      
      Node.inorder(tree1);       System.out.println();
      Node.inorderIter(tree1);   System.out.println();
      
      Node.preorder(tree1);       System.out.println();
      Node.preorderIter(tree1);   System.out.println();
      
      Node.postorder(tree1);       System.out.println();
      Node.postorderIter(tree1);   System.out.println();

//      driverCommonAncestor(tree1, tree1.find(3), tree1.find(7));
//      driverCommonAncestor(tree1, tree1.find(2), tree1.find(4));
//      driverCommonAncestor(tree1, tree1.find(6), tree1.find(7));
//      driverCommonAncestor(tree1, tree1.find(7), tree1.find(1));
//      driverCommonAncestor(tree1, tree1.find(7), new Node(100));
      
//      driveMorphEqual(build(" 1 ( 2 ( 3 4 ) 5 ( 6 ) ) ", false), 
//              build(" 1 ( 2 ( 3 4 ) 5 ( 6 ) ) ", true));
//      driveMorphEqual(build(" 1 ( 2 ( 3 4 ) 5 ( 6 ) ) ", false), 
//              build(" 1 ( 2 ( 3 4 ) 5 ( 6 ) ) ", false));
//      driveMorphEqual(build(" 1 ( 2 ( 3  ) 5 ( 6 ) ) ", false),
//              build(" 1 ( 2 ( 3 4 ) 5 ( 6 ) ) ", false));
//      driveTreeFromTraversal(new int[] {1, 2, 4, 5, 3, 6, 7}, new int[] {4, 2, 5, 1, 6, 3, 7});
//      driveBST();
    }

    static void driverCommonAncestor(Node tree, Node node1, Node node2) {
      System.out.printf("Tree: %s\nCommon for: %d %d = ", tree.describe(), node1.value, node2.value);
//      Node common = tree.commonAncestor(node1, node2);
      Node common = Node.commonAncestor(tree, node1, node2);
      if (common == null)
        System.out.printf("NF\n");
      else 
        System.out.printf("%d\n", common.value);
    }
    
    static void driveMorphEqual(Node root1, Node root2) {
      System.out.printf("%s\n%s\n%s\n", root1.describe(), root2.describe(), morphEqual(root1, root2));
    }
    
    static Node build(String treeSpec, boolean flip) throws Exception {
      Tree.Node tn = Tree.buildTree(treeSpec);
      return Node.convert(tn, flip);
    }
    
    static boolean morphEqual(Node root1, Node root2) {
      if (root1 == null && root2 == null)
        return true;
      else if (root1 == null || root2 == null)
        return false;
      assert (root1 != null && root2 != null);
      if (root1.value != root2.value)
        return false;
      
      return (morphEqual(root1.left, root2.left) && morphEqual(root1.right, root2.right)) || 
              (morphEqual(root1.left, root2.right) && morphEqual(root1.right, root2.left));
    }
    
    static void driveTreeFromTraversal(int[] preOrder, int[] inOrder) {
      Node root = treeFromTraversal(preOrder, inOrder);
      System.out.printf("PreOrder:\n");
      Rotate.printArray(preOrder);
      System.out.printf("InOrder:\n");
      Rotate.printArray(inOrder);
      if (root == null)
        System.out.printf("Empty\n");
      else
        System.out.printf("%s\n", root.describe());
    }
    
    static Node treeFromTraversal(int[] preOrder, int[] inOrder) {
      return treeFromTraversalRec(preOrder, 0, preOrder.length, inOrder, 0, inOrder.length);
    }

    static Node treeFromTraversalRec(int[] preOrder, int startPre, int endPre, int[] inOrder, int startIn, int endIn) {
      assert (endPre - startPre == endIn - startIn);
      if (startPre == endPre) {
        return null;
      }
      Node root = new Node(preOrder[startPre]);
      int i;
      for (i = startIn; i < endIn; i++) {
        if (inOrder[i] == preOrder[startPre]) {
          break;
        }
      }
      assert (i >= startIn && i < endIn);
      int countL = i - startIn, countR = endIn - (i + 1);

      root.left = treeFromTraversalRec(preOrder, startPre + 1, startPre + 1 + countL, inOrder, startIn, i);
      root.right = treeFromTraversalRec(preOrder, startPre + 1 + countL, endPre, inOrder, i + 1, endIn);

      return root;
    }
  }
  
  static class Graph {
    List<Node> nodes;
    
    Graph(List<Node> nodes) {
      this.nodes = new ArrayList<Node>(nodes);
    }
    
    static class Node {
      int value;
      List<Node> nodes;
      int inDegree;
      boolean visited;
      int distance;
      
      Node(int value) {
        this.value = value;
        nodes = new ArrayList<Node>();
      }
      
      void visitDFS(Deque<Node> dq) {
        visited = true;
        for (Node node : nodes) {
          if (!node.visited)
            node.visitDFS(dq);
        }
        dq.push(this);
      }
    }
    
    static class RNode {
      int value;
      int[] nodeIndices;
      
      RNode(int value, int[] nodeIndices) {
        this.value = value;
        this.nodeIndices = nodeIndices;
      }
    }
    
    static void driver() {
      Graph graph = buildGraph();
      graph.topoOrder();
      graph.DFS();
      graph.BFS();
    }
    
    static Graph buildGraph() {
      RNode[] rnodes = new RNode[] {
        new RNode(0, new int[] {3, 1}),
        new RNode(1, new int[] {2, 3}),
        new RNode(2, new int[] {4}),
        new RNode(3, new int[] {}),
        new RNode(4, new int[] {3}),
        new RNode(5, new int[] {}),
        new RNode(6, new int[] {5}),
      };
      
      List<Node> nodes = new ArrayList<Node>(rnodes.length);
      for (RNode rnode : rnodes) {
        nodes.add(new Node(rnode.value));
      }
      
      for (int i = 0; i < rnodes.length; i++) {
        RNode rnode = rnodes[i];
        Node nodeCur = nodes.get(i);
        for (int index : rnode.nodeIndices) {
          Node nodeLink = nodes.get(index);
          nodeCur.nodes.add(nodeLink);
        }
      }
      return new Graph(nodes);
    }
    
    void setInDegree() {
      for (Node node : nodes) {
        node.inDegree = 0;
      }
      for (Node node : nodes) {
        for (Node nodeNext : node.nodes) {
          nodeNext.inDegree++;
        }
      }
    }
    
    void topoOrder() {
      setInDegree();
      Deque<Node> stack = new ArrayDeque<Node>();
      for (Node node : nodes) {
        if (node.inDegree == 0)
          stack.add(node);
      }
      
      while (!stack.isEmpty()) {
        Node node = stack.pop();
        
        System.out.printf("%d\n", node.value);
        for (Node nodeNext : node.nodes) {
          nodeNext.inDegree--;
          if (nodeNext.inDegree == 0) 
            stack.push(nodeNext);
        }
      }
    }

    void clearVisited() {
      for (Node node : nodes) {
        node.visited = false;
      }
    }
    
    void DFS() {
      Deque<Node> dq = new ArrayDeque<>();
      for (Node node : nodes) {
        if (!node.visited)
          node.visitDFS(dq);
      }
      // topological sort
      while (!dq.isEmpty()) {
        Node node = dq.pop();
        System.out.printf("%d\n", node.value);
      }
    }
    
    void BFS() {
      clearVisited();
      if (nodes.isEmpty())
        return;
      Queue<Node> q = new ArrayDeque<>();
      Node start = nodes.get(0);
      start.distance = 0;
      start.visited = true;
      q.add(start);
      
      while (!q.isEmpty()) {
        Node node = q.remove();
        System.out.printf("%d: %d\n", node.value, node.distance);
        for (Node next : node.nodes) {
          if (!next.visited) {
            next.visited = true;
            next.distance = node.distance + 1;
            q.add(next);
          }
        }
      }
    }
  }
  
  static class StringUtil {
    static void driver() {
//      driveSep("aaa;bb;cc", ";", 3);
//      driveSep("aa;;b;;cc;;", ";;", 3);
//      driveSep("aa;;bb;;c;;d", ";;", 3);
//      driveSep("a;b", ";", 3);
//      driveCommonSubstring("ababc", 2);
//      driveCommonSubstring("abbbbab", 2);
//      driveCommonSubstring("abbbbab", 3);
      driveCommonSubstring("abcdefghijklmnop", "what are abc and defg doing here");
      driveCommonSubstring("abc", "what are abc and defg doing here");
      driveCommonSubstring("abc", "abc");
      driveCommonSubstring("abc", "def");
    }
    
    static void driveSep(String in, String sep, int countExpect) {
      String[] tuple = parseBySeparator(in, sep, countExpect);
      if (tuple == null) {
        System.out.printf("%s by %s: fail\n", in, sep);
      } else {
        System.out.printf("%s by %s: ", in, sep);
        for (String value : tuple) {
          System.out.printf("%s ", value);
        }
        System.out.println();
      }
    }
    
    static String[] parseBySeparator(String in, String sep, int countExpect) {
      String[] tuple = new String[countExpect];
      int fromIndex = 0;
      int countFound = 0;
      boolean tooMany = false;
      
      while (true) {
        int sepIndex = in.indexOf(sep, fromIndex);
        if (sepIndex < 0)
          break;
        
        if (countFound == countExpect) {
          tooMany = true;
          break;
        }
        tuple[countFound] = in.substring(fromIndex, sepIndex);
        countFound++;
        fromIndex = sepIndex + sep.length();
      }
      
      if (!tooMany && fromIndex < in.length()) {
        if (countFound >= countExpect) {
          tooMany = true;
        } else {
          tuple[countFound] = in.substring(fromIndex, in.length());
          countFound++;
        }
      }
      boolean tooFew = false;
      if (countFound < countExpect)
        tooFew = true;
      
      return (tooFew || tooMany)? null : tuple;
    }
    
    static void driveCommonSubstring(String in, int nRepeat) {
//      System.out.printf("%s (%d): %s\n", in, nRepeat, commonSubstring(in, nRepeat));
      System.out.printf("%s: %s\n", in, commonSubstringDP(in));
    }
    
    static void driveCommonSubstring(String x, String y) {
      System.out.printf("%s, %s: %s\n", x, y, commonSubstringDP(x, y));
    }

    static String commonSubstring(String in, int nRepeat) {
      String[] subs = new String[in.length()];
      for (int i = 0; i < in.length(); i++) {
        subs[i] = in.substring(i);
      }
      Arrays.sort(subs, new Comparator<String>() {
        @Override
        public int compare(String a, String b) {
          return a.compareTo(b);
        }
      });
      int maxLen = 0;
      int indexMax = 0;
      for (int i = 0; i < subs.length - nRepeat + 1; i++) {
        int commonPrefixLen = commonPrefixLen(subs[i], subs[i + nRepeat - 1]);
        if (commonPrefixLen > maxLen) {
          maxLen = commonPrefixLen;
          indexMax = i;
        }
      }
      return (maxLen == 0)? "" : subs[indexMax].substring(0, maxLen);
    }
    
    static int commonPrefixLen(String a, String b) {
      int i, len = Math.min(a.length(), b.length());
      for (i = 0; i < len; i++) {
        if (a.charAt(i) != b.charAt(i))
          break;
      }
      return i;
    }
    
    static String commonSubstringDP(String in) {
      assert(in != null);
      int n = in.length();
      int[][] L = new int[n][n];
      int maxLen = 0, maxEndI = 0, maxEndJ = 0;
      
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          if (i == j) {
            L[i][j] = 0;
          } else {
            int match = (in.charAt(i) == in.charAt(j))? 1 : 0;
            if (i > 0 && j > 0)
              L[i][j] = L[i-1][j-1] + match;
            else
              L[i][j] = match;
            if (L[i][j] > maxLen) {
              maxLen = L[i][j];
              maxEndI = i;
              maxEndJ = j;
            }
          }
        }
      }
      return (maxLen == 0)? "" : in.substring(maxEndI + 1 - maxLen, maxEndI + 1);
    }
    
    static String commonSubstringDP(String x, String y) {
      assert (x != null && y != null);
      int m = x.length();
      int n = y.length();
      int[][] L = new int[m][n];
      int maxLen = 0, maxEndI = 0, maxEndJ = 0;

      for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
          int match = (x.charAt(i) == y.charAt(j)) ? 1 : 0;
          if (i > 0 & j > 0)
            L[i][j] = L[i - 1][j - 1] + match;
          else
            L[i][j] = match;
          if (L[i][j] > maxLen) {
            maxLen = L[i][j];
            maxEndI = i;
            maxEndJ = j;
          }
        }
      }
      return (maxLen == 0) ? "" : x.substring(maxEndI + 1 - maxLen, maxEndI + 1);
    }
  }
  
  static class WordBreaker {
    
    static class State {
      boolean isReachable;
      boolean explored;
      String word;
      float cost;
    }
    
    static void driver() {
      driveBreak("thisisalongsentencewithoutanyspaces", new String[] {
        "a", "an", "the", "is", "as", "was", "alongs", "sent", "long", "this", "paces", "faces", "spaces", "sentence", "with", "without",
        "any"
      });
      driveBreak("thisisaverylongsentencewithoutanyspaces", new String[]{
        "a", "an", "the", "is", "sent", "long", "this", "paces", "faces", "spaces", "sentence", "with", "without",
        "any"
      });
    }
    static void driveBreak(String text, String[] words) {
      System.out.printf("Break: %s\nBy: \n", text);
      for (String word : words) {
        System.out.printf("%s ", word);
      }
      System.out.println();
      // breakByWords(text, words);
      breakByWordsDfs(text, words);
    }
    
    static void breakByWords(String text, String[] words) {
      int n = text.length();
      State[] states = new State[n + 1];
      for (int i = 0; i < states.length; i++) {
        states[i] = new State();
      }
      states[0].isReachable = true;
      for (int i = 0; i < n; i++) {
        State state = states[i];
        if (state.isReachable) {
          int indexText = i;
          for (String word : words) {
            int wl = word.length();
            if (i + wl <= n && word.equals(text.substring(indexText, indexText + wl))) {
              State stateNext = states[i + wl];
              stateNext.isReachable = true;
              stateNext.word = word;
            }
          }
        }
      }
      traceback(states);
    }
    
    static void breakByWordsDfs(String text, String[] words) {
      int n = text.length();
      State[] states = new State[n + 1];
      for (int i = 0; i < states.length; i++) {
        states[i] = new State();
      }
      breakMemoized(states, text, words, n);
      traceback(states);
    }
    
    // this implementation re-explores states which are not reachable
    static void breakDfsRec(State[] states, String text, String[] words, int index, int n) {
      for (String word : words) {
        int wl = word.length();
        if (index + wl <= n && word.equals(text.substring(index, index + wl))) {
          int indexNext = index + wl;
          State stateNext = states[indexNext];
          if (!stateNext.isReachable) {
            stateNext.isReachable = true;
            stateNext.word = word;
            breakDfsRec(states, text, words, indexNext, n);
          }
          if (states[n].isReachable)
            break;
        }
      }
    }
    
    static boolean breakMemoized(State[] states, String text, String[] words, int index) {
      if (index == 0)
        return true;
      State stateCur = states[index];
      if (stateCur.explored)
        return stateCur.isReachable;
      for (String word : words) {
        int indexNew = index - word.length();
        if (indexNew < 0 || !word.equals(text.substring(indexNew, index)))
          continue;
        if (breakMemoized(states, text, words, indexNew)) {
          stateCur.word = word;
          stateCur.isReachable = true;
          break;
        }
      }
      stateCur.explored = true;
      return stateCur.isReachable;
    }
    
    static void traceback(State[] states) {
      State stateCur = states[states.length - 1];
      if (!stateCur.isReachable) {
        System.out.printf("Not possible to break\n");
      } else {
        tracebackRec(states, states.length - 1);
        System.out.println();
      }
    }
    
    static void tracebackRec(State[] states, int indexState) {
      if (indexState == 0)
        return;
      State stateCur = states[indexState];
      int indexNew = indexState - stateCur.word.length();
      tracebackRec(states, indexNew);
      System.out.printf("%s ", stateCur.word);
    }
  }
  
  static class Combinatorics {
    
    static void driver() {
      chooseAll(5);
      
//      Map<String, KeyMap> phoneMap = buildPhoneMap();
      
//      drivePhoneMap(phoneMap, "1439");
//      driveTuples(new String[] {"a", "b", "c"});
//      int items[] = new int[] {1, 2, 3, 4, 5, 6};
//      choose(items, items.length, 3, new ArrayList<Integer>());
    }
    
    static void drivePhoneMap(Map<String, KeyMap> phoneMap, String number) {
      List<String> words = allWordsFromNumberRec(phoneMap, number);
      System.out.printf("Expansions of %s:\n", number);
      if (words == null)
        System.out.printf("  Fail\n");
      else {
        for (String word : words) {
          System.out.printf("  %s\n", word);
        }
      }
    }
    
    static void driveTuples(String[] words) {
      List<String> wordsAsList = Arrays.asList(words);
      Tuple in = new Tuple(wordsAsList);
      List<Tuple> tuples = genTuples(in);
      for (Tuple tuple : tuples) {
        System.out.printf("%s\n", tuple.describe());
      }
    }
    
    static class KeyMap {
      String[] letters;
      
      KeyMap(String[] words) {
        this.letters = words;
      }
    }
    
    static Map<String, KeyMap> buildPhoneMap() {
      Map<String, KeyMap> phoneMap = new HashMap<>();
      phoneMap.put("1", new KeyMap(new String[] {"a", "b", "c"}));
      phoneMap.put("2", new KeyMap(new String[] {"d", "e", "f"}));
      phoneMap.put("3", new KeyMap(new String[] {"g", "h", "i"}));
      phoneMap.put("4", new KeyMap(new String[] {"j", "k", "l"}));
      phoneMap.put("5", new KeyMap(new String[] {"m", "n", "o"}));
      phoneMap.put("6", new KeyMap(new String[] {"p", "q", "r"}));
      phoneMap.put("7", new KeyMap(new String[] {"s", "t", "u"}));
      phoneMap.put("8", new KeyMap(new String[] {"v", "w", "x"}));
      phoneMap.put("9", new KeyMap(new String[] {"y", "z", }));
      phoneMap.put("0", new KeyMap(new String[] {"_"}));
      return phoneMap;
    }
    
    static List<String> allWordsFromNumber(Map<String, KeyMap> phoneMap, String number) {
      List<String> words = new ArrayList<String>();
      words.add("");
      
      for (int index = 0; index < number.length(); ++index) {
        String digit = number.substring(index, index + 1);
        KeyMap km = phoneMap.get(digit);
        if (km == null)
          return null;
        List<String> newWords = new ArrayList<String>();
        for (String word : words) {
          for (String letter: km.letters) {
            newWords.add(word + letter);
          }
        }
        words = newWords;
      }
      return words;
    }
    
    static List<String> allWordsFromNumberRec(Map<String, KeyMap> phoneMap, String number) {
      List<String> words = new ArrayList<String>();
      words.add("");
      return allWordsRec(phoneMap, number, 0, words);
    }
    
    static List<String> allWordsRec(Map<String, KeyMap> phoneMap, String number, int index, List<String> wordsIn) {
      if (index == number.length())
        return wordsIn;
      String digit = number.substring(index, index + 1);
      KeyMap km = phoneMap.get(digit);
      if (km == null)
        return wordsIn;
      List<String> wordsOut = new ArrayList<>();
      for (String word : wordsIn) {
        for (String letter : km.letters) {
          wordsOut.add(word + letter);
        }
      }
      return allWordsRec(phoneMap, number, index + 1, wordsOut);
    }
    
    static class Tuple {
      List<String> words;
      Tuple(List<String> words) {
        this.words = words;
      }
      
      String describe() {
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
          sb.append(word).append(" ");
        }
        return sb.toString();
      }
    }
    
    static List<Tuple> genTuples(Tuple in) {
      int n = in.words.size();
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < n; ++i) {
        sb.append("0");
      }
      StringInt select = new StringInt(sb.toString(), 2);
      List<Tuple> tuples = new ArrayList<Tuple>();
      for (int numGen = 0; numGen < (int) Math.pow(2, n); numGen++) {
        List<String> newWords = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
          String digit = select.value.substring(i, i + 1);
          if (digit.equals("0")) {
            newWords.add("*");
          } else {
            newWords.add(in.words.get(i));
          }
        }
        tuples.add(new Tuple(newWords));
        select.increment();
      }
      return tuples;
    }
    
    static void choose(int[] items, int n, int k, List<Integer> chosen) {
      assert (n >= k);

      if (k == 0) {
        // done print the chosen and return
        for (int chosenItem : chosen) {
          System.out.printf("%d ", chosenItem);
        }
        System.out.println();
        return;
      }
      int indexCur = items.length - n;
      if (k < n) {
        // skip current
        choose(items, n - 1, k, chosen);
      }
      // choose current
      List<Integer> added = new ArrayList(chosen);
      added.add(items[indexCur]);
      choose(items, n - 1, k - 1, added);
    }
    
    static void chooseAll(int n) {
      boolean[] flags = new boolean[n];
      for (int i = 0; i < (int) Math.pow(2, n); i++) {
        printFlags(flags);
        incrementFlags(flags);
      }
    }

    static void incrementFlags(boolean[] flags) {
      boolean carry = true;
      for (int i = 0; i < flags.length; i++) {
        if (!carry) {
          break;
        }
        assert (carry);
        boolean cur = flags[i];
        if (!cur) {
          cur = true;
          carry = false;
        } else {
          assert (cur && carry);
          cur = false;
        }
        flags[i] = cur;
      }
    }

    static void printFlags(boolean[] flags) {
      for (int i = 0; i < flags.length; i++) {
        if (flags[i]) {
          System.out.printf("%d ", i);
        }
      }
      System.out.println();
    }
  }
  
  static class HashTab<K, V> {
    
    
    private List<List<Entry<K, V>>> buckets;
    private double growthFactor = 2;
    private double loadFactor = 0.8;
    private int initialBuckets = 1023;
    private int initialSlots = 3;
    private int countEntries;
    private long readers = 0;
    private Lock readLock;
    private Lock writeLock;

    HashTab() {
      readLock = new ReentrantLock();
      writeLock = new ReentrantLock();
      buckets = new ArrayList<>(initialBuckets);
      for (int i = 0; i < initialBuckets; i++) {
        buckets.add(new ArrayList<Entry<K, V>>(initialSlots));
      }
    }
    
    public void put(K key, V value) {
      writeLock.lock();
      try {
        long hash = (key == null)? 0 : key.hashCode();
        int iBucket = (int) (hash % buckets.size());
        List<Entry<K, V>> bucket = buckets.get(iBucket);
        Entry<K, V> entry = findInBucket(bucket, key);
        if (entry == null) {
          bucket.add(new Entry<K, V>(key, value));
          countEntries++;
        }
        else 
          entry.value = value;
      } finally {
        writeLock.unlock();
      }
    }
    
    public V get(K key) {
      readLock.lock(); 
      try {
        readers++;
        if (readers == 1)
          writeLock.lock();
      } finally {
        readLock.unlock();
      }
      
      long hash = hash(key);
      int iBucket = (int) (hash % buckets.size());
      List<Entry<K, V>> bucket = buckets.get(iBucket);
      Entry<K, V> entry = findInBucket(bucket, key);
      V valueRet;
      if (entry == null) {
        put(key, null);
        valueRet = null;
      }
      else 
        valueRet = entry.value;
      
      readLock.lock(); 
      try {
        readers--;
        if (readers == 0)
          writeLock.unlock();
      } finally {
        readLock.unlock();
      }
      return valueRet;
    }
    
    private long hash(K key) {
      long hash = (key == null)? 0 : key.hashCode();
      return hash;
    }
    
    private Entry findInBucket(List<Entry<K, V>> bucket, K key) {
      Entry entryRet = null;
      for (Entry entry : bucket) {
        if ((entry.key == null && key == null)
                || (entry.key != null && entry.key.equals(key))) {
          entryRet = entry;
          break;
        }
      }
      return entryRet;
    }
    
    static public class Entry<K, V> {
      private K key;
      private V value;

      public Entry(K key, V value) {
        this.key = key;
        this.value = value;
      }
    }
  }
  
  static class Sort {
    static Random random = new Random();
    
    static void driver() {
      driveOne(new int[] {1, 2, 4, 3, 9, 6, 5, 4, 3, 11, 2, 7});
      driveOne(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
      driveOne(new int[] {7, 6, 5, 4, 3, 2, 1, 3, 23, 5, 7, 9});
    }
    
    static void driveOne(int[] x) {
//      heapsort(x);
      Rotate.printArray(x);
      int[] y = new int[x.length];
      for (int i = 0; i < x.length; i++) {
        y[i] = select(i, x, 0, x.length - 1);
      }
      Rotate.printArray(y);
    }
    
    static void quicksort(int[] x, int first, int last) {
      if (first >= last)
        return;
      int q = partition(x, first, last);
      quicksort(x, first, q - 1);
      quicksort(x, q + 1, last);
    }
    
    static int partition(int[] x, int first, int last) {
      assert(first < last);
      int selectFromRange = random.nextInt(last - first + 1);
      int indexPivot = first + selectFromRange;
      swap(x, indexPivot, last);
      int pivot = x[last];
      int indexLower = first - 1;
      for (int indexScan = first; indexScan < last; indexScan++) {
        if (x[indexScan] <= pivot) {
          indexLower++;
          swap(x, indexLower, indexScan);
        }
      }
      indexLower++;
      swap(x, indexLower, last);
      return indexLower;
    }
   
    static void swap(int[] x, int index1, int index2) {
      assert(index1 >= 0 && index1 < x.length);
      assert(index2 >= 0 && index2 < x.length);
      int temp = x[index1];
      x[index1] = x[index2];
      x[index2] = temp;
    }
    
    static void heapsort(int[] x) {
      int n = x.length;
      makeHeap(x);
      for (int i = 0; i < n - 1; i++) {
        swap(x, 0, n - i - 1);
        restoreHeap(x, 0, n - i - 1);
      }
    }
    
    static void makeHeap(int[] x) {
      int n = x.length;
      for (int i = n / 2; i >= 0; i--) {
        restoreHeap(x, i, n);
      }
    }
    
    static void restoreHeap(int[] x, int i, int n) {
      int iBiggest = i;
      if (2 * i < n && x[2 * i] > x[iBiggest])
        iBiggest = 2 * i;
      if (2 * i + 1 < n && x[2 * i + 1] > x[iBiggest])
        iBiggest = 2 * i + 1;
      if (iBiggest != i) {
        swap(x, i, iBiggest);
        restoreHeap(x, iBiggest, n);
      }
    }
    
    static int select(int k, int[] x, int s, int e) {
      assert e >= s;
      assert k >= s && k <= e;
      if (s == e)
        return x[s];
      int indexPivot = partition(x, s, e);
      if (k == indexPivot)
        return x[k];
      else if (k < indexPivot)
        return select(k, x, s, indexPivot - 1);
      else /* k > indexPivot */
        return select(k, x, indexPivot + 1, e);
    }
  }
  
  static class YT {
    boolean search(int[][] yt, int key, int sRow, int eRow, int sCol, int eCol) {
      if (sRow == eRow && sCol == eCol) {
        return (key == yt[sRow][sCol]);
      }

      int mRow = (sRow + eRow + 1) / 2;
      int mCol = (sCol + eCol + 1) / 2;

      int median = yt[mRow][mCol];
      if (key == median) {
        return true;
      } else if (key > median) {
        return search(yt, key, sRow, eRow, mCol, eCol)
                || search(yt, key, sRow, mRow, sCol, mCol);
      } else {
        return search(yt, key, sRow, mRow, sCol, eCol)
                || search(yt, key, mRow, eRow, sCol, mCol);
      }
    }
    
    boolean search2(int[][] yt, int key) {
      if (yt.length == 0 || yt[0].length == 0) {
        return false;
      }
      int m = yt.length;
      int n = yt[0].length;
      if (key < yt[0][0] || key > yt[m - 1][n - 1]) {
        return false;
      }
      int row = 0, col = n - 1;
      while (col >= 0 && row < m) {
        int curValue = yt[row][col];
        if (key == curValue) {
          return true;
        } else if (key > curValue) {
          row++;
        } else {
          col--;
        }
      }
      return false;
    }
    
    int kthSq(int[][] yt, int k) {
      int m = yt.length;
      assert (m > 0);
      int n = yt[0].length;
      assert (m == n);
      assert (k >= 0 && k < n * n);
      // find d such that (d + 1) * (d + 1) >= k
      int d;
      for (d = 0; d < n; d++) {
        if ((d + 1) * (d + 1) >= k) {
          break;
        }
      }
      assert (d < n);
      if ((d + 1) * (d + 1) == k) {
        return yt[d][d];
      }
      assert (d == 0 || d * d < k);
      // merge yt[d][0] .. yt[d][d - 1] & yt[0][d] .. yt[d-1][d]
      int[] merged = mergesort(yt, d);
      return merged[k - d * d];
    }
    
    int[] mergesort(int[][] yt, int d) {
      if (d == 0)
        return new int[0];
      int[] x = new int[d];
      for (int i = 0; i < d; i++)
        x[i] = yt[d][i];
      int[] y = new int[d];
      for (int i = 0; i < d; i++) {
        y[i] = yt[d][i];
      }
      return Utility.mergeSort(x, y);
    } 
}
  
  static class LL {
    static class Node {
      int value;
      Node next;
    }
    
    Node reverse(Node start) {
      if (start == null)
        return null;

      Node cur = start;
      Node last = null;
      while (cur != null) {
        Node n = cur.next;
        cur.next = last;
        last = cur;
        cur = n;
      }
      return last;
    }
    
    static Node reverse2(Node cur) {
      if (cur == null) {
        return null;
      }
      Node n = cur.next;
      Node rn = reverse2(n);
      if (n != null) {
        n.next = cur;
      }
      cur.next = null;
      return (rn == null) ? cur : rn;
    }
    
    static Node insert(Node start, Node ins) {
      if (start == null) {
        return ins;
      }
      Node last = null;
      Node cur = start;
      while (cur != null) {
        if (cur.value >= ins.value) {
          ins.next = cur;
          if (last == null) {
            return ins;
          }
          last.next = ins;
          return start;
        }
        last = cur;
      }
      // insert at the tail
      last.next = ins;
      return start;
    }
  }
  
  static class RodCut {
    static void driver() {
      int[] prices = new int[] {0, 1, 5, 8, 9, 10, 17, 17, 20, 24, 30};
      for (int i = 0; i < prices.length; i++) {
        cutRod2(prices, i);
      }
    }
    
    static void cutRod(int[] prices, int n) {
      assert(n < prices.length);
      int[] revenue = new int[n + 1];
      int[] lengths = new int[n + 1];
      
      for (int i = 0; i < revenue.length; i++) {
        revenue[i] = -1;
      }
      revenue[0] = 0;
      for (int pos = 0; pos < n; pos++) {
        if (revenue[pos] < 0)
          continue;
        for (int i = 1; i < prices.length; i++) {
          if (prices[i] < 0)
            continue;
          int lengthNew = pos + i;
          if (lengthNew > n)
            continue;
          int revenueNew = revenue[pos] + prices[i];
          if (revenueNew > revenue[lengthNew]) {
            revenue[lengthNew] = revenueNew;
            lengths[lengthNew] = i;
          }
        }
      }
      System.out.printf("L=%d: ", n);
      traceback(revenue, lengths, n);
      System.out.println();
    }
    
    static void cutRod2(int[] prices, int n) {
      int[] revenue = new int[n + 1];
      int[] lengths = new int[n + 1];
      for (int i = 0; i < revenue.length; i++) {
        revenue[i] = -1;
      }
      revenue[0] = 0;
      cutRodMemoized(prices, n, revenue, lengths);
      System.out.printf("L=%d : ", n);
      if (revenue[n] < 0)
        System.out.printf("Cannot cut");
      else
        traceback(revenue, lengths, n);
      System.out.println();
    }

    static void cutRodMemoized(int[] prices, int n, int[] revenue, int[] lengths) {
      if (revenue[n] >= 0) {
        return;   // already explored
      }
      for (int i = 1; i < prices.length; i++) {
        if (i > n)
          continue;
        cutRodMemoized(prices, n - i, revenue, lengths);
        if (revenue[n - i] >= 0 && revenue[n - i] + prices[i] > revenue[n]) {
          revenue[n] = revenue[n - i] + prices[i];
          lengths[n] = i;
        }
      }
    }

    static void traceback(int[] revenue, int[] lengths, int n) {
      if (n == 0)
        return;
      if (revenue[n] < 0)
        System.out.printf("Cannot break\n");
      else {
        int curLength = lengths[n];
        int lastLength = n - curLength;
        traceback(revenue, lengths, lastLength);
        int price = revenue[n] - revenue[lastLength];
        System.out.printf("%d: %d %d ", curLength, price, revenue[n]);
      }
    }
  }
  
  static class LCS {
    static class State {
      int lcs;
      boolean explored;
      int prevI;
      int prevJ;
    }
    
    static void driver() {
      find("ACCGGTCGAGTGCGCGAAGCCGGCCGAA", "GTCGTTCGGAATGCCGTTGCTCTGTAAA", true);
    }
    
    static void find(String x, String y, boolean memoized) {
      int m = x.length();
      int n = y.length();
      State[][] states = new State[m + 1][n + 1];
      for (int i = 0; i <= m; i++) {
        for (int j = 0; j <=n; j++)
          states[i][j] = new State();
      }
      
      if (memoized) {
        findMemoized(states, x, y, m, n);
      } else {
        for (int i = 1; i <= m; i++) {
          for (int j = 1; j <= n; j++) {
            char xCh = x.charAt(i - 1);
            char yCh = y.charAt(j - 1);
            State sCur = states[i][j];
            if (xCh == yCh) {
              int lcsNew = states[i - 1][j - 1].lcs + 1;
              sCur.lcs = lcsNew;
              sCur.prevI = i - 1;
              sCur.prevJ = j - 1;
            } else {
              if (states[i - 1][j].lcs > states[i][j - 1].lcs) {
                sCur.lcs = states[i - 1][j].lcs;
                sCur.prevI = i - 1;
                sCur.prevJ = j;
              } else {
                sCur.lcs = states[i][j - 1].lcs;
                sCur.prevI = i;
                sCur.prevJ = j - 1;
              } 
            }
          }
        }
      }
      traceback(states, m, n, x);
      System.out.println();
    }
    
    static void findMemoized(State[][] states, String x, String y, int m, int n) {
      if (m == 0 || n == 0)
        return;
      State sCur = states[m][n];
      if (sCur.explored)
        return;
      char xCh = x.charAt(m - 1);
      char yCh = y.charAt(n - 1);
      if (xCh == yCh) {
        findMemoized(states, x, y, m - 1, n - 1);
        sCur.lcs = states[m - 1][n - 1].lcs + 1;
        sCur.prevI = m - 1;
        sCur.prevJ = n - 1;
      } else {
        findMemoized(states, x, y, m - 1, n);
        findMemoized(states, x, y, m, n - 1);
        if (states[m-1][n].lcs > states[m][n-1].lcs) {
          sCur.lcs = states[m - 1][n].lcs;
          sCur.prevI = m - 1;
          sCur.prevJ = n;
        } else {
          sCur.lcs = states[m][n - 1].lcs;
          sCur.prevI = m;
          sCur.prevJ = n - 1;
        }
      }
      sCur.explored = true;
    }
    
    static void traceback(State[][] states, int i, int j, String x) {
      State sCur = states[i][j];
      if (i == 0 || j == 0)
        return;
      traceback(states, sCur.prevI, sCur.prevJ, x);
      if (sCur.prevI < i && sCur.prevJ < j)
        System.out.printf("%c", x.charAt(i - 1));
    }
  }
  
  static class EditDistance {
    
    static enum Op {ins, del, sub, twiddle, kill};
    static final double costSub = 1;
    static final double costIns = 1;
    static final double costDel = 1;
    static final double costTwiddle = 1;
    static final double costKill = 2;
    
    static void driver() {
      align("where is that there", "what is");
      align("what is this", "What's up with that");
    }
    
    static void align(String x, String y) {
      int m = x.length();
      int n = y.length();
      
      double[][] costs = new double[m + 1][n + 1];
      Op[][] ops = new Op[m + 1][n + 1];
      int[][] prevI = new int[m + 1][n + 1];
      int[][] prevJ = new int[m + 1][n + 1];
      
      for (int i = 0; i <= m; i++) {
        for (int j = 0; j <= n; j++) {
          costs[i][j] = Double.POSITIVE_INFINITY;
        }
      }
      costs[0][0] = 0;
      
      for (int i = 0; i <= m; i++) {
        for (int j = 0; j <= n; j++) {
          if (i > 0 && j > 0) {
            char xCh = x.charAt(i - 1);
            char yCh = y.charAt(j - 1);
            double costNew = costs[i - 1][j - 1] + ((xCh == yCh)? 0 : costSub);
            if (costNew < costs[i][j]) {
              costs[i][j] = costNew;
              prevI[i][j] = i - 1;
              prevJ[i][j] = j - 1;
              ops[i][j] = Op.sub;
            }
          }
          
          if (i > 0) {
            double costNew = costs[i - 1][j] + costDel;
            if (costNew < costs[i][j]) {
              costs[i][j] = costNew;
              prevI[i][j] = i - 1;
              prevJ[i][j] = j;
              ops[i][j] = Op.del;
            }
          }
          
          if (j > 0) {
            double costNew = costs[i][j - 1] + costIns;
            if (costNew < costs[i][j]) {
              costs[i][j] = costNew;
              ops[i][j] = Op.ins;
              prevI[i][j] = i;
              prevJ[i][j] = j - 1;
            }
          }
          
          if (i >= 2 && j >= 2 && x.charAt(i - 2) == y.charAt(j - 1) && x.charAt(i - 1) == y.charAt(j - 2)) {
            double costNew = costs[i - 2][j - 2] + costTwiddle;
            if (costNew < costs[i][j]) {
              costs[i][j] = costNew;
              ops[i][j] = Op.twiddle;
              prevI[i][j] = i - 2;
              prevJ[i][j] = j - 2;
            }
          }
        }
      }
      
      for (int i = 0; i < m; i++) {
        double costNew = costs[i][n] + costKill;
        if (costNew < costs[m][n]) {
          costs[m][n] = costNew;
          ops[m][n] = Op.kill;
          prevI[m][n] = i;
          prevJ[m][n] = n;
        }
      }
      
      System.out.printf("Align: %s : %s\n", x, y);
      if (costs[m][n] < Double.POSITIVE_INFINITY) {
        System.out.printf("Total cost: %.0f\n", costs[m][n]);
        traceback(costs, ops, prevI, prevJ, x, y, m, n);
      } else {
        System.out.printf("Failed\n");
      }
      System.out.println();
    }
    
    static void traceback(double[][] costs, Op[][] ops, int[][] prevI, int[][] prevJ, String x, String y, int m, int n) {
      if (m == 0 && n == 0)
        return;
      Op opCur = ops[m][n];
      if (opCur == Op.sub) {
        traceback(costs, ops, prevI, prevJ, x, y, prevI[m][n], prevJ[m][n]);
        char xCh = x.charAt(m - 1);
        char yCh = y.charAt(n - 1);
        if (xCh == yCh)
          System.out.printf("M: %c \n", xCh);
        else 
          System.out.printf("S: %c %c \n", xCh, yCh);
      } else if (opCur == Op.ins) {
        traceback(costs, ops, prevI, prevJ, x, y, prevI[m][n], prevJ[m][n]);
        char yCh = y.charAt(n - 1);
        System.out.printf("I: %c \n", yCh);
      } else if (opCur == Op.del) {
        traceback(costs, ops, prevI, prevJ, x, y, prevI[m][n], prevJ[m][n]);
        char xCh = x.charAt(m - 1);
        System.out.printf("D: %c \n", xCh);
      } else if (opCur == Op.twiddle) {
        traceback(costs, ops, prevI, prevJ, x, y, prevI[m][n], prevJ[m][n]);
        System.out.printf("T: %c %c\n", x.charAt(m - 2), y.charAt(n - 1));
        System.out.printf("T: %c %c\n", x.charAt(m - 1), y.charAt(n - 2));
      } else if (opCur == Op.kill) {
        traceback(costs, ops, prevI, prevJ, x, y, prevI[m][n], prevJ[m][n]);
        System.out.printf("K: %s\n", x.substring(prevI[m][n], x.length()));
      }
    }
  }
  
  static class PotsOfGold {
    
    static void driver() {
      payoffStart(new int[] {10, 20, 30, 50, 5, 100, 70, 10});
      payoffStart(new int[] {10, 100, 20});
    }
    
    static void payoffStart(int[] pots) {
      // allocate store & initialize to -1
      int n = pots.length;
      int[][] store = new int[n][n];
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          store[i][j] = - 1;
        }
      }
      int[][] move = new int[n][n];
      int payoffA = payoff(pots, 0, pots.length - 1, store, move);
      // traceback moves
      System.out.printf("A %s by %d\n", (payoffA >= 0) ? "wins" : "loses", (int) Math.abs(payoffA));
      Rotate.printArray(pots);
      traceback(pots, move, 0, n - 1, "A");
    }

    static int payoff(int[] pots, int s, int e, int[][] store, int[][] move) {
      if (s > e) {
        return 0;
      }
      if (store[s][e] >= 0) {
        return store[s][e];
      }
      int payoffS = pots[s] - payoff(pots, s + 1, e, store, move);
      int payoffE = pots[e] - payoff(pots, s, e - 1, store, move);
      if (payoffS > payoffE) {
        store[s][e] = payoffS;
        move[s][e] = s;
      } else {
        store[s][e] = payoffE;
        move[s][e] = e;
      }
      return store[s][e];
    }

    static void traceback(int[] pots, int[][] move, int s, int e, String player) {
      if (s > e) {
        return;
      }
      int iPot = move[s][e];
      System.out.printf("%s (%d %d): %2d: %3d\n", player, s, e, iPot, pots[iPot]);
      String nextPlayer = player.equals("A") ? "B" : "A";
      if (s == iPot) {
        traceback(pots, move, s + 1, e, nextPlayer);
      } else {
        traceback(pots, move, s, e - 1, nextPlayer);
      }
    }
  }
  
  static class RandomWalk {
    
    static void driver() {
      probAlive(5, 5, 10, 20);
      probAlive(0, 0, 20, 30);
      probAlive(1, 1, 2, 5);
      probAlive(2, 2, 4, 5);
    }
    static double probAlive(int x, int y, int n, int steps) {
      double[][][] store = new double[n][n][steps + 1];
      // initialize store
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          for (int s = 0; s <= steps; s++) {
            store[i][j][s] = -1;
          }
        }
      }
      if (n == 0) {
        return 0;
      }
      double probRet = probAliveRec(x, y, n, steps, store);
      System.out.printf("%d steps from (%d, %d) in (%dx%d): %7.5f\n", steps, x, y, n, n, probRet);
      return probRet;
    }

    static double probAliveRec(int x, int y, int n, int steps, double[][][] store) {
      if (x < 0 || y < 0 || x >= n || y >= n) {
        return 0;
      }
      assert (x >= 0 && x < n && y >= 0 && y < n);
      if (steps == 0) {
        return 1;
      }
      if (store[x][y][steps] >= 0) {
        return store[x][y][steps];
      }
      double probRet = -1;

      probRet = 0.25 * (probAliveRec(x + 1, y, n, steps - 1, store)
              + probAliveRec(x - 1, y, n, steps - 1, store)
              + probAliveRec(x, y + 1, n, steps - 1, store)
              + probAliveRec(x, y - 1, n, steps - 1, store));
      store[x][y][steps] = probRet;
      return probRet;
    }
  }
  
  static class Misc{
    static void charFreq2(String in) {
      char chCur = '\0', chMax = '\0', chMax2 = '\0';
      int frCur = 0, frMax = 0, frMax2 = 0;

      for (int i = 0; i < in.length(); i++) {
        char chI = in.charAt(i);
        if (i == 0) {
          chCur = chI;
        }
        if (chI == chCur) {
          frCur++;
        } else {
          assert (chCur != chI);
          if (frCur >= frMax) {
            chMax2 = chMax;
            frMax2 = frMax;
            chMax = chCur;
            frMax = frCur;
          } else if (frCur > frMax2) {
            chMax2 = chCur;
            frMax2 = frCur;
          }
          chCur = chI;
          frCur = 1;
        }
      }

      if (frCur >= frMax) {
        chMax2 = chMax;
        frMax2 = frMax;
        chMax = chCur;
        frMax = frCur;
      } else if (frCur > frMax2) {
        chMax2 = chCur;
        frMax2 = frCur;
      }

      if (frMax2 == 0) {
        System.out.printf("Cannot find second highest frequency\n");
      } else {
        System.out.printf("Second highest freq char: %c\n", chMax2);
      }
    }

    static boolean findName(String tweet, List<String> names) {
      String[] tokens = tokenize(tweet);
      Map<String, Integer> voc = new HashMap<>();
      for (String name : names) {
        Integer index = voc.get(name);
        if (index != null) {
          return false;
        }
        voc.put(name, voc.size());
      }

      int[] indices = convertUsingVoc(tokens, voc);
      int iName = 0;
      for (int iToken = 0; iToken < indices.length; iToken++) {
        if (indices[iToken] == iName) {
          iName++;
          if (iName == names.size()) {
            return true;
          }
        } else if (iName > 0) {
          iName = 0;
          iToken--;
        }
      }
      return false;
    }
    
    private static String[] tokenize(String in) {
      List<String> tokens = new ArrayList<String>();
      
      return tokens.toArray(new String[tokens.size()]);
    }

    private static int[] convertUsingVoc(String[] tokens, Map<String, Integer> voc) {
      int[] indices = new int[tokens.length];

      int iword = 0;
      for (String token : tokens) {
        Integer index = voc.get(token);
        if (index == null)
          index = -1;
        indices[iword++] = index;
      }
      return indices;
    }

    boolean searchRotatedMain(int[] x, int key) {
      return searchRotated(x, key, 0, x.length - 1);
    }

    boolean searchRotated(int[] x, int key, int lo, int hi) {
      if (lo > hi) {
        return false;
      }
      if (lo == hi) {
        return x[lo] == key;
      }
      int mid = (lo + hi) / 2;
      assert (mid >= lo && mid < hi);
      if (x[lo] <= x[mid]) {
        // lower part is sorted
        if (key >= x[lo] && key <= x[mid]) {
          return searchRotated(x, key, lo, mid);
        } else {
          return searchRotated(x, key, mid + 1, hi);
        }
      } else {
        // upper part is sorted
        if (key > x[mid] && key <= x[hi]) {
          return searchRotated(x, key, mid + 1, hi);
        } else {
          return searchRotated(x, key, lo, mid);
        }
      }
    }

    int searchRotated(int[] x, int key) {
      int lo = 0, hi = x.length - 1;
      if (lo > hi) {
        return -1;
      }
      while (lo < hi) {
        int mid = (lo + hi) / 2;
        assert (mid >= lo && mid < hi);
        if (x[lo] <= x[mid]) {
          // lower part is sorted
          if (key >= x[lo] && key <= x[mid]) {
            hi = mid;
          } else {
            lo = mid + 1;
          }
        } else {
          // upper part is sorted
          if (key > x[mid] && key <= x[hi]) {
            lo = mid + 1;
          } else {
            hi = mid;
          }
        }
      }
      if (x[lo] == key) {
        return lo;
      }
      return -lo - 1;
    }

    static class Seq {
      int end;
      boolean inc;
      
      Seq(int end, boolean inc) {
        this.end = end;
        this.inc = inc;
      }

      @Override
      public int hashCode() {
        return end * 2 + (inc? 1 : 0);
      }

      @Override
      public boolean equals(Object obj) {
        if (obj == null) {
          return false;
        }
        if (getClass() != obj.getClass()) {
          return false;
        }
        final Seq other = (Seq) obj;
        if (this.end != other.end) {
          return false;
        }
        if (this.inc != other.inc) {
          return false;
        }
        return true;
      }
      
    }
    
    
    static void longestSeq(int[] x) {
      Map<Seq, Integer> map = new HashMap<>();
      Seq seqMax = null;
      int lenMax = 0;
      
      for (int i = 0; i < x.length; i++) {
        int xCur = x[i];
        if (xCur == Integer.MIN_VALUE || xCur == Integer.MAX_VALUE) {
          System.out.printf("Cannot handle value %d\n", xCur);
          return;
        }
        
        Seq seqDec = new Seq(xCur, false);
        Seq seqInc = new Seq(xCur, true);
        Integer lenDec = map.get(seqDec), lenInc = map.get(seqInc);
        if (lenDec == null) {
          lenDec = 0;
        } else {
          seqDec.end = xCur - 1;
        }
        map.put(seqDec, lenDec + 1);
        if (lenDec + 1 > lenMax) {
          seqMax = seqDec;
          lenMax = lenDec + 1;
        }
        if (lenInc == null) {
          lenInc = 0; 
        } else {
          seqInc.end = xCur + 1;
        }
        map.put(seqInc, lenInc + 1);
        if (lenInc + 1 > lenMax) {
          seqMax = seqInc;
          lenMax = lenInc + 1;
        }
      }
      
      if (seqMax == null) {
        System.out.printf("Longest sequence of length: 0\n");
      } else {
        System.out.printf("Longest sequence of length: %d", lenMax);
        int from, to;
        if (seqMax.inc) {
          from = seqMax.end - lenMax;
          to = seqMax.end - 1;
        } else {
          from = seqMax.end + 1;
          to = seqMax.end + lenMax - 1;
        }
        System.out.printf(" : %d - %d\n", from, to);
      }
    }
  }
  
  static class Knapsack {
    static class State {
      int value;
      boolean include;
    }
    
    static void driver() {
      int[] wts = new int[] {1, 2, 4, 6, 8, 10};
      int[] values = new int[] {3, 4, 7, 8, 10, 8};
      
      fit(wts.length, 17, wts, values);
      fit(wts.length, 10, wts, values);
    }
    
    static void fit(int n, int w, int[] wts, int[] values) {
      State[][] states = new State[n + 1][w + 1];
      for (int i = 0; i < n + 1; i++) {
        for (int j = 0; j < w + 1; j++) {
          states[i][j] = new State();
        }
      }
      for (int item = 0; item < n; item++) {
        int wtCur = wts[item];
        for (int wIter = 0; wIter <= w; wIter++) {
          State stateCur = states[item + 1][wIter];
          stateCur.value = states[item][wIter].value;
          if (wtCur <= wIter) {
            int valNew = states[item][wIter - wtCur].value + values[item];
            if (valNew > stateCur.value) {
              stateCur.value = valNew;
              stateCur.include = true;
            }
          }
        }
      }
      traceback(states, wts, n, w);
      System.out.println();
    }
    
    static void traceback(State[][] states, int[] wts, int n, int w) {
      if (n == 0)
        return;
      State stateCur = states[n][w];
      if (stateCur.include) {
        traceback(states, wts, n - 1, w - wts[n - 1]);
        State statePrev = states[n - 1][w - wts[n - 1]];
        System.out.printf("item %3d: wt %3d: val %3d: cum wt: %3d: cum value: %3d\n", n - 1, wts[n - 1], stateCur.value - statePrev.value, w, stateCur.value);
      } else {
        traceback(states, wts, n - 1, w);
      }
    }
  }
  
  static class Knapsack2 {

    static class State {

      int value;
      boolean include;
      boolean explored;
      
      State() {
      }
    }
    
    static class Store {
      private State[][] states;
      
      Store(int n, int w) {
        states = new State[n + 1][w + 1];
        for (int i = 0; i < n + 1; i++) {
          for (int j = 0; j < w + 1; j++) {
            states[i][j] = new State();
          }
        }
      }
      
      boolean isExplored(int n, int w) {
        return states[n][w].explored; 
      }
      
      void set(int n, int w, int value, boolean include) {
        State stateCur = states[n][w];
        stateCur.value = value;
        stateCur.include = include;
        stateCur.explored = true;
      }
      
      int n() {
        return states.length - 1;
      }
      
      int w() {
        return (states.length == 0)? 0 : states[0].length - 1;
      }
      
      int value(int n, int w) {
        return states[n][w].value;
      }
      
      void traceback(int[] wts, int n, int w) {
        if (n == 0) {
          return;
        }
        State stateCur = states[n][w];
        if (stateCur.include) {
          traceback(wts, n - 1, w - wts[n - 1]);
          State statePrev = states[n - 1][w - wts[n - 1]];
          System.out.printf("item %3d: wt %3d: val %3d: cum wt: %3d: cum value: %3d\n", n - 1, wts[n - 1], stateCur.value - statePrev.value, w, stateCur.value);
        } else {
          traceback(wts, n - 1, w);
        }
      }
    }

    static void driver() {
      int[] wts = new int[]{1, 2, 4, 6, 8, 10};
      int[] values = new int[]{3, 4, 7, 8, 10, 8};

      Store store;
      store = new Store(wts.length, 17);
      fit(store, store.n(), store.w(), wts, values);
      store.traceback(wts, store.n(), store.w());
      System.out.println();

      store = new Store(wts.length, 10);
      fit(store, store.n(), store.w(), wts, values);
      store.traceback(wts, store.n(), store.w());
      System.out.println();
    }

    static int fit(Store store, int n, int w, int[] wts, int[] values) {
      if (n == 0)
        return 0; 
      
      if (store.isExplored(n, w)) {
        return store.value(n, w);
      }
      
      int wtCur = wts[n - 1];
      int value = fit(store, n - 1, w, wts, values);
      boolean include = false;
      
      if (wtCur <= w) {
        int valInclude = fit(store, n - 1, w - wtCur, wts, values) + values[n - 1];
        if (valInclude > value) {
          value = valInclude;
          include = true;
        }
      }
      
      store.set(n, w, value, include);

      return value;
    }

  }

}


