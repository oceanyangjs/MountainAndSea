package com.mountain.sea.netty;

import com.alibaba.fastjson.serializer.StringCodec;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/6/15 19:51
 */
public class NettyServerChannelHandler extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 添加内置初始化器(编码初始化器：HttpServerCodec)
//        pipeline.addLast("HttpServerCodec", new HttpServerCodec());
//        pipeline.addLast("StringEncoder", new StringEncoder());
//        pipeline.addLast("StringDecoder", new StringDecoder());
        pipeline.addLast("aggregator", new HttpObjectAggregator(65535));
        pipeline.addLast("http-chunked", new ChunkedWriteHandler());
        ch.pipeline().addLast("http-codec", new HttpServerCodec());
        // 添加自定义初始化器，先写类名NettyServerHandler，然后去新建这个自定义初始化器
//        tcp 消息处理
//        pipeline.addLast("NettyTcpServerHandler", new NettyTcpServerHandler());
//        websocket 消息处理
        pipeline.addLast("NettyWebsocketServerHandler", new NettyWebsocketServerHandler());
    }
}
