package com.example.client;

import com.example.client.service.NettyClientService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ClientApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext =  SpringApplication.run(ClientApplication.class, args);

		NettyClientService nettyClientService =  applicationContext.getBean(NettyClientService.class);

		nettyClientService.start();

	}



}
