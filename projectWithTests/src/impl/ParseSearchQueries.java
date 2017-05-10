package impl;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.DoubleSupplier;
import java.util.Arrays;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.String;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ParseSearchQueries {
    public ParseSearchQueries() {
        
    }

    public void parseFileIntoArray(String filename, List<List<List<String>>> array, HashMap<String, List<Integer>> hash_map) {
        /* 

        // Searchquery
        ArrayList<
            // Sub-names
            ArrayList<
                // Name
                ArrayList<
                    // Word
                    String
                >
            >
        >

        To get a particular searchquery (ArrayList<ArrayList<String>>):
        Searchquery.get(i)

        To get a particular searchquery sub-name (ArrayList<String>):
        Searchquery.get(i).get(j)

        To get a particular word in the searchquery sub-name (String):
        Searchquery.get(i).get(j).get(k)

        */
        // Loop through all lines/search queries
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            int i = 0;
            for (String line; (line = br.readLine()) != null; i++) {
                // line -> ArrayList<ArrayList<String>>

                // Add searchquery slot
                array.add(new ArrayList<List<String>>());

                // Store temporary split by search query name without splitting by spaces
                List<String> tmp_searchquery_split = Arrays.asList(line.split(",")); 

                // Split each searchquery by sub-names
                for (int j = 0; j < tmp_searchquery_split.size(); j++) {
                    // Within searchquery slot add a sub-name
                    array.get(i).add(new ArrayList<String>());

                    List<String> tmp_subname_split = Arrays.asList(tmp_searchquery_split.get(j).split("\\s+"));

                    for (int k = 0; k < tmp_subname_split.size(); k++) {
                        array.get(i).get(j).add(new String());
                        array.get(i).get(j).set(k, tmp_subname_split.get(k));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException occured");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("IOException occured");
        }

        // Make a shortcut hash map of all searchquery indices
        for (int m = 0; m < array.size(); m++) {
            for (int n = 0; n < array.get(m).size(); n++) {
                hash_map.put(array.get(m).get(n).get(0), new ArrayList<Integer>());
                hash_map.get(array.get(m).get(n).get(0)).add(m);
                hash_map.get(array.get(m).get(n).get(0)).add(n);
            }
        }
    }


}

