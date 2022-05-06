package com.imooc.bigdata.hadoop.hdfs.utils;

import java.util.HashMap;
import java.util.Map;


/**
 * 自定义上下文, 其实就是缓存
 */
public class ImoocContext {

    private Map<Object, Object> cacheMap = new HashMap<Object, Object>();

    public Map<Object, Object> getCacheMap() {
        return cacheMap;

    }

    // 写缓存
    public void write(Object key, Object value) {
        cacheMap.put(key, value);
    }

    /**
     * 从缓存中获取值
     * @param key 单词
     * @return 返回单词的数量
     */
    public Object get(Object key) {
        return cacheMap.get(key);
    }

}
