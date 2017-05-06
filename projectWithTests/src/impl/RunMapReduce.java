package impl;

import java.util.*;
import java.util.stream.*;
import java.util.function.DoubleSupplier;

public class RunMapReduce {
    public static void main(String[] args) {

        Runtime runtime = Runtime.getRuntime();
        System.out.println("Total Memory (MB): " + runtime.totalMemory()/(1024*1024));
        System.out.println("Max Memory (MB): " + runtime.maxMemory()/(1024*1024));

        Master master = Master.getInstance();
        master.read_file();
        master.get_countries();

        Long startTime = System.currentTimeMillis();
        master.runMap();
        Long stopTime = System.currentTimeMillis();
        Long elapsedTime = stopTime - startTime;
        System.out.println("Total time taken (seconds): " + elapsedTime.floatValue()/1000L);

        master.runReduce();
    }
}


