package com.imooc.bigdata.hadoop.hdfs;

import com.imooc.bigdata.hadoop.hdfs.utils.ImoocContext;

public interface ImoocMapper {
    /**
     *
     * @param line 读取到的每一行数据
     * @param context 上下文 / 缓存
     */
    public void map(String line, ImoocContext context);
}
