package com.example.server.handler;

import com.example.server.model.MProtocol;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * create by lorne on 2017/12/26
 */

@ChannelHandler.Sharable
public class ProtocolEncoderHandler extends MessageToMessageEncoder<MProtocol>{

    private Logger logger = LoggerFactory.getLogger(this.getClass());



    @Override
    protected void encode(ChannelHandlerContext ctx, MProtocol msg, List<Object> out) throws Exception {
        logger.info("encode-->");

        String data = msg.getName()+"|"+msg.getData();

        out.add(Unpooled.buffer().writeBytes(data.getBytes()));

    }
}
