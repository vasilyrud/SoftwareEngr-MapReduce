package impl;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.DoubleSupplier;

// Singleton because there is only one Master
public class Master {
    
    private int num_cores;
    private ExecutorService thread_pool;
    
    // Make a list of pairs of indices to various parts of the file

    // Need to somehow keep track of threads and what they are up to

    // Constructor
    private Master() {
        this.num_cores = Runtime.getRuntime().availableProcessors() - 1;
        this.thread_pool = Executors.newFixedThreadPool(num_cores);
    }

    // Internal singleton method that stores the only class instance
    private static class MasterHolder {
        private static final Master INSTANCE = new Master();
    }

    // Public interface to instantiate singleton internally
    public static Master getInstance() {
        return MasterHolder.INSTANCE;
    }

    // Return calculated number of cores available to Java
    public int returnNumCores() {
        return num_cores;
    }

    public void runLoops() {
        for (int i = 0; i < 4; i++) {
         thread_pool.execute(new Map(i));
       }

       // Need to decide when to shutdown
    }
}

