package impl;

import java.util.*;
import java.util.OptionalDouble;
import java.util.stream.*;
import java.util.function.DoubleSupplier;

// Basic test modified from:
// http://www.dreamsyssoft.com/java-8-lambda-tutorial/map-reduce-tutorial.php

public class TestMapReduce {
  private static List<User> users = Arrays.asList(
      new User(1, "Steve", "Vai", 40),
      new User(4, "Joe", "Smith", 32),
      new User(3, "Steve", "Johnson", 57),
      new User(9, "Mike", "Stevens", 18),
      new User(10, "George", "Armstrong", 24),
      new User(2, "Jim", "Smith", 40),
      new User(8, "Chuck", "Schneider", 34),
      new User(5, "Jorje", "Gonzales", 22),
      new User(6, "Jane", "Michaels", 47),
      new User(7, "Kim", "Berlie", 60)
    );
  
  private static List<Integer> rand_nums = new ArrayList<Integer>();

  private static final int total_nums = 100000000;

  public static void main(String[] args) {
    long startTimeOld;
    long endTimeOld;
    long startTimeNew;
    long endTimeNew;

    for(int i = 0; i < total_nums; i++) {
      // No need for high-quality random function yet
      rand_nums.add((int)(Math.random() * 100));
    }

    System.out.println("Iteration, Time for iterative, Time for stream");

    for (int i = 1; i <= total_nums; i*=10) {
      startTimeOld = System.nanoTime();
      oldJavaWay();
      endTimeOld = System.nanoTime();

      startTimeNew = System.nanoTime();
      newJavaWay();
      endTimeNew = System.nanoTime();

      System.out.print(i);
      System.out.print(",");
      System.out.print(endTimeOld - startTimeOld);
      System.out.print(",");
      System.out.println(endTimeNew - startTimeNew);
    }
  }

  private static void oldJavaWay() {
    int count = 0;
    double average = 0;

    for (int num : rand_nums) {
      average = (average*((double)count) + (double)num)/((double)(++count));
    }

    // System.out.println("OLD WAY average User Age: " + average);
  }

  private static void newJavaWay() {
    // double average = users.parallelStream().mapToDouble(u -> u.age).average().getAsDouble();
    double average = rand_nums.parallelStream().mapToDouble(u -> u).average().getAsDouble();

    // System.out.println("NEW WAY average User Age: " + average);
  }
}