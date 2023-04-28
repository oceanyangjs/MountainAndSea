package com.mountain.sea.testQueryTable;

import com.mountain.sea.pojo.CountWindowAverage;
import com.mountain.sea.pojo.MyFlatter;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/7/22 9:28
 */
public class TestQueryTable {
    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<String> inputStream = env.socketTextStream("localhost",8888);
        env.fromElements(Tuple2.of(1L, 3L), Tuple2.of(1L, 5L), Tuple2.of(1L, 7L), Tuple2.of(1L, 4L), Tuple2.of(1L, 2L),Tuple2.of(1L, 3L), Tuple2.of(1L, 5L), Tuple2.of(1L, 7L), Tuple2.of(1L, 4L), Tuple2.of(1L, 2L))
//        inputStream.flatMap(new MyFlatter())
                .keyBy(0)
                .flatMap(new CountWindowAverage())
                .print();
        try {
            env.execute();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
