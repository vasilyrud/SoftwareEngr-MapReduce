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
    oldJavaWay();
    newJavaWay();
  }

  private static void oldJavaWay() {
    int sum = 0;

    for (User u : users) {
      sum += u.age;
    }

    System.out.println("OLD WAY Sum User Age: " + sum);
  }

  private static void newJavaWay() {
    // int sum = users.parallelStream().map(u -> u.age).reduce(0, (a, b) -> a+b);
    int sum = users.parallelStream().mapToInt(u -> u.age).sum();

    System.out.println("NEW WAY Sum User Age: " + sum);
  }
}