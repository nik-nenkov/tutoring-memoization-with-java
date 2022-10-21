package com.programming.dynamic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

public class BlackAndWhiteSolver {
  private final int rowSize;
  private final int numRows;
  private final ArrayList<boolean[]> possibleRows = new ArrayList<>();
  private final ArrayList<int[]> possibleNextRows = new ArrayList<>();
  private final HashMap<KeyOfTwo, BigInteger> mapOfValues = new HashMap<>();
  private BigInteger numberOfSolutions;

  public BlackAndWhiteSolver(int sideA, int sideB) {
    if (sideA > sideB) {
      numRows = sideA;
      rowSize = sideB;
    } else {
      numRows = sideB;
      rowSize = sideA;
    }
  }

  private static boolean[] mutate(boolean[] a, int i) {
    a[i] = !a[i];
    return a;
  }

  private static boolean compare(boolean[] a, boolean[] b) {
    boolean check = true;
    for (int i = 0; i < a.length; i++) {
      if (a[i] == b[i] && b[i]) {
        check = false;
        break;
      }
    }
    return check;
  }

  private BigInteger countAll(int currentPosition, int numberOfRows, int currentRow) {

    KeyOfTwo k = new KeyOfTwo(currentPosition, currentRow);

    if (!mapOfValues.containsKey(k)) {
      if (currentPosition == numberOfRows - 1) {
        return BigInteger.valueOf(possibleNextRows.get(currentRow).length);
      } else {
        BigInteger result = BigInteger.valueOf(0);
        for (int i = 0; i < possibleNextRows.get(currentRow).length; i++) {
          result =
              result.add(
                  countAll(currentPosition + 1, numberOfRows, possibleNextRows.get(currentRow)[i]));
        }
        mapOfValues.putIfAbsent(k, result);
        return result;
      }
    } else {
      return mapOfValues.get(k);
    }
  }

  private int findPossibleRows(boolean[] r, int j) {
    if ((j == 0 || !r[j - 1]) && j < r.length) {
      return findPossibleRows(r, j + 1) + findPossibleRows(mutate(r, j), j + 1);
    } else if (j == r.length) {
      boolean[] m = r.clone();
      possibleRows.add(m);
      return 1;
    } else {
      r[j] = false;
      return findPossibleRows(r, j + 1);
    }
  }

  private void computeNextRows() {
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

  public int getRowSize() {
    return rowSize;
  }

  public int getNumRows() {
    return numRows;
  }

  public BlackAndWhiteSolver findNumberOfPossibleSolutions() {
    this.findPossibleRows(new boolean[this.getRowSize()], 0);
    this.computeNextRows();
    this.numberOfSolutions = this.countAll(0, this.getNumRows(), 0);
    return this;
  }

  public BigInteger getNumberOfSolutions() {
    return numberOfSolutions;
  }

  @Data
  @EqualsAndHashCode
  @AllArgsConstructor
  private static class KeyOfTwo {
    private final Integer curPos;
    private final Integer curRow;
  }
}
