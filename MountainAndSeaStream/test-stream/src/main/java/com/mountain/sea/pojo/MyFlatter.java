package com.mountain.sea.pojo;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/6/9 16:21
 */
public class MyFlatter implements FlatMapFunction<String, Tuple2<String,Integer>> {
    @Override
    public void flatMap(String o, Collector<Tuple2<String,Integer>> collector) throws Exception {
        String[] words = o.split(" ");
        for (String word: words) {
            collector.collect(new Tuple2<>(word, 1));
        }
    }
}
