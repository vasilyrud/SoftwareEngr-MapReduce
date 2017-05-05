package test.unit.elo239;

import impl.MapReduce;


import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotEquals;

public class MapReduceTest {
	MapReduce mapReduce = new MapReduce();

	@Test
    public void testSmallSample() {
    	List<Integer> nums = new ArrayList<Integer>();
    	for(int i =0; i < 100; i++){
    		nums.add(i);
    	}
    	double newAverage = mapReduce.newJavaWay(nums);
    	double oldAverage = mapReduce.oldJavaWay(nums);
    	assertEquals(newAverage, oldAverage, 0.001);
	}

/*

    }

    @Test
    public void test100Ints() {
    	List<Integer> rand_nums = new ArrayList<Integer>();

	    for(int i = 0; i < 100; i++) {
	      // No need for high-quality random function yet
	      rand_nums.add((int)(Math.random() * 100));
	    }

    }



  public static final int total_nums = 100000000;
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
  */

}
