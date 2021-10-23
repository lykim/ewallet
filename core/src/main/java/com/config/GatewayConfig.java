package com.config;

import core.gateway.Gateway;
import core.gateway.GatewayConstant;
import core.gateway.UserGateway;

public abstract class GatewayConfig {
	protected UserGateway userGateway;
	protected abstract void setGateways();
	protected abstract void cleanStoreage();

	public Gateway getGateway(String gatewayName) {
		switch (gatewayName) {
		case GatewayConstant.USER_GATEWAY:
			return userGateway;
		default:
			return null;
		}
	}
}
