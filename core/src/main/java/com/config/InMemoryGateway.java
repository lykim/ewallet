package com.config;

import core.gateway.inmemory.UserGatewayMock;

public class InMemoryGateway extends GatewayConfig{

	@Override
	protected void setGateways() {
		userGateway = UserGatewayMock.getInstance();
		
	}

	@Override
	protected void cleanStoreage() {
		userGateway.clean();
		
	}

}
