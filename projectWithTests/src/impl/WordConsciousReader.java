package impl;

import api.Reader;

import java.util.ArrayList;
import java.util.List;
import java.io.RandomAccessFile;
import java.io.FileNotFoundException;
import java.io.IOException;

/* Divides the source file into blocks via pointer positions.
However, avoids landing in the middle of a word or at least in
the middle of a word that is less that 100 char long, since presumably
no search query would exceed that. */ 

public class WordConsciousReader implements Reader {

    public int BLOCK_SIZE;

    // constructor
    public WordConsciousReader(int blockSize) { 
        this.BLOCK_SIZE = blockSize*1000000;
    }

    public void makeIndexArray(String filename, List<List<Long>> array) {
        try {
            RandomAccessFile file = new RandomAccessFile(filename, "r");
            System.out.println("Partitioning file");

            char testChar = '0';
            byte [] testLength; 
            int counter = 0;
            int maxSearchQueryLength = 100;
            int prev_counter = 0;

            // Distribute the main blocks of BLOCK_SIZE each
            for (long i = 0; i < file.length()/BLOCK_SIZE; i++) {
                testLength = new byte[maxSearchQueryLength];
                file.seek((i+1)*BLOCK_SIZE - 2);
                file.read(testLength);
                prev_counter = counter;
                counter = 0;

                do{
                    testChar = (char)testLength[counter];
                    counter++;
                }while(Character.isLetter(testChar) && counter < maxSearchQueryLength - 1);
                   
                array.add(new ArrayList<Long>());
                array.get((int)i).add(i*BLOCK_SIZE + prev_counter); // start of block
                array.get((int)i).add((i+1)*BLOCK_SIZE + counter - 1); // end of block

                // System.out.println("Made block from " + array.get((int)i).get(0) + " to " + array.get((int)i).get(1));
            }

            // Make the final block that is smaller than the rest
            array.add(new ArrayList<Long>());
            array.get((int)(file.length()/BLOCK_SIZE)).add((long)(file.length()/BLOCK_SIZE)*BLOCK_SIZE);
            array.get((int)(file.length()/BLOCK_SIZE)).add((long)file.length() - 1);

            file.close();
        } catch (FileNotFoundException e) {
            System.out.println("Reader: FileNotFoundException occured");
             System.exit(0);
        } catch (IOException e) {
            System.out.println("IOException occured");
        }
    }

}
