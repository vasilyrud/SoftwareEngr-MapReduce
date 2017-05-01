package impl;

import java.util.*;
import java.util.stream.*;
import java.util.function.DoubleSupplier;

public class Master {
    
    private int num_cores;

    public Master() {
        this.num_cores = Runtime.getRuntime().availableProcessors();
    }
}



