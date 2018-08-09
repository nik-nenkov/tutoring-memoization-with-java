package com.programming.dynamic;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class BlackAndWhite {

    private static ArrayList<boolean[]> possibleRows = new ArrayList<>();
    private static ArrayList<int[]> possibleNextRows = new ArrayList<>();
    private static HashMap<KeyOfTwo, BigInteger> mapOfValues = new HashMap<>();
    // private static HashMap<HashMap<Integer,Integer>, BigInteger> mapOfValues = new HashMap<>();

    private static void computeNextRows() {
        for (int i = 0; i < possibleRows.size(); i++) {
            int k = 0;
            for (boolean[] r : possibleRows) {
                if (compare(possibleRows.get(i), r)) {
                    k++;
                }
            }
            int[] pnm = new int[k];
            int p = 0;
            for (int j = 0; j < possibleRows.size(); j++) {
                if (compare(possibleRows.get(i), possibleRows.get(j))) {
                    pnm[p] = j;
                    p++;
                }
            }
            possibleNextRows.add(pnm);
        }
    }

    private static boolean compare(boolean[] a, boolean[] b) {
        if (a.length != b.length) {
            return false; //TODO this line of code never executes !!!
        } else {
            boolean check = true;
            for (int i = 0; i < a.length; i++) {
                if (a[i] == b[i] && b[i]) {
                    check = false;
                }
            }
            return check;
        }
    }

    private static boolean[] mutate(boolean[] a, int i) {
        a[i] = !a[i];
        return a;
    }

    private static int ways(boolean[] r, int j) {
        if ((j == 0 || !r[j - 1]) && j < r.length) {
            return ways(r, j + 1) + ways(mutate(r, j), j + 1);
        } else if (j == r.length) {
            boolean[] m = r.clone();
            possibleRows.add(m);
            return 1;
        } else {
            r[j] = false;
            return ways(r, j + 1);
        }
    }

    private static BigInteger countAll(int currentPosition, int numberOfRows, int currentRow) {
        // HashMap<Integer,Integer> hm = new HashMap<>();
        // hm.put(currentPosition, currentRow);

        KeyOfTwo k = new KeyOfTwo(currentPosition, currentRow);

        if (!mapOfValues.containsKey(k)) {
            if (currentPosition == numberOfRows - 1) {
                return BigInteger.valueOf(possibleNextRows.get(currentRow).length);
            } else {
                BigInteger result = BigInteger.valueOf(0);
                for (int i = 0; i < possibleNextRows.get(currentRow).length; i++) {
                    result = result.add(countAll(currentPosition + 1, numberOfRows, possibleNextRows.get(currentRow)[i]));
                }
                mapOfValues.putIfAbsent(k, result);
                return result;
            }
        } else {
            return mapOfValues.get(k);
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int rowSize = sc.nextInt();
        int numRows = sc.nextInt();
        if (rowSize > numRows) {
            int swap = rowSize;
            rowSize = numRows;
            numRows = swap;
        }
        boolean[] row = new boolean[rowSize];
        ways(row, 0);
        computeNextRows();
        System.out.println(countAll(0, numRows, 0));
    }

}