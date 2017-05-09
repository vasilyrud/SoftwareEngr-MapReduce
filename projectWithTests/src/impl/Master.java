package impl;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.DoubleSupplier;
import java.io.RandomAccessFile;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.RejectedExecutionException;
import java.util.HashMap;
import java.util.Map;
import java.io.File;

import api.Reader;

// Singleton because there is only one Master
public class Master {

    public String SRCFILE;
    public String MAPDIR;
    public String REDDIR;
    private String SEARCHFILE;

    private int block_size;  
    public RandomAccessFile file;
    private Reader file_reader;

    private int num_cores;
    private int queue_size;
    private ExecutorService thread_pool;
    private final ParseCountries country_parser;
    

    // Make a list of pairs of indices to various parts of the file
    public List<List<Long>> index_array;

    // Country names to parse
    public List<List<List<String>>> countries_array;
    public HashMap<String, List<Integer>> countries_indices;

    // Need to somehow keep track of threads and what they are up to
    public BlockingQueue<Runnable> thread_queue;

    public ReduceClass reducer;

    // Constructor
    private Master() {
        this.num_cores = Runtime.getRuntime().availableProcessors() - 1;
        this.queue_size = 10;
        this.thread_queue = new ArrayBlockingQueue<Runnable>(queue_size);
        this.thread_pool = new ThreadPoolExecutor(
                                    num_cores,
                                    num_cores,
                                    10,
                                    TimeUnit.SECONDS,
                                    thread_queue
                                );
        this.country_parser = new ParseCountries();
        this.index_array = new ArrayList<List<Long>>();
        this.countries_array = new ArrayList<List<List<String>>>();
        this.countries_indices = new HashMap<String, List<Integer>>();
        this.reducer = new ReduceClass();
    }

    public void init(String srcFilePath, String searchFilePath, String mapDir, String reduceDir, int blockSize){
        this.SRCFILE = srcFilePath;
        this.SEARCHFILE = searchFilePath; //"data/AllCountries.csv"
        this.MAPDIR = mapDir;
        this.REDDIR = reduceDir;
        this.block_size = blockSize; 
        this.file_reader = new BlockReader(block_size);
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

    private void makeOutputDir(String dirname) {
        File dir = new File(dirname);

        // clear directory
        if (dir.exists()) {
            for (File f : dir.listFiles()) {
                f.delete();
            }
            dir.delete();
        }

        // create the directory
        if (dir.mkdir()) {
            System.out.println("Created directory: " + dirname);
        }
    }

    public void runMap() {
        System.out.println("Going through " + index_array.size() + " blocks on " + num_cores + " cores.");
        makeOutputDir(MAPDIR);

        // Execute as many maps as there are file segments
        for (int i = 0; i < index_array.size(); i++) {
            while(true) {
                try {
                    thread_pool.execute(new MapClass(i,
                                                    index_array.get(i).get(0),
                                                    index_array.get(i).get(1)
                                        ));
                    break;
                } catch (RejectedExecutionException e) {
                    System.out.println("Rejected Execution. Thread delayed.");
                    try {
                        TimeUnit.MILLISECONDS.sleep(1000);
                    } catch (InterruptedException te) {
                        System.out.println("InterruptedException thrown");
                    }
                }
            }
        }


        // Need to decide when to shutdown
        thread_pool.shutdown();
        try {
            thread_pool.awaitTermination(30L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.out.println("InterruptedException in master");
        }
    }

    public void runReduce() {
        makeOutputDir(REDDIR);
        reducer.runReduce();
    }

    private void printIndexArray() {
        for (List<Long> pair : index_array) {
            System.out.print(pair.get(0));
            System.out.print(" ");
            System.out.print(pair.get(1));

            System.out.println("");
        }
    }

    private void printCountries() {
        for (List<List<String>> country : countries_array) {
            for (List<String> country_subname : country) {
                for (String word : country_subname) {
                    System.out.print(word);
                    System.out.print("-");
                }
                System.out.print(";");
            }
            System.out.println("");
        }
    }

    public void read_file() {
        file_reader.makeIndexArray(SRCFILE, index_array);
        // printIndexArray();
    }

    public void get_countries() {
        country_parser.parseFileIntoArray(SEARCHFILE, countries_array, countries_indices);
        // printCountries();
    }

}
