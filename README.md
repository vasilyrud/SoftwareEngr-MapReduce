# SoftwareEngr-MapReduce
MapReduce Usage and Implementation for Software Engineering 2017 Final Project

## Project's purpose
The goal of the project is to implement a rudimentary working MapReduce system. The purpose of implementing MapReduce is to understand the algorithm in-depth as well as apply Design Patterns, Java concurrency, and software engineering testing best-practices.

MapReduce is a programming model for processing large datasets by splitting a particular computation between parallel clusters. The model of MapReduce has been applied by Google and others to analyze large datasets in a reasonable amount of time. Since we will be working on individual machines, we will rely on Java multy-threading to simulate the distributed clusters.

## How to run it?

1. Download the project:

```
git clone https://github.com/vasilyrud/SoftwareEngr-MapReduce.git
```

2. Open the directory of the project:

```
cd SoftwareEngr-MapReduce
cd projectWithTests
```

3. Run the file using an example file to parse (i.e. the World War 2 Wikipedia article):

```
ant run -Dreadfile=data/world_war_2_article.xml -Drules=data/AllCountries.csv -Dblocksize=50
```

4. Observe the output:

Print the first 15 lines of the output:

```
sed -n '1,15p' reduce_output/reduce.csv
```

The output should look like this:

```
Cambodia,1
Paraguay,1
Iceland,3
Syria,5
Greece,12
Mongolia,4
Austria,8
Latvia,7
Iran,4
Marshall Islands,2
UK,7
Luxembourg,3
Panama,1
Brazil,1
Guatemala,1
```

## Parameters

#### readfile

Specifies the file that should be read by MapReduce. When we parse the example file it is `world_war_2_article.xml`. When we parse the whole of Wikipedia it is `enwiki-20170420-pages-articles.xml`.

#### rules

Specifies what words (and alternatives spellings of the words) are being parsed for. For countries it is `AllCountries.csv`.

#### blocksize

Specifies what block size (in MB) the file is split into. For the sake of the test, 50 MB is the safer option to prevent running out of memory. For the full run, 500 MB was specified.

## Full Run

Ran on `8 cores` at `500 MB` chunks:

`6 min 10 sec`

Ran on `1 core` at `500 MB` chunks:

`15 min 18 sec`

