package com.example.server.handler;

import com.example.server.model.MProtocol;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * create by lorne on 2017/12/26
 */
@ChannelHandler.Sharable
public class ProtocolDecoderHandler extends MessageToMessageDecoder<byte[]>{

    private Logger logger = LoggerFactory.getLogger(this.getClass());



    @Override
    protected void decode(ChannelHandlerContext ctx, byte[] msg, List<Object> out) throws Exception {

        logger.info("decode-->");

        MProtocol protocol = new MProtocol();

        protocol.setData(new String(msg));
        protocol.setName("test");

        out.add(protocol);


    }
}
