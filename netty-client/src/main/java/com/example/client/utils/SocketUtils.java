package com.example.client.utils;


import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by lorne on 2017/7/6.
 */
public class SocketUtils {


    public static void sendMsg(final ChannelHandlerContext ctx, final String msg) {
        ctx.writeAndFlush(Unpooled.buffer().writeBytes(msg.getBytes()));

    }


    public static void sendMsg(final Channel ctx, final String msg) {
        ctx.writeAndFlush(Unpooled.buffer().writeBytes(msg.getBytes()));
    }

}
