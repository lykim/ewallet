package com.stories;

import com.config.ApplicationConfig;
import com.stories.base.CommonStoryTemplate;

import core.gateway.GatewayConstant;
import core.gateway.UserGateway;
import core.model.Authorizeable;
import core.model.User;
import core.requestModel.RequestModel;
import core.responseModel.ResponseModel;
import core.utils.TokenUtils;

public class BalanceReadStory extends CommonStoryTemplate<RequestModel, ResponseModel<User>>{
	UserGateway gateway = (UserGateway)ApplicationConfig.getInstance()
			.getGatewayConfig()
			.getGateway(GatewayConstant.USER_GATEWAY);
	
	public BalanceReadStory(RequestModel requestModel) {
		this.requestModel = requestModel;
		this.responseModel = new ResponseModel<User>();
		this.authorize = new Authorizeable();
	}
	
	@Override
	protected void doBusinessProcess() {
		User user = gateway.findUserByUsername(TokenUtils.getUsernameFromToken(requestModel.token));
		responseModel.code = 200;
		responseModel.message = "Balance read success";
		responseModel.content = user;
	}

}
