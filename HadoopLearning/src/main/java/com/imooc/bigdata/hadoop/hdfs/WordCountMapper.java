package com.imooc.bigdata.hadoop.hdfs;

public class WordCountMapper implements ImoocMapper {

    @Override
    public void map(String line, ImoocContext context) {
        String[] words = line.split("\t");

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
