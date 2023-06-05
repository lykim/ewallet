package core.stories;

import org.junit.jupiter.api.AfterAll;

import com.config.ApplicationConfig;
import com.flip.persistence.MysqlGatewayConfig;

import core.gateway.GatewayConstant;
import core.gateway.UserGateway;

public abstract class BaseStoriesTest {
	protected static UserGateway userGateway;

	static{
		ApplicationConfig.getInstance().addGatewayConfig("MYSQL_GATEWAY", new MysqlGatewayConfig());
		getApplicationConfig().setGateways();
		userGateway =  (UserGateway)getApplicationConfig().getGatewayConfig().getGateway(GatewayConstant.USER_GATEWAY);
	}
	
	@AfterAll
	public static void afterAll() {
		getApplicationConfig().cleanGatewayStoreage();
	}
	private static ApplicationConfig getApplicationConfig() {
		return ApplicationConfig.getInstance();
	}
}
