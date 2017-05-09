package impl;

import java.util.*;
import java.util.stream.*;
import java.util.function.DoubleSupplier;

public class RunMapReduce {
    public static void main(String[] args) {
        /*********************************************
            ADJUSTABLE SETTINGS
        *********************************************/
            // 616 MB wiki xml source file (Windows)
            String src_file_path = "C:/Users/USER/Documents/Software Engineering/SoftwareEngr-MapReduce/smallWiki.xml";
            // 58 GB wiki xml file on hard drive (Mac)
            //String file_path = "/Volumes/Samsung_T3/enwiki-20170420-pages-articles.xml";

            // standard map output setting
            String mapDir = "map_output";
            //access catched map output on external hard drive (Mac)
            //String MAPDIR = "/Volumes/Samsung_T3/map_output";

            // reduce output folder path
            String reduceDir = "reduce_output";

            // block size in MB for splititng up the file
            int block_size = 100;

        /*******************************************/    

        Runtime runtime = Runtime.getRuntime();
        System.out.println("Total Memory (MB): " + runtime.totalMemory()/(1024*1024));
        System.out.println("Max Memory (MB): " + runtime.maxMemory()/(1024*1024));
        
        Master master = Master.getInstance();
        master.init(src_file_path, mapDir, reduceDir, block_size);
        master.read_file();
        master.get_countries();

        Long startTime = System.currentTimeMillis();

        master.runMap();
        master.runReduce();
        
        Long stopTime = System.currentTimeMillis();
        Long elapsedTime = stopTime - startTime;
        System.out.println("Total time taken (seconds): " + elapsedTime.floatValue()/1000L);
    }
}


