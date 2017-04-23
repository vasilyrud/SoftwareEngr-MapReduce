package test_mr;

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

  public static void main(String[] args) {
    long startTimeOld = System.nanoTime();
    oldJavaWay();
    long endTimeOld = System.nanoTime();

    long startTimeNew = System.nanoTime();
    newJavaWay();
    long endTimeNew = System.nanoTime();

    System.out.print("Time for iterative: ");
    System.out.println(endTimeOld - startTimeOld);

    System.out.print("Time for stream:    ");
    System.out.println(endTimeNew - startTimeNew);
  }

  private static void oldJavaWay() {
    int sum = 0;

    for (User u : users) {
      sum += u.age;
    }

    double average = (double) sum / users.size();

    System.out.println("OLD WAY average User Age: " + average);
  }

  private static void newJavaWay() {
    double average = users.parallelStream().mapToDouble(u -> u.age).average().getAsDouble();

    System.out.println("NEW WAY average User Age: " + average);
  }
}