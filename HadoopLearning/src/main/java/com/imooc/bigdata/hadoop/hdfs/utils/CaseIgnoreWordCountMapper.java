package com.imooc.bigdata.hadoop.hdfs.utils;

import com.imooc.bigdata.hadoop.hdfs.ImoocMapper;

import java.util.Locale;

// 用来完成  一行数据 到 缓存的映射   数据行 ==> 缓存
public class CaseIgnoreWordCountMapper implements ImoocMapper {

    @Override
    public void map(String line, ImoocContext context) {
        String[] words = line.toLowerCase().split(" ");

        for(String word : words) {
            Object value = context.get(word);
            if(value == null) { // 没有出现过此单词
                context.write(word, 1);
            } else { // 将单词计数加 1
                int v = Integer.parseInt(value.toString());
                context.write(word, v+1);
            }
        }
    }
}