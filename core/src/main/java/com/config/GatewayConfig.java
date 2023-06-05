package com.config;

import core.gateway.Gateway;
import core.gateway.GatewayConstant;
import core.gateway.TransferGateway;
import core.gateway.UserGateway;

public abstract class GatewayConfig {
	protected UserGateway userGateway;
	protected TransferGateway transferGateway;
	protected abstract void setGateways();
	protected void cleanStoreage() {
		if(ApplicationConfig.getInstance().getEnvironment().equals("TEST")) {
			cleanAll();			
		}
	}
	protected abstract void cleanAll();

	public Gateway getGateway(String gatewayName) {
		switch (gatewayName) {
		case GatewayConstant.USER_GATEWAY:
			return userGateway;
		case GatewayConstant.TRANSFER_GATEWAY:
			return transferGateway;
		default:
			return null;
		}
	}
}
