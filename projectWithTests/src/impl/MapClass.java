package impl;

import java.util.*;
import java.util.stream.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.DoubleSupplier;
import java.io.RandomAccessFile;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

public class MapClass implements Runnable {

    private int map_id;
    private Long start;
    private Long end;
    
    private int word_index_i;
    private int word_index_j;

    private RandomAccessFile file;

    public Map<String, Long> countries_count;

    // private byte[] read_buffer;

    public MapClass(int id, Long start, Long end) {
        this.map_id = id;
        this.start = start;
        this.end = end;
        // this.read_buffer = new byte[(int)(end - start)];
        this.word_index_i = -1;
        this.word_index_j = -1;
        this.countries_count = new HashMap<String, Long>();
    }

    private boolean find_word_index(String word) {
        for (int i = 0; i < Master.getInstance().countries_array.size(); i++) {
            for (int j = 0; j < Master.getInstance().countries_array.get(i).size(); j++) {
                if (word.equals(Master.getInstance().countries_array.get(i).get(j).get(0))) {
                    word_index_i = i;
                    word_index_j = j;
                    return true;
                }
            }
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

    public void run() {
        // This is where the Map runs
        System.out.print("Starting map with id: ");
        System.out.print(map_id);
        System.out.println("");

        try {
            this.file = new RandomAccessFile(Master.getInstance().main_file_path, "r");
        } catch (FileNotFoundException e) {
            System.out.println("Inside map: FileNotFoundException occured");
        } catch (NullPointerException e) {
            System.out.println("CAUGHT NullPointerException");
        }


        // assertNotNull(this.file);


        // Find start of segment
        try { 
            file.seek(start);
            // file.readFully(read_buffer);
        } catch (IOException e) {
            System.out.println("Inside map: IOException occured");
        } catch (NullPointerException e) {
            System.out.println("CAUGHT NullPointerException");
        }


        char curr_char = '0';
        int counter = 0;
        int total = (int)(end - start);
        StringBuilder curr_word = new StringBuilder(30);
        String string_word;
        ArrayList<String> final_string;
        int expected_phrase_size = 0;
        while (true) {
            if (counter >= total) {
                break;
            }

            // Iterate through file bytes until an uppercase character is reached
            try {
                do {
                    curr_char = (char)file.readByte();
                    counter++;
                } while (!Character.isUpperCase(curr_char) && counter < total);
            } catch (IOException e) {
                System.out.println("IOException thrown");
            }

            // aggregate characters into a word
            curr_word.setLength(0);
            curr_word.append(curr_char);
            try {
                while (Character.isLetter(curr_char = (char)file.readByte()) && counter < total) {
                    counter++;
                    curr_word.append(curr_char);
                }
            } catch (IOException e) {
                System.out.println("IOException thrown");
            }

            // loop through all countries to check if the word matches with any of the first words in the sub-names
            string_word = curr_word.toString();
            if (find_word_index(string_word) == false) {
                continue;
            }
            
            // if it does, and if there are more words in the country sub-name, check next bytes in the file
            if (Master.getInstance().countries_array.get(word_index_i).get(word_index_j).size() > 1) {
                expected_phrase_size = 0;
                for (int p = 1; p < Master.getInstance().countries_array.get(word_index_i).get(word_index_j).size(); p++) {
                    expected_phrase_size += 1; // account for spaces
                    expected_phrase_size += Master.getInstance().countries_array.get(word_index_i).get(word_index_j).get(p).length();
                }
                curr_word.append(curr_char); // append the one from previous loop
                for (int l = 0; l < expected_phrase_size && counter < total; l++) {
                    counter++;
                    try {
                        curr_word.append(curr_char = (char)file.readByte());
                    } catch (IOException e) {
                        System.out.println("IOException thrown");
                    }
                }
            }
            final_string = new ArrayList<String>(Arrays.asList(curr_word.toString().split(" ")));
            if (arrays_equal(final_string, Master.getInstance().countries_array.get(word_index_i).get(word_index_j)) == -1) {
                continue;
            }

            // if there is a full match, iterate in a local HashMap the country name (i.e. first subname)
            String hash_name = "";
            for (String s : Master.getInstance().countries_array.get(word_index_i).get(word_index_j)) {
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

        // afterwords, write all these countries and their respective counts into csv file
        for (Map.Entry<String, Long> entry : countries_count.entrySet()) {
            String key = entry.getKey();
            Long value = entry.getValue();
            System.out.println(key + "=" + value);
        }




        // Split by "\\W+" to get all words in stream

        // // Loop through countries
        // for (int i = 0; i < Master.getInstance().countries_array.size(); i++) {
        //     for (int j = 0; j < Master.getInstance().countries_array.get(i).size(); j++) {
        //         for (int k = 0; k < Master.getInstance().countries_array.get(i).get(j).size(); k++) {
        //             System.out.println(Master.getInstance().countries_array.get(i).get(j).get(k));
        //         }
        //     }
        // }


        try {
            this.file.close();
       } catch (FileNotFoundException e) {
            System.out.println("Inside map: FileNotFoundException occured");
        } catch (IOException e) {
            System.out.println("Inside map: IOException occured");
        } catch (NullPointerException e) {
            System.out.println("CAUGHT NullPointerException");
        }


        System.out.print("Ending map with id: ");
        System.out.print(map_id);
        System.out.println("");
    }
}

