package com.stories;

import com.config.ApplicationConfig;
import com.stories.base.CommonStoryTemplate;

import core.exception.BusinessException;
import core.gateway.GatewayConstant;
import core.gateway.TransferGateway;
import core.gateway.UserGateway;
import core.model.Authorizeable;
import core.model.User;
import core.requestModel.TransferRequestModel;
import core.responseModel.ResponseModel;
import core.utils.TokenUtils;

public class TransferStory extends CommonStoryTemplate<TransferRequestModel, ResponseModel<String>>{

	UserGateway userGateway = (UserGateway)ApplicationConfig.getInstance()
			.getGatewayConfig()
			.getGateway(GatewayConstant.USER_GATEWAY);
	TransferGateway transferGateway = (TransferGateway)ApplicationConfig.getInstance()
			.getGatewayConfig()
			.getGateway(GatewayConstant.TRANSFER_GATEWAY);
	int amount;
	User destinationUser;
	User sourceUser;
	public TransferStory(TransferRequestModel requestModel) {
		this.requestModel = requestModel;
		this.responseModel = new ResponseModel<>();
		this.authorize = new Authorizeable();
	}
	@Override
	protected void doBusinessProcess() {
		setDestinationUser();
		setSourceUser();
		amount = requestModel.amount;
		validateAmount();
		transferGateway.transfer(sourceUser.getUsername(), destinationUser.getUsername(), amount);
		responseModel.code = 200;
		responseModel.message = "Transfer success";
	}
	
	private void validateAmount() {
		if(sourceUser.getBalanceAmount() <= amount)
			throw new BusinessException(400, "Insufficient balance");		
	}
	
	private void setSourceUser() {
		sourceUser = userGateway.findUserByUsername(TokenUtils.getUsernameFromToken(requestModel.token));
	}
	
	private void setDestinationUser() {
		destinationUser = userGateway.findUserByUsername(requestModel.toUsername);
		if(destinationUser == null)
			throw new BusinessException(404, "Destination user not found");
	}
	
}
