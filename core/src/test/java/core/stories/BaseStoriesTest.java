package core.stories;

import org.junit.jupiter.api.AfterAll;

import com.config.ApplicationConfig;

import core.gateway.GatewayConstant;
import core.gateway.UserGateway;

public abstract class BaseStoriesTest {
	protected static UserGateway userGateway;

	static{
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
