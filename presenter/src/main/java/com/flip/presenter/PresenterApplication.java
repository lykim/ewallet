package com.flip.presenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.config.ApplicationConfig;
import com.flip.persistence.MysqlGatewayConfig;

@SpringBootApplication
public class PresenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(PresenterApplication.class, args);
		ApplicationConfig.getInstance().addGatewayConfig("MYSQL_GATEWAY", new MysqlGatewayConfig());
		ApplicationConfig.getInstance().setGateways();
	}

}
