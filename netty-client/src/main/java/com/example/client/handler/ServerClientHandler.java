package com.example.client.handler;

import com.example.client.utils.SocketUtils;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * create by lorne on 2017/12/26
 */
@ChannelHandler.Sharable
public class ServerClientHandler extends ChannelInboundHandlerAdapter{


    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        byte[] bytes = (byte[]) msg;

        String str = new String(bytes);

        logger.info("read->"+str);

    }



    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("channelInactive");


        SocketUtils.sendMsg(ctx,"123456");

    }
}
