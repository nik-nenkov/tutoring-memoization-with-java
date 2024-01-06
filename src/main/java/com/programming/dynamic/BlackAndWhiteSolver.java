package com.programming.dynamic;

import lombok.Getter;
import lombok.extern.java.Log;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

@Log
public class BlackAndWhiteSolver {
    private final int rowSize;
    private final int numRows;
    private final ArrayList<boolean[]> possibleRows = new ArrayList<>();
    private final ArrayList<int[]> possibleNextRows = new ArrayList<>();
    private final HashMap<KeyOfTwo, BigInteger> mapOfValues = new HashMap<>();

    @Getter
    private final long numberOfPossibleRows;

    @Getter
    private BigInteger numberOfSolutions;

    public BlackAndWhiteSolver(int sideA, int sideB) {
        if (sideA > sideB) {
            numRows = sideA;
            rowSize = sideB;
        } else {
            numRows = sideB;
            rowSize = sideA;
        }
        final boolean[] startingWithEmptyRow = new boolean[this.rowSize];
        // we start validating rows at position zero:
        final int startingPositionInsideRow = 0;
        // using this function we find all possible rows with the given length that satisfy the rule:
        this.numberOfPossibleRows = this.findPossibleRows(startingWithEmptyRow, startingPositionInsideRow);
    }

    private static boolean compare(boolean[] a, boolean[] b) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == b[i] && b[i]) {
                return false;
            }
        }
        return true;
    }

    private BigInteger countAll(int currentPosition, int numberOfRows, int currentRow) {

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

    private long findPossibleRows(boolean[] rowUnderConsideration, int currentPosition) {
        final int positionToTheLeft = currentPosition - 1;
        final int positionToTheRight = currentPosition + 1;

        if (currentPosition == rowSize) {
            // we have reached the maximum size of the rows and we have found one new possible row
            // we add it to the list of possible rows
            possibleRows.add(rowUnderConsideration.clone());
            // and we return one to signal that a new row was found
            return 1;
        } else if (positionToTheLeft>=0 && rowUnderConsideration[positionToTheLeft]) {
            // this last option is for when we have not reached the end but our previous square was not blank
            // then our only option here is to go for a blank since no two adjacent can be both 'true'
            rowUnderConsideration[currentPosition] = false;
            return findPossibleRows(rowUnderConsideration, positionToTheRight);
        } else {
            //if the previous square was 'false' or blank or we are at the first square
            //then we can look for possible rows that start with this square but also with an opposite one
            long possibleRowsStartingWithThisSquare = findPossibleRows(rowUnderConsideration, positionToTheRight);
            // we flip the state of the current position
            rowUnderConsideration[currentPosition] = !rowUnderConsideration[currentPosition];
            // and we go find the possible rows starting with that
            long possibleRowsStartingWithOppositeSquare = findPossibleRows(rowUnderConsideration, positionToTheRight);
            // returning the sum of the two
            return possibleRowsStartingWithThisSquare + possibleRowsStartingWithOppositeSquare;
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

    public void findNumberOfPossibleSolutions() {
        // having all possible rows computed we can start comparing them to find how many are compatible
        this.computeNextRows();
        this.numberOfSolutions = this.countAll(0, this.numRows, 0);
    }

    private record KeyOfTwo(Integer curPos, Integer curRow) {
    }
}
