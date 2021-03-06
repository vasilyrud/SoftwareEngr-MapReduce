package impl;

import java.util.*;
import java.util.stream.*;
import java.util.function.DoubleSupplier;
import java.lang.Integer;

public class RunMapReduce {
    public static void main(String[] args) {
      
        String src_file_path;
        String search_file_path;
        int block_size;
        
        if (args[0].length() != 11 && args[1].length() != 8 && args[2].length() != 12) {
            // use args
            src_file_path = args[0];
            search_file_path = args[1];
            block_size = Integer.parseInt(args[2]);
        } else {
            // get the source file path
            Scanner reader = new Scanner(System.in);
            System.out.println("Enter your source file (XML) path: ");
            src_file_path = reader.nextLine();

            // get the search file path
            System.out.println("Enter your search file (csv) path: ");
            search_file_path = reader.nextLine();

            // get the map block size
            System.out.println("Enter desired block size (MB): ");
            block_size = reader.nextInt();
        }

        /*********************************************
            ADJUSTABLE SETTINGS
        *********************************************/
            // 616 MB wiki xml source file (Windows)
            //String src_file_path = "C:/Users/USER/Documents/Software_Engineering/SoftwareEngr-MapReduce/smallWiki.xml";
            // 58 GB wiki xml file on hard drive (Mac)
            //String file_path = "/Volumes/Samsung_T3/enwiki-20170420-pages-articles.xml";

            //String search_file_path = "data/AllCountries.csv";

            // block size in MB for splititng up the file
            //int block_size = 50;
        /*******************************************/
        
        // standard map output setting
        String mapDir = "map_output";
        //access catched map output on external hard drive (Mac)
        //String MAPDIR = "/Volumes/Samsung_T3/map_output";

        // reduce output folder path
        String reduceDir = "reduce_output";    

        Runtime runtime = Runtime.getRuntime();
        System.out.println("Total Memory (MB): " + runtime.totalMemory()/(1024*1024));
        System.out.println("Max Memory (MB): " + runtime.maxMemory()/(1024*1024));
        
        Master master = Master.getInstance();
        master.init(src_file_path, search_file_path, mapDir, reduceDir, block_size);
        master.read_file();
        master.get_searchquery();

        Long startTime = System.currentTimeMillis();

        master.runMap();
        master.runReduce();
        
        Long stopTime = System.currentTimeMillis();
        Long elapsedTime = stopTime - startTime;
        System.out.println("Total time taken (seconds): " + elapsedTime.floatValue()/1000L);
        System.out.println("The final results are stored in " + reduceDir + " folder.");
    }
}


