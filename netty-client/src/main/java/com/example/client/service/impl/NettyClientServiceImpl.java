package com.example.client.service.impl;

import com.example.client.handler.ServerClientHandler;
import com.example.client.service.NettyClientService;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * create by lorne on 2017/12/26
 */
@Service
public class NettyClientServiceImpl implements NettyClientService {


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private EventLoopGroup workerGroup;

    private String host = "127.0.0.1";

    private int port = 8099;

    @Override
    public void start() {


        ServerClientHandler serverClientHandler = new ServerClientHandler();

        workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap(); // (1)
            b.group(workerGroup); // (2)
            b.channel(NioSocketChannel.class); // (3)
            b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {


                    ch.pipeline().addLast(new ByteArrayDecoder());
                    ch.pipeline().addLast(new ByteArrayEncoder());

                    ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 1, 1, 0, 0));

                    ch.pipeline().addLast(serverClientHandler);

                }
            });
            // Start the client.
            logger.info("连接socket服务-> host:" + host + ",port:" + port);
            b.connect(host, port); // (5)


        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        }
    }

    @Override
    public void close() {
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
            workerGroup = null;

        }
    }
}
