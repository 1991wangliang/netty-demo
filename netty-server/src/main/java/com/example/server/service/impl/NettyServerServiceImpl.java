package com.example.server.service.impl;

import com.example.server.handler.ProtocolDecoderHandler;
import com.example.server.handler.ProtocolEncoderHandler;
import com.example.server.handler.ServerServiceHandler;
import com.example.server.service.NettyServerService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * create by lorne on 2017/12/26
 */
@Service
public class NettyServerServiceImpl implements NettyServerService {



    private Logger logger = LoggerFactory.getLogger(NettyServerServiceImpl.class);

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;


    private int port = 8099;


    @Override
    public void start() {

        ProtocolDecoderHandler protocolDecoderHandler = new ProtocolDecoderHandler();

        ProtocolEncoderHandler protocolEncoderHandler = new ProtocolEncoderHandler();

        ServerServiceHandler serverServiceHandler = new ServerServiceHandler();

        bossGroup = new NioEventLoopGroup(50); // (1)
        workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {

                            ch.pipeline().addLast(new ByteArrayDecoder());
                            ch.pipeline().addLast(new ByteArrayEncoder());

//                            ch.pipeline().addLast(new LengthFieldPrepender(4, false));
                            ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 1, 1, 0, 0));

                            ch.pipeline().addLast(protocolDecoderHandler);
                            ch.pipeline().addLast(protocolEncoderHandler);

                            ch.pipeline().addLast(serverServiceHandler);


                        }
                    });

            // Start the server.
            b.bind(port);
            logger.info("Socket started on port(s): " + port + " (socket)");

        } catch (Exception e) {
            // Shut down all event loops to terminate all threads.
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }

    }
}
