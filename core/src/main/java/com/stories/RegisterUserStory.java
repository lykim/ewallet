package com.stories;

import com.config.ApplicationConfig;
import com.stories.base.CommonStoryTemplate;

import core.exception.BusinessException;
import core.gateway.GatewayConstant;
import core.gateway.UserGateway;
import core.model.User;
import core.requestModel.UserRequestModel;
import core.responseModel.ResponseModel;
import core.utils.TokenUtils;

public class RegisterUserStory extends CommonStoryTemplate<UserRequestModel, ResponseModel<User>>{
	UserGateway gateway = (UserGateway)ApplicationConfig.getInstance()
			.getGatewayConfig()
			.getGateway(GatewayConstant.USER_GATEWAY);
	public RegisterUserStory(UserRequestModel requestModel) {
		this.requestModel = requestModel;
		this.responseModel = new ResponseModel<>();
	}
	
	public void doBusinessProcess() {
		validateUsername();
		isUsernameAlreadyExist();
		registerUser();
	}
	
	private void registerUser() {
		User user = new User(requestModel.userName);
		gateway.create(user);
		String jws = TokenUtils.createJWT(requestModel.userName, 86400000);
		responseModel.content = user;
		responseModel.token = jws;
		responseModel.code = 201;
	}
	
	private void isUsernameAlreadyExist() {
		User existingUser = gateway.findUserByUsername(requestModel.userName);
		if(existingUser != null) throw new BusinessException(409, "username already exist");
	}
	
	private void validateUsername() {
		if(requestModel.userName == null || requestModel.userName.length() < 1) {
			throw new BusinessException(400, "bad request");
		}
	}
	
}
