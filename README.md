# SoftwareEngr-MapReduce
MapReduce Usage and Implementation for Software Engineering 2017 Final Project

## Project's purpose
The goal of the project is to implement a rudimentary working MapReduce system. The purpose of implementing MapReduce is to understand the algorithm in-depth as well as apply Design Patterns, Java concurrency, and software engineering testing best-practices.

MapReduce is a programming model for processing large datasets by splitting a particular computation between parallel clusters. The model of MapReduce has been applied by Google and others to analyze large datasets in a reasonable amount of time. Since we will be working on individual machines, we will rely on Java multy-threading to simulate the distributed clusters.

## MapClass Test

Ran on 8 cores at 500 MB chunks:

6 min 10 sec

Ran on 1 core at 500 MB chunks:

15 min 18 sec

## How to run it?

The main folder which contains all our source code is ```projectWithTests```. The application uses ant and build.xml file with the two standard commands: ```ant run```will run the source code contained in ```projectWithTests``` folder, while ```ant test``` will trigger the unit tests.

Note that ```ant run``` is linked to the RunMapReduce.class, which has specific file paths for the input file that you wish to parse and as well as for the output files. The output files can be left untouched and the program will run. However, one needs to specify the correct file path for the input file on their computer. We used two different input files to test our program, both of which are too large to be shared on GitHub even in compressed form. Our main test file is the Wikipedia English article database (58 GB) which one may obtain [here](https://en.wikipedia.org/wiki/Wikipedia:Database_download). Alternatively, one can also download a smaller Wikipedia file (~600 MB) by going to [this site](https://dumps.wikimedia.org/enwiki/20170120/) and retrieving one file from the section "All pages, current versions only". Once the correct pathway is specified then, the program should run smoothly.

Another parameter that could be adjusted is the ```block_size``` in the RunMapReduce.class. The ```block_size``` refers to the size (in MB) of the blocks which each thread in the MapClass will deal with. We found that on a laptop size 50-100 MB was the limit, while when running on a stationary computer the optimal size was 500MB, but that may vary depending on the computer specifiations.
