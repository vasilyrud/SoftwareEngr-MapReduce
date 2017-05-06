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
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

public class MapClass implements Runnable {

    private int map_id;
    private Long start;
    private int total;

    private int word_index_i;
    private int word_index_j;

    private RandomAccessFile file;

    private byte [] buffer;

    public Map<String, Long> countries_count;

    public MapClass(int id, Long start, Long end) {
        this.map_id = id;
        this.start = start;
        this.total = (int)(end - start);
        this.buffer = new byte[total];
        this.word_index_i = -1;
        this.word_index_j = -1;
        this.countries_count = new HashMap<String, Long>();
    }

    // This is where the Map runs
    public void run() {
        System.out.printf("Starting map with id: %d.%n", map_id);
        Long startTime = System.currentTimeMillis();

        // retrieve the file block to be processed
        this.fileInit(start, buffer);

        char curr_char = '0';
        int byte_counter = 0;
        int counter = 0;
        StringBuilder curr_word = new StringBuilder(30);
        String string_word;
        List<String> potential_match;
        ArrayList<String> final_string;
        int expected_phrase_size = 0;

    
        while (counter < total) {

            // Iterate through file bytes until an uppercase character is reached
            do {
                curr_char = (char)buffer[counter];
                counter++;
            } while (!Character.isUpperCase(curr_char) && counter < total);

            // aggregate characters into a word
            curr_word.setLength(0);
            curr_word.append(curr_char);
            while (counter < total && Character.isLetter(curr_char = (char)buffer[counter])) {
                counter++;
                curr_word.append(curr_char);
            }
            
            // loop through all countries to check if the word matches with any of the first words in the sub-names
            // if it does identify potential match country name
            string_word = curr_word.toString();
            if (!find_word_index(string_word)) continue;   
            else  potential_match = Master.getInstance().countries_array.get(word_index_i).get(word_index_j);

            // if there are more words in the country sub-name, then check next bytes in the file
            if (potential_match.size() > 1) {
                expected_phrase_size = 0;
                for (int p = 1; p < potential_match.size(); p++) {
                    expected_phrase_size += 1; // account for spaces
                    expected_phrase_size += potential_match.get(p).length();
                }
                curr_word.append(curr_char); // append the one from previous loop
                for (int l = 0; counter < total && l < expected_phrase_size; l++) {
                    counter++;
                    curr_word.append(curr_char = (char)buffer[counter]);
                }
            }

            // check if the string matches expected country name
            final_string = new ArrayList<String>(Arrays.asList(curr_word.toString().split(" ")));
            if (arrays_equal(final_string, potential_match) == -1) {
                continue;
            }

            // if there is a full match, iterate in a local HashMap the country name (i.e. first subname)
            String hash_name = "";
            for (String s : potential_match) {
                hash_name += s + " ";
            }
            hash_name = hash_name.substring(0, hash_name.length() - 1);
            Long curr_value = countries_count.get(hash_name);
            if (curr_value == null) {
                countries_count.put(hash_name, 1L);
            } else {
                countries_count.put(hash_name, curr_value + 1L);
            }
        }

        // afterwards, write all these countries and their respective counts into csv file
        try {
            Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(Master.getInstance().MAPDIR+"/map"+map_id+".csv"), "utf-8"));

            for (Map.Entry<String, Long> entry : countries_count.entrySet()) {
                String key = entry.getKey();
                Long value = entry.getValue();
                writer.write(key + "," + value + "\n");
            }

            writer.close();
        } catch (IOException ex) {
            System.out.println("Inside MapClass: IOException");
        }


        try {
            this.file.close();
        } catch (FileNotFoundException e) {
            System.out.println("Inside map: FileNotFoundException occured");
        } catch (IOException e) {
            System.out.println("Inside map: IOException occured");
        } catch (NullPointerException e) {
            System.out.println("CAUGHT NullPointerException");
        }

        Long stopTime = System.currentTimeMillis();
        Long elapsedTime = stopTime - startTime;
        System.out.println("Timing map with id: " + map_id + ", milli-seconds ran: " + elapsedTime.floatValue());
    }

    private void fileInit(Long start, byte [] buffer){
        // read file
        try {
            this.file = new RandomAccessFile(Master.getInstance().main_file_path, "r");
        } catch (FileNotFoundException e) {
            System.out.println("Inside map: FileNotFoundException occured");
        } catch (NullPointerException e) {
            System.out.println("CAUGHT NullPointerException");
        }

        // Find start of segment and read the block
        try {
            file.seek(start);
            file.read(buffer);
        } catch (IOException e) {
            System.out.println("Inside map: IOException occured");
        } catch (NullPointerException e) {
            System.out.println("CAUGHT NullPointerException");
        }
    }

    /****** HELPERS *****/

    private boolean find_word_index(String word) {
    if (Master.getInstance().countries_indices.containsKey(word)) {
        word_index_i = Master.getInstance().countries_indices.get(word).get(0);
        word_index_j = Master.getInstance().countries_indices.get(word).get(1);
        return true;
    }
    return false;
    }

    private int arrays_equal(List<String> array_1, List<String> array_2) {
        if (array_1.size() != array_2.size()) {
            return -1;
        }
        for (int i = 0; i < array_1.size(); i++) {
            if (!array_1.get(i).equals(array_2.get(i))) {
                return -1;
            }
        }
        return 1;
    }
}


