package com.imooc.bigdata.hadoop.hdfs.app;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.net.URI;

public class App {
    public static void main(String[] args) throws Exception{

        Configuration configuration = new Configuration();
        Path path = new Path("/hello");
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://hadoop000:8020"), configuration, "lijh");

        boolean result = fileSystem.mkdirs(path);
        System.out.println(result);

    }
}
