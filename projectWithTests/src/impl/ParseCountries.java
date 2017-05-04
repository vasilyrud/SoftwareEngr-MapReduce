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

public class ParseCountries {
    public ParseCountries() {
        
    }

    public void parseFileIntoArray(String filename, List<List<List<String>>> array) {
        /* 

        // Country
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

        To get a particular country (ArrayList<ArrayList<String>>):
        Countries.get(i)

        To get a particular country sub-name (ArrayList<String>):
        Countries.get(i).get(j)

        To get a particular word in the country sub-name (String):
        Countries.get(i).get(j).get(k)

        */

        // Loop through all lines/countries
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            int i = 0;
            for (String line; (line = br.readLine()) != null; i++) {
                // line -> ArrayList<ArrayList<String>>

                // Add country slot
                array.add(new ArrayList<List<String>>());

                // Store temporary split by country name without splitting by spaces
                List<String> tmp_country_split = Arrays.asList(line.split(",")); 

                // Split each country by sub-names
                for (int j = 0; j < tmp_country_split.size(); j++) {
                    // Within country slot add a sub-name
                    array.get(i).add(new ArrayList<String>());

                    List<String> tmp_subname_split = Arrays.asList(tmp_country_split.get(j).split("\\s+"));

                    for (int k = 0; k < tmp_subname_split.size(); k++) {
                        array.get(i).get(j).add(new String());
                        array.get(i).get(j).set(k, tmp_subname_split.get(k));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException occured");
        } catch (IOException e) {
            System.out.println("IOException occured");
        }
    }
}

