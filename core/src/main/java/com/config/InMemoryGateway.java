package com.config;

import core.gateway.inmemory.TransferGatewayMock;
import core.gateway.inmemory.UserGatewayMock;

public class InMemoryGateway extends GatewayConfig{

	@Override
	protected void setGateways() {
		userGateway = UserGatewayMock.getInstance();
		transferGateway = TransferGatewayMock.getInstance();
	}

	@Override
	protected void cleanAll() {
			userGateway.clean();
			transferGateway.clean();			
	}

}
