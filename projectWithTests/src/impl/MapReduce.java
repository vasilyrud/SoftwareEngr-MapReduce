package impl;

import java.util.*;
import java.util.OptionalDouble;
import java.util.stream.*;
import java.util.function.DoubleSupplier;

// Basic test modified from:
// http://www.dreamsyssoft.com/java-8-lambda-tutorial/map-reduce-tutorial.php

public class MapReduce {


  public static double oldJavaWay(List<Integer> inputNums) {
    int count = 0;
    double average = 0;

    for (int num : inputNums) {
      average = (average*((double)count) + (double)num)/((double)(++count));
    }

    // System.out.println("OLD WAY average User Age: " + average);
    return average;
  }

  public static double newJavaWay(List<Integer> inputNums) {
    // double average = users.parallelStream().mapToDouble(u -> u.age).average().getAsDouble();
    double average = inputNums.parallelStream().mapToDouble(u -> u).average().getAsDouble();

    // System.out.println("NEW WAY average User Age: " + average);
    return average;
  }
     
}