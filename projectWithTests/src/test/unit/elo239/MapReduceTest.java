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


}
