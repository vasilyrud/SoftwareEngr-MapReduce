package impl;

import java.util.*;
import java.util.stream.*;
import java.util.function.DoubleSupplier;

// Singleton because there is only one Master
public class Master {
    
    private int num_cores;

    // Use ExecutorService to create a thread pool with num_cores threads
    // Somehow keep track of threads nad what they are up to

    private Master() {
        this.num_cores = Runtime.getRuntime().availableProcessors();
    }

    private static class MasterHolder {
        private static final Master INSTANCE = new Master();
    }

    public static Master getInstance() {
        return MasterHolder.INSTANCE;
    }

    public int returnNumCores() {
        return num_cores;
    }
}

