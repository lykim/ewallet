package com.stories;

import com.config.ApplicationConfig;
import com.stories.base.CommonStoryTemplate;

import core.exception.BusinessException;
import core.gateway.GatewayConstant;
import core.gateway.UserGateway;
import core.model.Authorizeable;
import core.requestModel.BalanceTopupRequestModel;
import core.responseModel.ResponseModel;
import core.utils.TokenUtils;

public class BalanceTopupStory extends CommonStoryTemplate<BalanceTopupRequestModel, ResponseModel<String>>{
	private int amount = 0;
	UserGateway gateway = (UserGateway)ApplicationConfig.getInstance()
			.getGatewayConfig()
			.getGateway(GatewayConstant.USER_GATEWAY);
	public BalanceTopupStory(BalanceTopupRequestModel requestModel) {
		this.requestModel = requestModel;
		this.responseModel = new ResponseModel<String>();
		this.authorize = new Authorizeable();
	}
	
	@Override
	protected void doBusinessProcess() {
		parseAmount();
		validateAmount();
		gateway.addBalance(TokenUtils.getUsernameFromToken(requestModel.token),amount);
		setSuccessResponse();
	}
	
	private void setSuccessResponse() {
		responseModel.code = 204;
		responseModel.message = "Topup successfull";
	}
	
	private void parseAmount() {
		try {
			amount = Integer.parseInt(requestModel.amount);			
		}catch (NumberFormatException e) {
			throw new BusinessException(400, "invalid topup amount");
		}
	}
	
	private void validateAmount() {
		if(amount < 1 || amount >= 10000000) throw new BusinessException(400, "invalid topup amount");
	}

}
