package com.flip.persistence;

import com.config.GatewayConfig;
import com.flip.persistence.gateway.TransferGatewayMysql;
import com.flip.persistence.gateway.UserGatewayMysql;

public class MysqlGatewayConfig extends GatewayConfig{
	@Override
	protected void setGateways() {
		userGateway = UserGatewayMysql.getInstance();
		transferGateway = TransferGatewayMysql.getInstance();
	}

	@Override
	protected void cleanAll() {
			userGateway.clean();
			transferGateway.clean();			
	}
}
