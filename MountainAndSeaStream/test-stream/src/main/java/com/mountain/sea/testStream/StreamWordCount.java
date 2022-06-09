package com.mountain.sea.testStream;

import com.mountain.sea.pojo.MyFlatter;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/6/9 16:20
 */
public class StreamWordCount {
    public static void process() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<String> inputStream = env.socketTextStream("localhost",8888);

        DataStream<Tuple2<String,Integer>> outStream = inputStream.flatMap(new MyFlatter())
                .keyBy(0).sum(1);
        outStream.print().setParallelism(4);

        env.execute();
    }
}
