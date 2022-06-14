package com.mountain.sea.utils;

import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.runtime.state.StateBackend;
import org.apache.flink.runtime.state.filesystem.FsStateBackend;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.concurrent.TimeUnit;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/6/14 13:24
 */
public class StreamEnvironmentUtil {
    public static StreamExecutionEnvironment getEnvironment(Long checkpointInterval, String checkpointUrl, Integer restartAttempts, Long restartInterval, String registerFile) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.getConfig().enableObjectReuse();
        env.enableCheckpointing(checkpointInterval);
        env.getCheckpointConfig().setMinPauseBetweenCheckpoints(1000);
        env.getCheckpointConfig().enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);
        StateBackend stateBackend = new FsStateBackend(checkpointUrl);
        env.setStateBackend(stateBackend);
        env.setRestartStrategy(RestartStrategies.fixedDelayRestart(restartAttempts, Time.of(restartInterval, TimeUnit.SECONDS)));
        env.getConfig().setGlobalJobParameters(ParameterTool.fromPropertiesFile(registerFile));
        return env;
    }

    public static StreamExecutionEnvironment getEnvironmentNonCheckpoint(Integer restartAttempts, Long restartInterval, String registerFile) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.getConfig().enableObjectReuse();
        env.setRestartStrategy(RestartStrategies.fixedDelayRestart(restartAttempts, Time.of(restartInterval, TimeUnit.SECONDS)));
        env.getConfig().setGlobalJobParameters(ParameterTool.fromPropertiesFile(registerFile));
        return env;
    }
}
