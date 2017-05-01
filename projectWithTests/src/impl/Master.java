package impl;

import java.util.*;
import java.util.stream.*;
import java.util.function.DoubleSupplier;

// Singleton because there is only one Master
public class Master {
    
    private int num_cores;

    private Master() {
        this.num_cores = Runtime.getRuntime().availableProcessors();
    }

    private static class MasterHolder {
        private static final Master INSTANCE = new Master();
    }

    public static Master getInstance() {
        return MasterHolder.INSTANCE;
    }
}

