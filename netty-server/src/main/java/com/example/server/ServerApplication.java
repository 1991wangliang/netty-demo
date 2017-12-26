package com.example.server;

import com.example.server.service.NettyServerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext applicationContext =  SpringApplication.run(ServerApplication.class, args);

		NettyServerService nettyServerService =  applicationContext.getBean(NettyServerService.class);

		nettyServerService.start();

	}
}
