package com.example.server.handler;

import com.example.server.model.MProtocol;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * create by lorne on 2017/12/26
 */
@ChannelHandler.Sharable
public class ServerServiceHandler extends ChannelInboundHandlerAdapter{


    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        MProtocol protocol =(MProtocol) msg;


        logger.info("read-->"+protocol.getData());

        protocol.setName("ok");

        ctx.writeAndFlush(protocol);
    }


}
