package test.unit.test0;

import impl.Master;


import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotEquals;

public class MapReduceTestFull {

	@Test
  public void testMasterReducer() {
      Master master = Master.getInstance();
      assertNotNull(Master.getInstance().reducer);
	}

  @Test
  public void testMasterCores() {
      Master master = Master.getInstance();
      int coreNum = Master.getInstance().returnNumCores();
      assertTrue(coreNum > 0);
  }


}
