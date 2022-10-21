package com.programming.dynamic;

import java.util.Scanner;

public class BlackAndWhite {
  public static void main(String[] args) {
    try (Scanner scanner = new Scanner(System.in)) {
      System.out.println(
          "Depending on hardware, "
              + "maximum computable (in reasonable time) board size is around 16x32 or 10x2000");

      System.out.print("Side A=");
      var sideA = scanner.nextInt();

      System.out.print("Side B=");
      var sideB = scanner.nextInt();

      System.out.println(
          "Result is "
              + new BlackAndWhiteSolver(sideA, sideB)
                  .findNumberOfPossibleSolutions()
                  .getNumberOfSolutions()
                  .toString());
    } catch (Exception e) {
      System.out.println("There was a problem with your scanner");
      System.exit(-1);
    }
  }
}
