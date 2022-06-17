package com.mountain.sea.netty;

import com.mountain.sea.core.utils.StringUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.util.CharsetUtil;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/6/15 19:53
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx){

    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //获取客户端发送过来的消息
//        ByteBuf byteBuf = (ByteBuf) msg;
//        if(!(msg instanceof String)){
////            super.channelRead(ctx,msg);
//        }else{
//            String str = (String) msg;
//            System.out.println("收到客户端" + ctx.channel().remoteAddress() + "发送的消息：" + str);
//        }
        System.out.println("Client Address ====== " + ctx.channel().remoteAddress() + msg);
        System.out.println(msg);
        ctx.channel().writeAndFlush("from server:" + UUID.randomUUID());
        ctx.fireChannelActive();
        TimeUnit.MILLISECONDS.sleep(500);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //发送消息给客户端
//        ctx.writeAndFlush(Unpooled.copiedBuffer("服务端已收到消息，并给你发送一个问号?", CharsetUtil.UTF_8));
        ctx.writeAndFlush("服务端已收到消息，并给你发送一个问号?");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //发生异常，关闭通道
        ctx.close();
    }
}
