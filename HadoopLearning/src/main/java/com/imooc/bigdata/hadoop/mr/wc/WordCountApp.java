package com.imooc.bigdata.hadoop.mr.wc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.net.URI;


/**
 * 使用MR统计HDFS上的文件对应的词频
 *
 * Driver: 配置Mapper，Reducer的相关属性
 *
 * 提交到本地运行：开发过程中使用
 */
public class WordCountApp {


    public static void main(String[] args) throws Exception{

        System.out.println("begin ----------");

        System.setProperty("HADOOP_USER_NAME", "lijh");
        System.setProperty("hadoop.home.dir", "d:\\winutil\\");

        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS","hdfs://192.168.180.130:8020");

        System.out.println("1");

        // 创建一个Job
        Job job = Job.getInstance(configuration);

        // 设置Job对应的参数: 主类
        job.setJarByClass(WordCountApp.class);

        System.out.println(" 2 ");

        // 设置Job对应的参数: 设置自定义的Mapper和Reducer处理类
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);

        // 设置Job对应的参数: Mapper输出key和value的类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        // 添加 Combiner 的设置
        job.setCombinerClass(WordCountReducer.class);

        System.out.println(" 3 ");

        // 设置Job对应的参数: Reduce输出key和value的类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        System.out.println(" 4 ");

        // 如果输出目录已经存在，则先删除
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://192.168.180.130:8020"),configuration, "lijh");
        Path outputPath = new Path("/wordcount/output");

        System.out.println(" 5 ");

        if(fileSystem.exists(outputPath)) {
            fileSystem.delete(outputPath,true);
        }


        // 设置Job对应的参数: Mapper输出key和value的类型：作业输入和输出的路径
        FileInputFormat.setInputPaths(job, new Path("/wordcount/input"));
        FileOutputFormat.setOutputPath(job, outputPath);

        System.out.println(" 6 ");

        // 提交job
        boolean result = job.waitForCompletion(true);
        System.out.println(" 7 ");
        System.exit(result ? 0 : -1);

    }
}
