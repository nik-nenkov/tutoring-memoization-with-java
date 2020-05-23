package com.programming.dynamic;

import lombok.extern.java.Log;
import lombok.val;

import java.util.Scanner;

@Log
public class BlackAndWhite {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        final int sideA = sc.nextInt();
        final int sideB = sc.nextInt();

        val solver = new BlackAndWhiteSolver(sideA,sideB);

        solver.findNumberOfPossibleSolutions();

        log.info(solver.getNumberOfSolutions().toString());
    }
}