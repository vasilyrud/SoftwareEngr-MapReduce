package impl;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.DoubleSupplier;
import java.io.RandomAccessFile;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

public class ReduceClass {

    public Map<String, Long> countries_count;

    // Constructor
    public ReduceClass() {

    }

    public void runReduce() {

        countries_count = new HashMap<String, Long>();

        // Loop through all map outputs
        for (int f = 0; f < Master.getInstance().index_array.size(); f++) {
            try (BufferedReader br = new BufferedReader(new FileReader(Master.getInstance().cached_map_output + "/map" + f + ".csv"))) {
                // Loop through lines
                for (String line; (line = br.readLine()) != null;) {

                    // Split line by comma
                    List<String> tmp_country_split = Arrays.asList(line.split(","));

                    // Get country name and amount
                    String hash_name = tmp_country_split.get(0);
                    Long hash_amount = Long.parseLong(tmp_country_split.get(1));
                    // Check if country is already in hashMap
                    Long curr_value = countries_count.get(hash_name);
                    if (curr_value == null) {
                        countries_count.put(hash_name, hash_amount);
                    } else {
                        countries_count.put(hash_name, curr_value + hash_amount);
                    }

                }
            } catch (FileNotFoundException e) {
                System.out.println("FileNotFoundException in ReduceClass for map_id: " + f);
            } catch (IOException e) {
                System.out.println("IOException in ReduceClass for map_id: " + f);
            }
        }



        try {
            Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(Master.getInstance().REDDIR+"/reduce.csv"), "utf-8"));

            for (Map.Entry<String, Long> entry : countries_count.entrySet()) {
                String key = entry.getKey();
                Long value = entry.getValue();
                writer.write(key + "," + value + "\n");
            }

            writer.close();
        } catch (IOException ex) {
            System.out.println("Inside ReduceClass: IOException");
        }

    }


}
