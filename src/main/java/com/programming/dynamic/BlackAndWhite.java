package com.programming.dynamic;

import lombok.extern.java.Log;

import java.util.Scanner;

@Log
public class BlackAndWhite {
    public static void main(String[] args) {
        var scanner = new Scanner(System.in);

        System.out.println("Depending on hardware, " +
                "maximum computable (in reasonable time) board size is around 16x32 or 10x2000");

        System.out.print("Side A=");
        var sideA = scanner.nextInt();

        System.out.print("Side B=");
        var sideB = scanner.nextInt();

        var solver = new BlackAndWhiteSolver(sideA, sideB);

        solver.findNumberOfPossibleSolutions();
        log.info(solver.getNumberOfSolutions().toString());
    }
}