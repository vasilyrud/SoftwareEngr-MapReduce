package impl;

import api.Reader;

import java.util.ArrayList;
import java.util.List;
import java.io.RandomAccessFile;
import java.io.FileNotFoundException;
import java.io.IOException;

public class BlockReader implements Reader {

    public final int BLOCK_SIZE = 5000000; // 5 MB

    // constructor
    public BlockReader() {

    }

    public void makeIndexArray(String filename, List<List<Long>> array) {
        try {
            RandomAccessFile file = new RandomAccessFile(filename, "r");
            System.out.println("Partitioning file");

            // Distribute the main blocks of BLOCK_SIZE each
            for (long i = 0; i < file.length()/BLOCK_SIZE; i++) {
                array.add(new ArrayList<Long>());
                array.get((int)i).add(i*BLOCK_SIZE); // start of block
                array.get((int)i).add((i+1)*BLOCK_SIZE - 1); // end of block
            }

            // Make the final block that is smaller than the rest
            array.add(new ArrayList<Long>());
            array.get((int)(file.length()/BLOCK_SIZE)).add((long)(file.length()/BLOCK_SIZE)*BLOCK_SIZE);
            array.get((int)(file.length()/BLOCK_SIZE)).add((long)file.length() - 1);

            file.close();
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException occured");
        } catch (IOException e) {
            System.out.println("IOException occured");
        }
    }

}
