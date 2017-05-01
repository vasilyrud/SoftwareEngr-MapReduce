package impl;

import java.util.*;
import java.util.OptionalDouble;
import java.util.stream.*;
import java.util.function.DoubleSupplier;

public class MapReduce {


  public static double oldJavaWay(List<Integer> inputNums) {
    int count = 0;
    double average = 0;

    for (int num : inputNums) {
      average = (average*((double)count) + (double)num)/((double)(++count));
    }

    return average;
  }

  public static double newJavaWay(List<Integer> inputNums) {
    // double average = users.parallelStream().mapToDouble(u -> u.age).average().getAsDouble();
    double average = inputNums.parallelStream().mapToDouble(u -> u).average().getAsDouble();

    return average;
  }
     
}