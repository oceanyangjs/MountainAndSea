package com.mountain.sea;

import com.mountain.sea.testStream.StreamWordCount;

/**
 * Hello world!
 *
 */
public class TestStreamApp
{
    public static void main( String[] args ) throws Exception {
        System.out.println( "Hello flink!" );
        StreamWordCount.process();
    }
}
