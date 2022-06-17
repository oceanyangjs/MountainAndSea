package com.mountain.sea.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/6/15 19:01
 */
@Component
public class NettyServer implements CommandLineRunner {
    @Value("${netty.port}")
    Integer port;
    @Override
    public void run(String... args) throws Exception {
//        todo 修改为线程池
        Thread nettyThread = new Thread(new Runnable() {
            @Override
            public void run() {
                //        接收客户端连接，分发给workerGroup
                EventLoopGroup bossGroup = new NioEventLoopGroup();
//        真正处理连接
                EventLoopGroup workerGroup = new NioEventLoopGroup();
//        启动服务
                ServerBootstrap serverBootstrap = new ServerBootstrap();
                try {
                    ChannelFuture channelFuture = serverBootstrap
//                            设置两个线程组
                            .group(bossGroup, workerGroup)
//                            设置服务端实现通道
                            .channel(NioServerSocketChannel.class)
//                            设置线程队列连接个数
                            .option(ChannelOption.SO_BACKLOG,128)
//                            设置保持活动连接状态
                            .childOption(ChannelOption.SO_KEEPALIVE,true)
//                              初始化通道对象
                            .childHandler(new NettyServerChannelHandler())
//                    端口绑定
                            .bind(port)
//                    使用同步启动
                            .sync();
                    // 启动Channel
                    channelFuture.channel().closeFuture().sync();
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    bossGroup.shutdownGracefully();
                    workerGroup.shutdownGracefully();
                }
            }
        },"nettyThread");
        nettyThread.start();
    }
}
