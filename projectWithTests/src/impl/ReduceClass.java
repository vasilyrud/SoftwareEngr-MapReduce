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

    private int word_index_i;
    private int word_index_j;

    public Map<String, Long> countries_count;

    // Constructor
    public ReduceClass() {
        this.word_index_i = -1;
        this.word_index_j = -1;
    }

    private boolean find_word_index(String word) {
        if (Master.getInstance().countries_indices.containsKey(word)) {
            word_index_i = Master.getInstance().countries_indices.get(word).get(0);
            word_index_j = Master.getInstance().countries_indices.get(word).get(1);
            return true;
        }
        return false;
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
                    String country_name = tmp_country_split.get(0);
                    Long hash_amount = Long.parseLong(tmp_country_split.get(1));

                    // Find index of the first word in the country name
                    if (find_word_index(country_name.split(" ")[0])) {

                    }

                    // Get the canonical name to use for this country
                    String hash_name = "";
                    for (String s : Master.getInstance().countries_array.get(word_index_i).get(0)) {
                        hash_name += s + " ";
                    }
                    hash_name = hash_name.substring(0, hash_name.length() - 1);


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
