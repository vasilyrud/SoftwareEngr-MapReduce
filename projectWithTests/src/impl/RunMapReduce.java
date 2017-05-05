package impl;

import java.util.*;
import java.util.stream.*;
import java.util.function.DoubleSupplier;

public class RunMapReduce {
    public static void main(String[] args) {
        String file_path = "/Volumes/Samsung_T3/enwiki-20170420-pages-articles.xml";

        Runtime runtime = Runtime.getRuntime();
        System.out.println("Total Memory (MB): " + runtime.totalMemory()/(1024*1024));
        System.out.println("Max Memory (MB): " + runtime.maxMemory()/(1024*1024));

        Master master = Master.getInstance();
        master.read_file(file_path);
        master.get_countries();
        master.runMap();
    }
}


