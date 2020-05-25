package com.programming.dynamic;

import lombok.extern.java.Log;

import java.util.Scanner;

@Log
public class BlackAndWhite {
    public static void main(String[] args) {
        var scanner = new Scanner(System.in);

        var sideA = scanner.nextInt();
        var sideB = scanner.nextInt();

        var solver = new BlackAndWhiteSolver(sideA, sideB);

        solver.findNumberOfPossibleSolutions();
        log.info(solver.getNumberOfSolutions().toString());
    }
}