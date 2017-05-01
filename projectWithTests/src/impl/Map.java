package impl;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.DoubleSupplier;

public class Map implements Runnable {
    
    private int map_id;
    
    public Map(int id) {
        this.map_id = id;
    }

    public void run() {
        // This is where the Map runs
        for (int i = 0; i < 50; i++) {
            System.out.println(map_id + " on iteration: " + i);
        }
    }
}


