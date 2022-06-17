package com.mountain.sea.netty;

import com.alibaba.fastjson.serializer.StringCodec;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

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
        pipeline.addLast("StringEncoder", new StringEncoder());
        pipeline.addLast("StringDecoder", new StringDecoder());
        // 添加自定义初始化器，先写类名NettyServerHandler，然后去新建这个自定义初始化器
        pipeline.addLast("NettyServerHandler", new NettyServerHandler());
    }
}
