package com.mountain.sea.testQueryTable;

import com.esotericsoftware.kryo.serializers.MapSerializer;
import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.common.JobID;
import org.apache.flink.api.common.state.MapState;
import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeutils.base.StringSerializer;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.queryablestate.client.QueryableStateClient;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/7/22 9:42
 */
public class TestQueryTableClient {
    public static void main(String[] args) {
//        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//        ExecutionConfig config = env.getConfig();
        QueryableStateClient client = null;
        try {
//            client = new QueryableStateClient("localhost", 9069);
            client = new QueryableStateClient("172.18.30.74",9069);
//            client.setExecutionConfig(config);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

// the state descriptor of the state to be fetched.
//        ValueStateDescriptor<Tuple2<Long, Long>> descriptor =
//                new ValueStateDescriptor<>(
//                        "average",
//                        TypeInformation.of(new TypeHint<Tuple2<Long, Long>>() {}));
//        CompletableFuture<ValueState<Tuple2<Long, Long>>> resultFuture =
//                client.getKvState(JobID.fromHexString("e5e5331cfdc1688979a0295816644884"), "query-name", 1L, BasicTypeInfo.LONG_TYPE_INFO, descriptor);

        ExecutionConfig executionConfig = new ExecutionConfig();
        executionConfig.addDefaultKryoSerializer(String.class, MapSerializer.class);
        MapStateDescriptor<String, HashMap<String, String>> mapStateDescriptor = new MapStateDescriptor<>(
                "mechanism-state",
                TypeInformation.of(String.class).createSerializer(executionConfig),
                TypeInformation.of(new TypeHint<HashMap<String, String>>() {
                }).createSerializer(executionConfig));
        CompletableFuture<MapState<String, HashMap<String, String>>> resultFuture =
                client.getKvState(JobID.fromHexString("e5e5331cfdc1688979a0295816644884"), "queryable-mechanism-state", "57993129281724416", BasicTypeInfo.STRING_TYPE_INFO, mapStateDescriptor);
// now handle the returned value
        resultFuture.thenAccept(response -> {
            try {
                Iterable<String> keys = response.keys();
//                Tuple2<Long, Long> res = keys;
//                System.out.println(res.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
